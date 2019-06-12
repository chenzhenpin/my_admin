package my.admin.code.common.security.permission;

import com.alibaba.fastjson.JSON;
import my.admin.code.common.security.jwt.JWTUtils;
import my.admin.code.common.utils.ResData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticationTokenFilter extends BasicAuthenticationFilter {


    private UserDetailsService userService; //用户信息service
    private JWTUtils jwtUtils;


    public AuthenticationTokenFilter(AuthenticationManager authenticationManager,UserDetailsService userService, JWTUtils jwtUtils) {
        super(authenticationManager);
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("token");//获取header中的验证信息
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String authToken = authHeader.substring("Bearer ".length());
            String username = null;//从token中获取用户信息，jwtUtils自定义的token加解密方式
            try {
                username = jwtUtils.verifyToken(authToken);
            } catch (Exception e) {
                //未登录
                SecurityContextHolder.getContext().setAuthentication(null);
                response.setContentType("text/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                writer.println(JSON.toJSONString(ResData.fail().setCode(ResData.NO_LOGIN_CODE).setMsg("token 失效")));
                return;
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(username);//根据用户名获取用户对象
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //设置为已登录
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

