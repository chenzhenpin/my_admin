package my.admin.code.common.security;

import com.alibaba.fastjson.JSON;
import my.admin.code.common.utils.ResData;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationAccessDeniedHandler implements AccessDeniedHandler {

     @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        //response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(JSON.toJSONString(ResData.fail().setCode(ResData.NO_AUTH_CODE).setMsg("没有该权限")));
    }

}
