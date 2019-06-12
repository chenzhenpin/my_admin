package my.admin.code.common.security;

import my.admin.code.common.utils.SpringUtil;
import my.admin.code.sys.entity.SysRole;
import my.admin.code.sys.entity.SysUser;
import my.admin.code.sys.service.ISysRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser extends SysUser implements UserDetails {
    SecurityUser(SysUser sysUser){
        BeanUtils.copyProperties(sysUser,this);
    }
    @Override
    public String getPassword() {
        return this.getAcctPassword();
    }


    @Override
    public String getUsername() {
        return this.getAcctName();
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
//        ISysRoleService sysRoleService =SpringUtil.getBean(ISysRoleService.class);
//        List<SysRole> roles = sysRoleService.getRolesByUserId(id);
//        for (SysRole role : roles) {
//            auths.add(new SimpleGrantedAuthority(role.getRoleCode()));
//        }
        auths.add(new SimpleGrantedAuthority("/sys/user/getUser"));
        return auths;
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否禁用
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return "1".equals(this.getAcctStatus())?true:false;
    }

}
