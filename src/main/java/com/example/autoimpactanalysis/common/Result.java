package com.example.autoimpactanalysis.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project_name: autoimpactanalysis
 * @ClassName: Result
 * @Author: kevin
 * @Date: 2022/6/14 09:48
 * @Description: 包装类 接口统一返回类
 * @Version: V1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String code;
    private String message;
    private Object data;

    public static Result success(){
        return new Result(Constants.CODE_200,"处理成功",null);
    }

    public static Result success(Object data){
        return new Result(Constants.CODE_200,"处理成功", data);
    }

    public static Result error(String code,String message){
        return new Result(code,message,null);
    }

    public static Result error(){
        return new Result(Constants.CODE_500,"系统错误",null);
    }

}
