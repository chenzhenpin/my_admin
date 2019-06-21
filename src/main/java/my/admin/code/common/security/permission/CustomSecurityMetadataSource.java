package my.admin.code.common.security.permission;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 返回需要判断的资源
 */
@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    //所有需要权限的url
    private List<String> allUrl=null;
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (allUrl==null){
            loadResourceDefine();
        }
        FilterInvocation fi = (FilterInvocation) object;
        String reqURL=fi.getRequestUrl();
        PathMatcher pathMatcher = new AntPathMatcher();
        List<ConfigAttribute> urls=null;
        for (String url:allUrl){
            if (StringUtils.isNotBlank(url)&&pathMatcher.match(url,reqURL)){
                urls=new ArrayList<>();
                urls.add(new SecurityConfig(url));
            }
        }
        //需要的权限,返回null 不走CustomAccessDecisionManager；
        return urls;
    }
     public void loadResourceDefine(){
        allUrl=new ArrayList<>();
        allUrl.add("/sys/user/getUser");
     }
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}