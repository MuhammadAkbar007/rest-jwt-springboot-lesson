package uz.pdp.apprestjwt.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    static long expireTime = 36_000_000; // (1000 => 1 sek) [10 hour]
    static String keyWord = "BuTokenningMaxfiySuziHechKimBilmasin1234567890";

    public String generateToken(String username) {

        Date expDate = new Date(System.currentTimeMillis() + expireTime);

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, keyWord)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(keyWord)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(keyWord)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
