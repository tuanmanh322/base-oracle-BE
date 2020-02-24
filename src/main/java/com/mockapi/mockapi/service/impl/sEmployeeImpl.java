package com.mockapi.mockapi.service.impl;

import com.mockapi.mockapi.common.TimeProvider;
import com.mockapi.mockapi.exception.ApiRequestException;
import com.mockapi.mockapi.exception.ResourceNotFoundException;
import com.mockapi.mockapi.model.*;
import com.mockapi.mockapi.repository.*;
import com.mockapi.mockapi.repository.impl.DRole_Emp;
import com.mockapi.mockapi.service.ISEmployeeService;
import com.mockapi.mockapi.util.Constants;
import com.mockapi.mockapi.util.RandomPassword;
import com.mockapi.mockapi.web.dto.AbsentDTO;
import com.mockapi.mockapi.web.dto.EmployeeDTO;
import com.mockapi.mockapi.web.dto.Issues_HistoryDTO;
import com.mockapi.mockapi.web.dto.NewsDTO;
import com.mockapi.mockapi.web.dto.request.EmployeeEditRequest;
import com.mockapi.mockapi.web.dto.request.EmployeeRequest;
import com.mockapi.mockapi.web.dto.request.LoginRequest;
import com.mockapi.mockapi.web.dto.request.SearchEmployeeRequest;
import com.mockapi.mockapi.web.dto.response.GetListDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.GetSingleDataResponseDTO;
import com.mockapi.mockapi.web.dto.response.resp.SearchRequestResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class sEmployeeImpl implements ISEmployeeService {
    private static final Logger log = LoggerFactory.getLogger(sEmployeeImpl.class);

    @Value("${upload-path}")
    String uploadPath;

    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserCustomDetail userDetailService;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private NewsRepo newsRepo;

    @Autowired
    private AbsentRepo absentRepo;

    @Autowired
    private IssueHistoryRepo issueHistoryRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TimeProvider timeProvider;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private EmployeeIssueRepo employeeIssueRepo;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Autowired
    private PositionRepo positionRepo;

    @Autowired
    private TeamRepo teamRepo;

    @Autowired
    private DRole_Emp dRole_emp;

    // thêm nhân viên
    @Override
    public GetSingleDataResponseDTO<EmployeeDTO> add(EmployeeRequest employeeRequest) {
        log.info("--request to add new employee: {}" + employeeRequest.toString());
        GetSingleDataResponseDTO<EmployeeDTO> result = new GetSingleDataResponseDTO<>();
        try {
            //tạo emp object =  map employeeRequest với Employee.class
            Employee emp = modelMapper.map(employeeRequest, Employee.class);
            if (findByUsername(employeeRequest.getUsername())) {
                log.info(" username already have!!!");
                result.setMessage("username already have!!!");
            } else {
                // tạo password random 8 kí tự
                String pw = RandomPassword.pwGenerate();
                emp.setActived(false);
                emp.setPassword(passwordEncoder.encode(pw));
                emp.getRoles().add(roleRepo.findByName(Constants.ROLE_PUBLIC));
                emp.setCreatedDate(new Date());
                emp = employeeRepo.save(emp);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(employeeRequest.getEmail());
                message.setSubject("Verified account & Password sender!");
                ConfirmationToken token = new ConfirmationToken(emp);
                confirmationTokenRepo.save(token);

                message.setText("Password: " + pw + "\n Go to this page to activate your account http://localhost:4200/#/verify-account?token=" + token.getToken());
                javaMailSender.send(message);
                result.setResult(modelMapper.map(emp, EmployeeDTO.class));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null);
        }
        log.info("Response of add new employee : {}" + employeeRequest.toString());
        return result;
    }

    @Override
    public GetSingleDataResponseDTO<EmployeeDTO> addByAd(EmployeeDTO dto) {
        GetSingleDataResponseDTO<EmployeeDTO> result = new GetSingleDataResponseDTO<>();
        try {
            Employee empl = modelMapper.map(dto, Employee.class);
            if (findByUsername(dto.getUsername())) {
                log.info("This account has already existed!");
                result.setMessage("This account has already existed!");
            } else {
                // set time = date now()
                empl.setCreatedDate(new Date());
                empl.setPassword(passwordEncoder.encode(dto.getPassword()));
                empl.setLastAccess(new Date());
                // phân quyền cho emp
                Set<Role> roles = new HashSet<>();
                Role role = roleRepo.findByName(dto.getRole());
                roles.add(role);
                empl.setRoles(roles);
                // add phòng ban vào emp
                Department department = departmentRepo.findByName(dto.getDepartment());
                empl.setDepartment(department);
                // add chức vụ vào emp
                Position position = positionRepo.findByName(dto.getPosition());
                empl.setPosition(position);
                empl = employeeRepo.save(empl);
                result.setResult(modelMapper.map(empl, EmployeeDTO.class));
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null);
        }
        return result;
    }

    @Override
    public boolean findByUsername(String username) {
        log.info("----request finD username----");
        try {
            Employee emp = employeeRepo.findByUsername(username);
            if (emp != null) {
                return true;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        log.info("---response-------------");
        return false;
    }

    @Override
    public GetSingleDataResponseDTO<EmployeeDTO> findById(Long id) {
        log.info("----request finD ID----");
        GetSingleDataResponseDTO<EmployeeDTO> result = new GetSingleDataResponseDTO<>();
        try {
            Employee emp = employeeRepo.getOne(id);
//            TypeMap<Employee,EmployeeDTO> toDto = modelMapper.getTypeMap(Employee.class,EmployeeDTO.class);
//            // ignoreDepartment
//            if(toDto  == null){
//                toDto = modelMapper.createTypeMap(Employee.class,EmployeeDTO.class);
//            }
//            toDto.addMappings(x-> x.skip(EmployeeDTO:: setDepartment));
//            toDto.addMappings(x -> x.skip(EmployeeDTO::setPosition));
//            EmployeeDTO dto = toDto.map(emp);
            EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
             emp.getRoles().forEach(role -> {
                 dto.setRole(role.getName());
             });
//            if (emp.getPosition().getName() != null) {
//                dto.setPosition(emp.getPosition().getName());
//            }
//            if (emp.getNews() != null) {
//                List<NewsDTO> newsDTOS = new ArrayList<>();
//                emp.getNews().stream().map((newmap) -> modelMapper.map(newmap, NewsDTO.class))
//                        .forEachOrdered((newm) -> {
//                            newsDTOS.add(newm);
//                        });
//                dto.setNews(newsDTOS);
//            }
//            if (emp.getIssues_histories() != null) {
//                List<Issues_HistoryDTO> historyDTOS = new ArrayList<>();
//                emp.getIssues_histories().stream().map((hismap) -> modelMapper.map(hismap, Issues_HistoryDTO.class))
//                        .forEachOrdered((his) -> {
//                            historyDTOS.add(his);
//                        });
//                dto.setIssues_histories(historyDTOS);
//            }
//            if (emp.getAbsents1() != null) {
//                List<AbsentDTO> absentDTOS = new ArrayList<>();
//                emp.getAbsents1().stream().map((abmap) -> modelMapper.map(abmap, AbsentDTO.class))
//                        .forEachOrdered((abs) -> {
//                            absentDTOS.add(abs);
//                        });
//                dto.setAbsents(absentDTOS);
//            }

            result.setResult(dto);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null);
        }
        log.info("---response-------------" + result.toString());
        return result;
    }

    @Override
    public GetListDataResponseDTO<EmployeeDTO> getAll() {
        log.info("--- request getAll Employee----");
        GetListDataResponseDTO<EmployeeDTO> result = new GetListDataResponseDTO<>();
        List<Employee> data = employeeRepo.findAll();
        List<EmployeeDTO> dto = new ArrayList<>();
        try {
            // duyệt list emp ( res = Employee)
            data.stream().map(res -> {
                EmployeeDTO em = modelMapper.map(res, EmployeeDTO.class);
                dto.add(em);
                result.setValue(dto);
                return result;
            }).collect(Collectors.toList());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setValue(null);
        }

        log.info("---response result ---- " + result.getMessage());
        return result;
    }

    @Override
    public boolean resetPW() {
        return false;
    }

    @Override
    public void activateAccount(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepo.findByToken(token);

        if (confirmationToken == null) {
            throw new ResourceNotFoundException("Confirmation token doesn't exist.");
        }

        if (confirmationToken.isUsed()) {
            throw new ApiRequestException("This token has been already used.");
        }

        Employee emp = confirmationToken.getEmployee();
        long timeDifference = timeProvider.timeDifferenceInMinutes(timeProvider.now(), confirmationToken.getDatetimeCreated());

        if (timeDifference < 30) {
            emp.setActived(true);
            emp.setLastAccess(new Date());
            employeeRepo.save(emp);
            confirmationToken.setUsed(true);
            confirmationTokenRepo.save(confirmationToken);
        } else {
            confirmationTokenRepo.delete(confirmationToken);
            employeeRepo.delete(emp);
            throw new ApiRequestException("Confirmation token timed out.");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        log.info("--- request delete obj Employee Employee----");
//        GetSingleDataResponseDTO<EmployeeDTO> result = new GetSingleDataResponseDTO<>();
        Employee emp = employeeRepo.findById(id).get();
        try {
            log.info("-----findAbsent--------");
                if (findAbsent(emp.getId()) != null) {
                    absentRepo.delete(findAbsent(emp.getId()));
                }
            log.info("-----findNews--------");
                if (findNews(emp.getId()) != null) {
                    newsRepo.delete(findNews(emp.getId()));
                }
            log.info("-----findConfigToken--------");
                if (findConfigToken(emp.getId()) != null) {
                    confirmationTokenRepo.delete(findConfigToken(emp.getId()));
                }
            log.info("-----findEmpIssue--------");
                if (findEmpIssue(emp.getId()) != null) {
                    employeeIssueRepo.delete(findEmpIssue(emp.getId()));
                }
            log.info("-----findIH--------");
                if (findIH(emp.getId()) != null) {
                    issueHistoryRepo.delete(findIH(emp.getId()));
                }
            log.info("-----getRoles--------");

                    emp.getRoles().forEach(role -> {
                        log.info("-----getRoles--------");
                        role.getEmployees().remove(emp);
                    });
            log.info("-----start delete--------");
                employeeRepo.delete(emp);
            log.info("-----end delete--------");
//            result.setResult(modelMapper.map(emp, EmployeeDTO.class));
//            log.info("-- Response of delete Employee " + result.getMessage());
//            return  result;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        log.info("---- deleted Employee service with id" + emp.getId());
    }

    public ABSENT findAbsent(Long id) {
        ABSENT absent = absentRepo.findByEmployeeId(id);
        return absent;
    }

    public Issues_History findIH(Long id) {
        Issues_History issuesHistory = issueHistoryRepo.findByUpdatePerson(id);
        return issuesHistory;
    }

    public Employee_Issue findEmpIssue(Long id) {
        Employee_Issue employeeIssue = employeeIssueRepo.findByEmployeeId(id);
        return employeeIssue;
    }

    public News findNews(Long id) {
        News news = newsRepo.findByEmployeeId(id);
        return news;
    }

    public ConfirmationToken findConfigToken(Long id) {
        ConfirmationToken confirmationToken = confirmationTokenRepo.findByEmployeeId(id);
        return confirmationToken;
    }

    @Override
    public GetListDataResponseDTO<SearchRequestResponse> All(SearchEmployeeRequest request) {
        log.info("--request to get all employee--sssss");
        GetListDataResponseDTO<SearchRequestResponse> result = new GetListDataResponseDTO<>();
        List<SearchRequestResponse> list = new ArrayList<>();
        try {
            Page<Employee> rawDatas = employeeRepo.findAll(PageRequest.of(request.getPage(), request.getPageSize()));
            if (rawDatas != null) {
                if (rawDatas.getContent().size() > 0) {
                    rawDatas.getContent().forEach(emp -> {
                        SearchRequestResponse requestResponse = modelMapper.map(emp, SearchRequestResponse.class);
                        list.add(requestResponse);
                    });
                }
                result.setResult(list, rawDatas.getTotalElements(), rawDatas.getTotalPages());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null, null, null);
        }
        log.info("Response to get all Employee " + result.getMessage());
        return result;
    }

    @Override
    public GetListDataResponseDTO<SearchRequestResponse> AllByParams(SearchEmployeeRequest request) {
        log.info("--request to getAllByParmas is -----");
        GetListDataResponseDTO<SearchRequestResponse> result = new GetListDataResponseDTO<>();
        Page<SearchRequestResponse> rawDatas = employeeDAO.getListByParams(request);
        System.out.println("content : " + rawDatas.getContent() + "elel: " + rawDatas.getTotalElements());
        result.setResult(rawDatas.getContent(), rawDatas.getTotalElements(), rawDatas.getTotalPages());
        log.info("--response to get list employee by params: " + result.getMessage());
        return result;
    }

    @Override
    public GetSingleDataResponseDTO<EmployeeEditRequest> update(EmployeeEditRequest request, MultipartFile multipartImage) {
        log.info("--request update employee service ----");
        GetSingleDataResponseDTO<EmployeeEditRequest> result = new GetSingleDataResponseDTO<>();
        try {
            // check emp does'nt exist
            Employee employee = employeeRepo.findById(request.getId()).get();
            if (employee != null) {
                    String fileName= uploadPath + multipartImage.getOriginalFilename();
                    File tempFile =new File(fileName);
                    if (!multipartImage.isEmpty()) {
                        try {
                            byte[] bytes = multipartImage.getBytes();
                            BufferedOutputStream stream =
                                    new BufferedOutputStream(new FileOutputStream(tempFile));
                            stream.write(bytes);
                            stream.close();
                        } catch (IOException e) {
                            throw new Exception(e.getLocalizedMessage());
                        }
                    }
                log.info("Saved uploaded image temporarily");
                employee.setBirthday(request.getBirthday());
                employee.setAddress(request.getAddress());
                employee.setEducation(request.getEducation());
                employee.setUserType(request.getUserType());
                employee.setAbsents(request.getAbsents());
                employee.setAbsents1(request.getAbsents());
                employee.setDepartment(request.getDepartment());
                employee.setEmail(request.getEmail());
                employee.setSkypeAcc(request.getSkypeAcc());
                employee.setPhoneNumber(request.getPhoneNumber());
                employee.setImage(multipartImage.getOriginalFilename());
                employee.setNews(request.getNews());
                employee.setFbLink(request.getFbLink());
                employee.setGraduationYear(request.getGraduationYear());
                employee.setFaculty(request.getFaculty());
                employee.setFullName(request.getFullName());
                employee.setUniversity(request.getUniversity());
                employee.setLastAccess(new Date());
                employeeRepo.save(employee);
                result.setResult(modelMapper.map(employee, EmployeeEditRequest.class));
            }
            log.info("--response Employee update service ---" + result.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null);
        }
        return result;
    }

    @Override
    public GetSingleDataResponseDTO<EmployeeEditRequest> update(EmployeeEditRequest request) {
        log.info("--request update employee service ----");
        GetSingleDataResponseDTO<EmployeeEditRequest> result = new GetSingleDataResponseDTO<>();
        try {
            Employee employee = employeeRepo.findById(request.getId()).get();
            if (employee != null) {
                employee.setBirthday(request.getBirthday());
                employee.setAddress(request.getAddress());
                employee.setEducation(request.getEducation());
                employee.setUserType(request.getUserType());
                employee.setAbsents(request.getAbsents());
                employee.setAbsents1(request.getAbsents());
                employee.setDepartment(request.getDepartment());
                employee.setEmail(request.getEmail());
                employee.setSkypeAcc(request.getSkypeAcc());
                employee.setPhoneNumber(request.getPhoneNumber());
                employee.setNews(request.getNews());
                employee.setFbLink(request.getFbLink());
                employee.setGraduationYear(request.getGraduationYear());
                employee.setFaculty(request.getFaculty());
                employee.setFullName(request.getFullName());
                employee.setUniversity(request.getUniversity());
                employee.setLastAccess(new Date());
                employeeRepo.save(employee);
                result.setResult(modelMapper.map(employee, EmployeeEditRequest.class));
            }
            log.info("--response Employee update service ---" + result.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null);
        }
        return result;
    }

    @Override
    public GetSingleDataResponseDTO<EmployeeDTO> updateByAd(EmployeeDTO dto) {
        log.info("--- start update employee by Admin");
        GetSingleDataResponseDTO<EmployeeDTO> result = new GetSingleDataResponseDTO<>();
        try {
            Employee emp = employeeRepo.findById(dto.getId()).get();
            if (emp != null) {
                emp.setActived(dto.isActived());
                emp.setBirthday(dto.getBirthday());
                emp.setAddress(dto.getAddress());
                emp.setEducation(dto.getEducation());
                emp.setUserType(dto.getUserType());
                emp.setEmail(dto.getEmail());
                emp.setSkypeAcc(dto.getSkypeAcc());
                emp.setPhoneNumber(dto.getPhoneNumber());
                emp.setFbLink(dto.getFbLink());
                emp.setGraduationYear(dto.getGraduationYear());
                emp.setFaculty(dto.getFaculty());
                emp.setFullName(dto.getFullName());
                emp.setUniversity(dto.getUniversity());
                emp.setLastAccess(new Date());
                Set<Role> roles = new HashSet<>();
                Role role = roleRepo.findByName(dto.getRole());
                roles.add(role);
                for (Role e : roles) {
                    System.out.println("role e:" + e.getName());
                }
                System.out.println("role : " + role.getName());
                System.out.println("role  size: " + roles.size());
                emp.setRoles(roles);
//                }
                employeeRepo.save(emp);
                result.setResult(modelMapper.map(emp, EmployeeDTO.class));
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            result.setResult(null);
        }


        return result;
    }
     

    @Override
    public GetSingleDataResponseDTO<Employee> checkUsername(String username) {
        GetSingleDataResponseDTO<Employee> result = new GetSingleDataResponseDTO<>();
        try {
            Employee emp = employeeRepo.findByUsername(username);
            String name = emp.getUsername();
            if (name != null) {
                result.setResult(emp);
                return result;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage(), ex);
            result.setResult(null);
        }
        return result;

    }

    @Override
    public void forgotPW(String email) {
        Employee employee = employeeRepo.findByEmail(email);
        String pw = RandomPassword.pwGenerate();
        if (employee != null) {
            employee.setPassword(passwordEncoder.encode(pw));
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject(" Password sender! \tfor username: " + employee.getUsername());
            message.setText("Password: " + pw);
            javaMailSender.send(message);
            employeeRepo.save(employee);
        }
    }
}

