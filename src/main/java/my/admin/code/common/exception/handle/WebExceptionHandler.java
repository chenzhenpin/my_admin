package my.admin.code.common.exception.handle;

import my.admin.code.common.exception.WebException;
import my.admin.code.common.utils.CommonUtils;
import my.admin.code.common.utils.MyLogUtils;
import my.admin.code.common.utils.ResData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
public class WebExceptionHandler {
    Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(WebException.class)
    public void WebExceptionHandler(HttpServletRequest request, HttpServletResponse response, WebException e)throws Exception {
        MyLogUtils.error(log, request, e);
        ResData error = ResData.fail().setCode(e.getCode()).setMsg(e.getMessage());
        CommonUtils.writeJson(response, error);
    }
}
