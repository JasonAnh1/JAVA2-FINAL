package com.samsung.bookmanagerment.controller;


import com.samsung.bookmanagerment.configuration.Translator;
import com.samsung.bookmanagerment.entity.User;
import com.samsung.bookmanagerment.entity.response.BaseResponse;
import com.samsung.bookmanagerment.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@Transactional
public class AuthController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping ("v1/auth/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody final User request) {
        try {
            if(request == null || request.getPhone() == null || request.getPassword() == null) {
                throw new Exception(Translator.toLocale("required_fields"));
            }
            return ResponseEntity.ok( userService.signin(request));
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
    @PostMapping("v1/auth/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody final User request) {
        try {
            if(request == null
                    || request.getPhone() == null
                    || request.getPassword() == null
                    || request.getEmail() == null
                    || request.getName() == null) {
                throw new Exception(Translator.toLocale("required_fields"));
            }
//            return ResponseEntity.badRequest().body(new BaseResponse("Tính năng này đang tạm khoá", null));
            return ResponseEntity.ok( userService.signup(request));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }
    @GetMapping ("admin/v1/auth/test")
    public ResponseEntity<?> test() {
        try {

            return ResponseEntity.ok(passwordEncoder.encode("123456") );
        } catch (Exception ex) {
            return  ResponseEntity.badRequest().body(new BaseResponse(ex.getMessage(), null));
        }
    }

}
