package com.ywz.internal_common.dto.verificationcode;


import lombok.Data;

@Data
public class VerifyCodeResponse {
    /**
     * 返回生成的验证码
     * */
    private String code;
}
