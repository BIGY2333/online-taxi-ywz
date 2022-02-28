package com.ywz.service_vertification_code.controller;


import com.ywz.internal_common.dto.ResponseResult;
import com.ywz.service_vertification_code.request.CodeVerifyRequest;
import com.ywz.service_vertification_code.service.VertifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/vertify-code")
@Slf4j
public class VertifyCodeController {

    @Autowired
    VertifyCodeService vertifyCodeService;

    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity, @PathVariable("phoneNumber") String phoneNumber){
        log.info("身份类型："+identity+",手机号："+phoneNumber);
        return vertifyCodeService.generate(identity, phoneNumber);
    }

    @PostMapping("/verify")
    public ResponseResult verify(@RequestBody CodeVerifyRequest request) {
        log.info("/verify-code/verify  request:"+ JSONObject.wrap(request));
        //获取手机号和验证码
        String phoneNumber = request.getPhoneNumber();
        int identity = request.getIdentity();
        String code = request.getCode();

        return vertifyCodeService.verify(identity,phoneNumber,code);

    }

    public static void main(String[] args) {
        System.out.println("aaa");
    }
}
