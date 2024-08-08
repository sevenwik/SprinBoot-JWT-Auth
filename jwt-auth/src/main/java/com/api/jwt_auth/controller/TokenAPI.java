package com.api.jwt_auth.controller;

import com.api.jwt_auth.models.Token;
import com.api.jwt_auth.util.JWTHelper;

public class TokenAPI {
    public static Token appUserToken;


    public static Token getAppUserToken() {
        if(appUserToken == null || appUserToken.getToken() == null || appUserToken.getToken().length() == 0) {
            appUserToken = createToken("ApiClientApp");
        }
        return appUserToken;
    }

    private static Token createToken(String username) {
        String scopes = "com.webage.data.apis";
        // special case for application user
        if( username.equalsIgnoreCase("ApiClientApp")) {
            scopes = "com.webage.auth.apis";
        }
        String token_string = JWTHelper.createToken(scopes);

        return new Token(token_string);
    }
}
