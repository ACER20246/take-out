package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.result.Result;
import com.sky.service.AuthService;
import com.sky.service.UserService;
import com.sky.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user/user")
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "微信登陆接口")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("微信登陆接口，参数：{}", userLoginDTO);
        //微信登录
        //登录成功后，生成jwt令牌,并返回userLoginVO
        UserLoginVO userLoginVO = authService.userLogin(userLoginDTO);
        return Result.success(userLoginVO);
    }
}
