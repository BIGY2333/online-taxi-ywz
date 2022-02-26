package com.ywz.service_vertification_code.service;

import com.ywz.internal_common.dto.ResponseResult;
import com.ywz.internal_common.dto.verificationcode.VerifyCodeResponse;

public interface VertifyCodeService {

    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber);
    public ResponseResult verify(int identity,String phoneNumber,String code);
}
