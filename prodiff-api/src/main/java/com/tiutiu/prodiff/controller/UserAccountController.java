package com.tiutiu.prodiff.controller;

import com.tiutiu.prodiff.constant.UserAccountConstant;
import com.tiutiu.prodiff.dto.UserAccountLoginDTO;
import com.tiutiu.prodiff.dto.UserAccountRegisterDTO;
import com.tiutiu.prodiff.entity.UserAccount;
import com.tiutiu.prodiff.vo.UserAccountLoginVO;
import com.tiutiu.prodiff.result.Result;
import com.tiutiu.prodiff.service.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "用户账户")
@Slf4j
public class UserAccountController {
    @Resource
    UserAccountService userAccountService;
    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<UserAccountLoginVO> login(@RequestBody UserAccountLoginDTO userAccountLoginDTO){
        UserAccount userAccount = userAccountService.login(userAccountLoginDTO);
        if(userAccount == null){
            return Result.fail(UserAccountConstant.LOGIN_ERROR);
        }
        String token = userAccountService.buildToken(userAccount);
        UserAccountLoginVO userAccountLoginVO = UserAccountLoginVO.builder()
                .id(userAccount.getId())
                .email(userAccount.getEmail())
                .username(userAccount.getUsername())
                .token(token)
                .build();
        return Result.success(userAccountLoginVO);
    }
    @PostMapping("/verificationCode")
    @Operation(summary = "获取验证码")
    public Result<Void> askForVerificationCode(@RequestParam String email, HttpServletRequest request){
        userAccountService.sendEmailVerificationCode(email, request.getRemoteAddr());
        return Result.success(null);
    }
    @PostMapping("/register")
    @Operation(summary = "注册")
    public Result<Void> register(@RequestBody UserAccountRegisterDTO userAccountRegisterDTO){
        String msg = userAccountService.register(userAccountRegisterDTO);
        if(msg != null){
            return Result.fail(msg);
        }
        return Result.success(null);
    }
    @PostMapping("/logout")
    @Operation(summary = "登出")
    public Result<Void> logout(){
        userAccountService.logout();
        return Result.success(null);
    }
}
