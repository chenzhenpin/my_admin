package my.admin.code.common.security;


import com.alibaba.fastjson.JSON;
import my.admin.code.common.utils.ResData;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登录失败处理
 */

@Component
public class AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            writer.println(JSON.toJSONString(ResData.fail().setMsg("用户名或密码错误")));
        } else if (e instanceof DisabledException) {
            writer.println(JSON.toJSONString(ResData.fail().setMsg("账户被禁用，请联系管理员")));
        } else if (e instanceof LockedException){
            writer.println(JSON.toJSONString(ResData.fail().setMsg(((LockedException) e).getMessage())));
        }else {
            writer.println(JSON.toJSONString(ResData.fail().setMsg("登录失败，内部错误")));
        }
    }

}
