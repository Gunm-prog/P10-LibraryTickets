package com.emilie.Lib7.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailAlreadyExistsException  extends RuntimeException {
    public EmailAlreadyExistsException(String message){ super(message);}
}
