package cn.zhanx.project05.controller;

import cn.zhanx.project05.domain.LoginUser;
import cn.zhanx.project05.domain.User;
import cn.zhanx.project05.service.LoginService;
import cn.zhanx.project05.vo.common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Api(value = "登录登出相关的controller", tags = "登录登出相关的接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation("登录")
    @PostMapping("/user/login")
    public Result<Map<String,String>> login(@RequestBody User user){
        return loginService.login(user);
    }

    @ApiOperation("登出")
    @GetMapping("/user/logout")
    public Result<String> logout(){
        return loginService.logout();
    }
}
