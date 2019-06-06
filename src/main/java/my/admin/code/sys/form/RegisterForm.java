package my.admin.code.sys.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegisterForm {
    @NotBlank(message = "姓名不能为空")
    private String name;
    @Size(min=2, max=30,message = "账号长度2-30")
    private String username;
    @Length(min=6,max=20,message="密码必须在6到20位之间")
    private  String password;

}
