package com.mockapi.mockapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    HttpStatus.NOT_FOUND : nếu có exception thì sẽ trả về 404 thay vì lỗi 501
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends StorageException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
