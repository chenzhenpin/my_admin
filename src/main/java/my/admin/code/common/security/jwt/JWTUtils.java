package my.admin.code.common.security.jwt;


import my.admin.code.common.exception.WebException;
import my.admin.code.common.utils.ResData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JWTUtils {
    @Value("${jwtKey}")
    public  String jwtKey;
    @Value("${jwtExpire}")
    public  Integer expire;

    public String creatToken(String acctName){
        try {
            Map<String, byte[]> keys = RsaKeyHelper.generateKey(jwtKey);
            return JWTHelper.generateToken(acctName, keys.get("pri"), expire);
        } catch (Exception e) {
            throw  new WebException(ResData.NO_LOGIN_CODE,"登录错误");
        }
    }

    public  String verifyToken(String token)throws Exception{
        Map<String, byte[]> keys = RsaKeyHelper.generateKey(jwtKey);
        return JWTHelper.parserToken(token,keys.get("pub"));
    }

}
