package com.industrialplatform.beta.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static  final  String secretkey="@w%g7y*4uiU2H1K#J5kjk&";

    //生成token
    public static  String getToken(Map<String,String> map){

        Calendar instance= Calendar.getInstance();
        instance.add(Calendar.DATE,1);
        //创建jwt builder
        JWTCreator.Builder builder= JWT.create();
        //payload
        map.forEach(builder::withClaim);

        String token=builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(secretkey));//signature
        return token;
    }

    //验证token合法性
    public  static  void verify(String token){
        JWT.require(Algorithm.HMAC256(secretkey)).build().verify(token);
    }

    //获取token信息
    public  static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify=JWT.require(Algorithm.HMAC256(secretkey)).build().verify(token);
        return verify;
    }
}
