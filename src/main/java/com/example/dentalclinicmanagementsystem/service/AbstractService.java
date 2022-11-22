package com.example.dentalclinicmanagementsystem.service;

import com.example.dentalclinicmanagementsystem.exception.TokenException;
import io.jsonwebtoken.Jwts;
import org.springframework.util.StringUtils;

import java.util.List;

public abstract class AbstractService {

    public String genCode(String fullName){
        String[] splitName = fullName.split(" ");
        StringBuilder code = new StringBuilder();
        for (String name : splitName) {
            code.append(name.trim().charAt(0));
        }

        return code.toString();
    }

    public String setUsername(String code, List<String> usernames){

        int maxUser = 1;
        for(String name : usernames){
            try {
                int number = Integer.parseInt(name.substring(code.length()));
                if(number >= maxUser) {
                    maxUser = number + 1;
                }
            }catch (NumberFormatException numberFormatException){

            }
        }
        return code + maxUser;
    }

    public Long getUserId(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        } else {
            throw new TokenException("Token invalid");
        }

        return Long.parseLong(Jwts.parser()
                .setSigningKey("dental_clinic")
                .parseClaimsJws(token)
                .getBody().getId());
    }

}
