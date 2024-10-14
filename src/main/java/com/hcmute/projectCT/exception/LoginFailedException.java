package com.hcmute.projectCT.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFailedException extends RuntimeException{
    private int errorCode;
    private String message;
}
