package my.admin.code.common.security;

import my.admin.code.common.utils.CommonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled =true)//启用注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
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
                .antMatchers("/static/**").permitAll()
                .antMatchers("/sys/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                //.loginPage("/sys/user/login")//登录页面，用户未登录则会重定向到该地址。
                .loginProcessingUrl("/sys/user/dologin")//处理登录地址
                .failureForwardUrl("/sys/user/failHandler")//登录成功转发到该地址
                .successForwardUrl("/sys/user/successHandler")//登录失败转发到该地址
                .permitAll()
                .and()
                .logout().logoutUrl("/sys/user/logout").logoutSuccessHandler(new LogoutHandler()).permitAll()
                .and().exceptionHandling().accessDeniedHandler(getAccessDeniedHandler())
                .and().csrf().disable();

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
