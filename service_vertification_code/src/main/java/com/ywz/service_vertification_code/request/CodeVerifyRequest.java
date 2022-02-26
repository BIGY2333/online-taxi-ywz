package com.ywz.service_vertification_code.request;

import lombok.Data;

@Data
public class CodeVerifyRequest {

    private int identity;

    private String phoneNumber;

    private String code;
}
