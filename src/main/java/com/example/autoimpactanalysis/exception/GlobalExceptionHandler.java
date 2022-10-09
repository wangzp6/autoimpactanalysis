package com.example.autoimpactanalysis.exception;

import com.example.autoimpactanalysis.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: ExceptionHandler
 * @Author: kevin
 * @Date: 2022/6/14 09:31
 * @Description: 全局异常处理器
 * @Version: V1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

     /**
      * @Auther: kevin
      * @param: se 业务异常
      * @Return Type:Result
      * @date: 2022/3/17 14:49
      * @describe: 如果拋出的是ServiceException，则调用该方法
      */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se) {
        return Result.error(se.getCode(), se.getMessage());

    }
}
