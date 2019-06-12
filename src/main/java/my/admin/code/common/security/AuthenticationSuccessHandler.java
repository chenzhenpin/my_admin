package my.admin.code.common.security;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSON;
import my.admin.code.common.security.jwt.JWTUtils;
import my.admin.code.common.utils.ResData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 登录成功处理类
 */

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    private JWTUtils jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        response.setContentType("text/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(JSON.toJSONString(ResData.ok().setMsg("登录成功").setData(jwtUtils.creatToken(username))));
    }
}
