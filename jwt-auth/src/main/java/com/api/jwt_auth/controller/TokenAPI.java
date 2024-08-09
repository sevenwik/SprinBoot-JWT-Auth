package com.api.jwt_auth.controller;

import com.api.jwt_auth.models.Token;
import com.api.jwt_auth.util.JWTHelper;

public class TokenAPI {
    public static Token appUserToken;


    public static Token getAppUserToken(String userName) {
        String token_string = JWTHelper.createToken(userName);
        appUserToken = new Token(token_string);
        return appUserToken;
    }
}
