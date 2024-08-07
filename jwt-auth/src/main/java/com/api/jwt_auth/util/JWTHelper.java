package com.api.jwt_auth.util;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
public class JWTHelper {
    public static String createToken(String scopes) {

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            long fiveHoursInMillis = 1000 * 60 * 60 * 5;
            Date expireDate = new Date(System.currentTimeMillis() + fiveHoursInMillis);
            String token = JWT.create()
                    .withSubject("apiuser")
                    .withIssuer("me@me.com")
                    .withClaim("scopes", scopes)
                    .withExpiresAt(expireDate)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            return null;
        }
    }
}
