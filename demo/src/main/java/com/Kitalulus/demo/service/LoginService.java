package com.Kitalulus.demo.service;

import com.Kitalulus.demo.dto.responce.JwtRes;

public interface LoginService {

    JwtRes validateUserAndGetToken(String username, String pwd);

}
