package com.mockapi.mockapi.util;

import com.mockapi.mockapi.web.dto.BaseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageBuilder {
    public static Pageable buildPageable(BaseDTO obj) {
        Pageable pageable = null;
        if (DataUtils.isNullOrEmpty(obj.getSort())) {
            pageable = PageRequest.of(obj.getPage(), obj.getPageSize(), null);
        } else {
            String[] sorts = obj.getSort().split(",");
            Sort sort = new Sort(Sort.Direction.fromString(sorts[1]), sorts[0]);
            pageable = PageRequest.of(obj.getPage(), obj.getPageSize(), sort);
        }
        return pageable;
    }
}
