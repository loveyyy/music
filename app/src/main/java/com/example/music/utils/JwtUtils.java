package com.example.music.utils;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;

/**
 * Create By morningsun  on 2019-12-19
 */
public class JwtUtils {
    static final String SECRET = "ThisIsASecretXnpoolKey";//秘钥
    static final String TOKEN_PREFIX = "xnpool";


    public static Map<String,Object> validateToken(String token) {
        Map<String,Object> resp = new HashMap<String,Object>();

            Map<String, Object> body = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            Integer id = (Integer) body.get("id");
            String username = (String) (body.get("username"));
            Date generateTime = new Date((Long)body.get("generateTime"));
            String device = (String) (body.get("device"));
            resp.put("id",id);
            resp.put("username",username);
            resp.put("generateTime",generateTime);
            return resp;
    }
}
