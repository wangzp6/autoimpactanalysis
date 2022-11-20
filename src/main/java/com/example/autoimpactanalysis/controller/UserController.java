package com.example.autoimpactanalysis.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.autoimpactanalysis.common.Constants;
import com.example.autoimpactanalysis.common.Result;
import com.example.autoimpactanalysis.entity.VO.UserVO;
import com.example.autoimpactanalysis.entity.User;
import com.example.autoimpactanalysis.service.IUserService;
import com.example.autoimpactanalysis.utils.JsonUtils;
import com.example.autoimpactanalysis.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName: UserController
 * @Author: kevin
 * @Date: 2022-06-14
 * @Version: V1.0
 * @Description: User 前端控制器 文件上传相关接口
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //全量查询
    @GetMapping("/findAll")
    public Result findAll() {
        log.info("进入user/findAll方法");
        return Result.success(userService.list());
    }

    //根据ID查询
    @GetMapping("/findById/{userId}")
    public Result findById(@PathVariable String userId) {
        log.info("进入findById方法");
        return Result.success(userService.getById(userId));
    }

    //根据username查询
    @GetMapping("/findByUserName/{userName}")
    public Result findByUserName(@PathVariable String userName) {
        log.info("进入userfindByUserName方法");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        return Result.success(userService.getOne(queryWrapper));
    }

    //分页查询
    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String userName,
                           @RequestParam(defaultValue = "") String email,
                           @RequestParam(defaultValue = "") String nickName,
                           @RequestParam(defaultValue = "") String address) {
        log.info("进入user/findPage方法");
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (!"".equals(userName)) {
            queryWrapper.like("user_name", userName);
        }
        if (!"".equals(email)) {
            queryWrapper.like("email", email);
        }
        if (!"".equals(nickName)) {
            queryWrapper.like("nick_name", nickName);
        }
        if (!"".equals(address)) {
            queryWrapper.like("address", address);
        }
        queryWrapper.orderByDesc("user_id");

        User currentUser = TokenUtils.getCurrentUser();
        log.info("当前用户信息：" + currentUser.getNickName());
        return Result.success(userService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    //新增或修改
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        log.info("进入user/save方法");
        return Result.success(userService.saveOrUpdate(user));
    }

    //根据ID删除
    @DeleteMapping("/delete/{userId}")
    public Result delete(@PathVariable String userId) {
        log.info("进入user/delete方法");
        return Result.success(userService.removeById(userId));
    }

    //批量删除
    @PostMapping("/deleteBatch/")
    public Result deleteBatch(@RequestBody List<String> userIds) {
        log.info("进入user/deleteBatch方法");
        return Result.success(userService.removeBatchByIds(userIds));
    }

    //导出
    @GetMapping("/userExport")
    public void userExport(HttpServletResponse response) throws Exception {
        List<User> list = userService.list();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);
        //设置浏览器的响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    //导入
    @PostMapping("/userImport")
    public Result userImport(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> list = reader.readAll(User.class);
        log.info(list.toString());
        return Result.success(userService.saveBatch(list));
    }

    //登录
    @PostMapping("/login")
    public Result login(@RequestBody UserVO userVO) {
        log.info("进入user/login方法");
        String username = userVO.getUserName();
        String password = userVO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            log.info("用户名或者密码为空");
            return Result.error(Constants.CODE_400, "参数错误");
        }
        //不从数据库验证用户
        /*if("admin".equals(username)&&"123456".equals(password)){
            UserDTO dto = userService.login(userDTO);
            log.info("dto:"+dto.toString());
            return Result.success(dto);
        }else{
            return Result.error(Constants.CODE_600,"用户名密码错误");
        }*/
        UserVO dto = userService.login(userVO);
        dto.setPassword("");
        return Result.success(dto);
    }

    //授权
    @GetMapping("/permission/{userName}")
    public Result permission(@PathVariable String userName) {
        log.info("进入user/permission方法");
        String json;
        if("kfadmin".equals(userName)){
            log.info("获取kfadmin菜单");
            json = "src/main/resources/permission/kf_permission.json";
        }else{
            json = "src/main/resources/permission/other_permission.json";
        }

        File jsonFile = new File(json);
        //通过上面那个方法获取json文件的内容
        String jsonData = JsonUtils.getStr(jsonFile);
        //转json对象
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        log.info("jsonObject:" + jsonObject);
        return Result.success(jsonObject.get("data"));
    }

    //注册
    @PostMapping("/register")
    public Result register(@RequestBody UserVO userVO) {
        log.info("进入user/register方法");
        String username = userVO.getUserName();
        String password = userVO.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            log.info("用户名或者密码为空");
            return Result.error(Constants.CODE_400, "参数错误");
        }
        return Result.success(userService.register(userVO));
    }

}

