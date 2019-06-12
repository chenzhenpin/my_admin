package my.admin.code.common.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import my.admin.code.sys.entity.SysUser;
import my.admin.code.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

    @Autowired
    ISysUserService iSysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("acct_name",username);
        SysUser sysUser=iSysUserService.getOne(queryWrapper);
        System.out.println("账号："+username);
        if (sysUser==null){
            System.out.println("用户不存在");
            throw new UsernameNotFoundException("用户不存在");
        }
        SecurityUser securityUser=new SecurityUser(sysUser);
        return securityUser;
    }
}
