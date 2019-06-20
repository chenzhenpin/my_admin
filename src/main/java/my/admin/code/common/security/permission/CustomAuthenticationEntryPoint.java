package my.admin.code.common.security.permission;

import com.alibaba.fastjson.JSON;
import my.admin.code.common.utils.ResData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
       response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(JSON.toJSONString(ResData.fail().setCode(ResData.NO_LOGIN_CODE).setMsg("未登录")));
    }
}

