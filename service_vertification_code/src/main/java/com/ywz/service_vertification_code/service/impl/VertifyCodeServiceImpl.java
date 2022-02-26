package com.ywz.service_vertification_code.service.impl;

import com.ywz.internal_common.constant.CommonStatusEnum;
import com.ywz.internal_common.constant.IdentityConstant;
import com.ywz.internal_common.dto.ResponseResult;
import com.ywz.internal_common.dto.verificationcode.VerifyCodeResponse;
import com.ywz.service_vertification_code.constant.VerifyCodeConstant;
import com.ywz.service_vertification_code.service.VertifyCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VertifyCodeServiceImpl implements VertifyCodeService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    /**
     * 生成验证码
     * */
    @Override
    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber) {

        //需要校验是否在发送时限内，防止恶意调用
        checkSendCodeTimeLimit(phoneNumber);

        //生成6位code,使用纯数字操作，不同字符串，更加快
        String code = String.valueOf((int)((Math.random()*9+1)*100000));
        String keyPre = generateKeyPreByIdentity(identity);
        String key = keyPre + phoneNumber;
        //存redis，2分钟过期
        BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);
        codeRedis.set(code);
        codeRedis.expire(120, TimeUnit.SECONDS);


        VerifyCodeResponse verifyCodeResponse = new VerifyCodeResponse();
        verifyCodeResponse.setCode(code);
        return ResponseResult.success(verifyCodeResponse);
    }

   /**
    * 校验验证码，是否和redis中保存的一致
    * */

    @Override
    public ResponseResult verify(int identity,String phoneNumber,String code){

        //三挡验证，一分钟错误三次，限制五分钟。一小时发了十次，限制24小时
        checkCodeThreeLimit(phoneNumber,code);

        //通过identity和电话的redis key来找value
        String keyPre = generateKeyPreByIdentity(identity);
        String key = keyPre + phoneNumber;
        BoundValueOperations<String, String> codeRedis = redisTemplate.boundValueOps(key);
        String redisCode = codeRedis.get();

        if(StringUtils.isNotBlank(code)
                && StringUtils.isNotBlank(redisCode)
                && code.trim().equals(redisCode.trim())) {
            return ResponseResult.success(null);
        }else {
            return ResponseResult.fail(CommonStatusEnum.VERIFY_CODE_ERROR.getCode(), CommonStatusEnum.VERIFY_CODE_ERROR.getValue());
        }

    }

    /**
     * 根据身份类型生成对应的缓存key
     * @param identity
     * @return
     */
    private String generateKeyPreByIdentity(int identity){
        String keyPre = "";
        if (identity == IdentityConstant.PASSENGER){
            keyPre = VerifyCodeConstant.PASSENGER_LOGIN_KEY_PRE;
        }else if (identity == IdentityConstant.DRIVER){
            keyPre = VerifyCodeConstant.DRIVER_LOGIN_KEY_PRE;
        }
        return keyPre;
    }

    /**
     * 判断此手机号发送时限限制
     * @param phoneNumber
     * @return
     */
    private ResponseResult checkSendCodeTimeLimit(String phoneNumber){
        //判断是否有 限制1分钟，10分钟，24小时。
        return ResponseResult.success("");
    }

    /**
     * 三档验证校验
     * @param phoneNumber
     * @param code
     * @return
     */
    private ResponseResult checkCodeThreeLimit(String phoneNumber,String code){
        //看流程图

        return ResponseResult.success("");
    }
    /**
     * Test git
     **/

    private ResponseResult test(){
        return ResponseResult.success("");
    }


}
