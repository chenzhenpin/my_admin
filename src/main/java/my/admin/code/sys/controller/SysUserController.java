package my.admin.code.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSON;
import my.admin.code.common.utils.ResData;
import my.admin.code.sys.entity.SysUser;
import my.admin.code.sys.form.RegisterForm;
import my.admin.code.sys.service.ISysUserService;
import my.admin.code.sys.validator.RegisterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author chen
 * @since 2019-05-31
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    RegisterValidator registerValidator;

    @PostMapping("/register")
    public ResData register(@Valid RegisterForm registerForm, BindingResult result){
        registerValidator.validate(registerForm,result);
        if (result.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            List<ObjectError>list= result.getAllErrors();
            for (ObjectError objectError: list){
                map.put(((FieldError)objectError).getField(),objectError.getDefaultMessage());
            }
            return  ResData.fail().setData(map);
        }
        String bcryptPwd=new BCryptPasswordEncoder().encode(registerForm.getPassword());
        SysUser sysUser = new SysUser();
        sysUser.setAcctName(registerForm.getUsername());
        sysUser.setRealName(registerForm.getName());
        sysUser.setAcctPassword(bcryptPwd);
        sysUserService.save(sysUser);
        return ResData.ok();
    }

    @RequestMapping("/logoutHandler")
    public ResData logout(HttpSession session){
        //session失效
        session.invalidate();
        return ResData.ok().setMsg("退出成功");
    }
//    @RequestMapping("/successHandler")
//    public ResData successHandler() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal=authentication.getDetails();
//        return ResData.ok().setMsg("登录成功").setData(((JSON)JSON.toJSON(principal)).getString("sessionId"));
//    }
//    @RequestMapping("/failHandler")
//    public ResData failHandler() {
//        return ResData.fail().setMsg("登录失败");
//    }
    @RequestMapping("/home")
    public ResData home() {
        return ResData.ok().setMsg("hello word");
    }


    @RequestMapping("/getUser")
    public ResData getUser() {
        return ResData.ok().setData(sysUserService.list());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/admin")
    public ResData admin() {
        return ResData.ok().setMsg("I an admin");
    }
}
