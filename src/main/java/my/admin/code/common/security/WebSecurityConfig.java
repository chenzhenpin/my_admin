package my.admin.code.common.security;

import my.admin.code.common.security.jwt.JWTUtils;
import my.admin.code.common.security.permission.AuthenticationTokenFilter;
import my.admin.code.common.security.permission.CustomAuthenticationEntryPoint;
import my.admin.code.common.security.permission.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled =true)//启用注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailHandler authenticationFailHandler;
    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/sys/user/register").permitAll()
            .antMatchers("/sys/user/home").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().authenticationEntryPoint(new CustomAuthenticationEntryPoint())//未登录同时是ajax
            .and()
            .formLogin()
            //.loginPage("/sys/user/login")//登录页面，用户未登录则会重定向到该地址。
            .loginProcessingUrl("/sys/user/dologin")//处理登录地址
            .successHandler(authenticationSuccessHandler)
            .failureHandler(authenticationFailHandler)
            //.failureForwardUrl("/sys/user/failHandler")//登录成功转发到该地址
            //.successForwardUrl("/sys/user/successHandler")//登录失败转发到该地址
            .permitAll()
            .and()
            .logout().logoutUrl("/sys/user/logout").logoutSuccessHandler(new LogoutHandler()).permitAll()
            .and().csrf().disable()
            //前后端分离采用JWT 不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())
            .and()
            .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
            .addFilter(new AuthenticationTokenFilter(authenticationManager(),customUserService(),jwtUtils));

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AccessDeniedHandler getAccessDeniedHandler() {
        return new AuthenticationAccessDeniedHandler();
    }
}
