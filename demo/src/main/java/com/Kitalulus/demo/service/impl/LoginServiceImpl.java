package com.Kitalulus.demo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.Kitalulus.demo.controller.exceptions.DomainException;
import com.Kitalulus.demo.dto.responce.JwtRes;
import com.Kitalulus.demo.service.LoginService;
import com.Kitalulus.demo.utill.JWTUtill;

@Service
public class LoginServiceImpl implements LoginService {
    Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    private final JWTUtill jwtUtill;
    @Value("${jwt.token.expiry:10}")
    Long tokenExpirySec;

    public LoginServiceImpl(JWTUtill jwtUtill) {
        this.jwtUtill = jwtUtill;
    }

    @Override
    public JwtRes validateUserAndGetToken(String username, String pwd) {
        // currently this static user used for login. only jwt token implemented, not
        // used Oauth for demo
        if (username.equals("abc123") && pwd.equals("123456")) {
            JwtRes response = new JwtRes();
            response.setAccessToken(jwtUtill.generateJwtToken(username));
            response.setExpiresIn(tokenExpirySec);
            return response;
        } else {
            throw new DomainException(HttpStatus.UNAUTHORIZED, "401", "Invalid credentials");
        }
    }

}
