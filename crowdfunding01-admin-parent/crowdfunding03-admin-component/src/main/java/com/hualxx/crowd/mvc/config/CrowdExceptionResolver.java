package com.hualxx.crowd.mvc.config;

import com.google.gson.Gson;
import com.hualxx.crowd.constant.CrowdConstant;
import com.hualxx.crowd.exception.LoginAcctAlreadyInUseException;
import com.hualxx.crowd.util.Crowdutil;
import com.hualxx.crowd.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author hual
 * @create 2020-11-12 2:41
 */

//@ControllerAdvice表示当前类是一个基于注解的异常处理类型
@ControllerAdvice
public class CrowdExceptionResolver {



    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveManthException(
            LoginAcctAlreadyInUseException e,
            HttpServletRequest req,
            HttpServletResponse res
    ) throws IOException {
        String viewName = "admin-add";
        return commonResolve(viewName, e, req, res);
    }

    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveManthException(
            ArithmeticException e,
            HttpServletRequest req,
            HttpServletResponse res
    ) throws IOException {
        String viewName = "system-error";
        return commonResolve(viewName, e, req, res);
    }

    //@ExceptionHandler 将一个具体的异常类型与一个方法关联起来
    @ExceptionHandler(NullPointerException.class)
    public ModelAndView resolveNullPointerExcetption(
            NullPointerException e,//实际捕获的异常
            HttpServletRequest req,//当前请求对象
            HttpServletResponse res) throws IOException {//当前响应对象

        String viewname = "system-error";//异常处理后要去的页面

        //1.判断当前请求类型
        boolean judgeResult = Crowdutil.judgeRequestType(req);

        return commonResolve(viewname,e,req,res);
    }

    private ModelAndView commonResolve(String viewname,Exception e,HttpServletRequest req,HttpServletResponse res) throws IOException {
        //1.判断当前请求类型
        boolean judgeResult = Crowdutil.judgeRequestType(req);

        //2.如果是Ajax请求
        if (judgeResult) {

            //3.创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(e.getMessage());

            //4.创建Gson对象
            Gson gson = new Gson();

            //5.将ResultEntity对象转换成JSON字符串
            String json = gson.toJson(resultEntity);

            //6.将JSON字符串作为响应体返回给浏览器
            res.getWriter().write(json);

            //7.由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        //8.如果不是Ajax请求则将创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();

        //9.将Exception对象存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,e);

        //10.设置对应的视图名称
        modelAndView.setViewName(viewname);

        return modelAndView;
    }
}
