package com.hcmute.projectCT.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddCollabFailedException extends RuntimeException{
    private int errorCode;
    private String message;
}
