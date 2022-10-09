package com.example.autoimpactanalysis.exception;

import lombok.Getter;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: ServiceException
 * @Author: kevin
 * @Date: 2022/6/14 09:31
 * @Description: 自定义异常
 * @Version: V1.0
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;
    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }
}
