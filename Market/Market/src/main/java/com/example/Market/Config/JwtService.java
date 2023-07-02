package com.example.Market.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//validate process
@Service
public class JwtService {
    private  static  final String SECRET_KEY="YlJpSH/3HjdB3pROM4eN5heOSBlc54StnRfLUR8+sCM/4BdRIw5ciNuBi0Pe2xrq" ;

    public String extractUsername(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    //extract any claim from my token
    //T : generic method accept any type
    public  <T> T extractClaim(String token , Function<Claims,T> claimsResolver){
        final  Claims claims =extractAllClaims(token) ;
       return claimsResolver.apply(claims);

    }

    //if we don't have extraClaims
    public String generateKey(UserDetails userDetails){
        return  generateToken( new HashMap<>(), userDetails);
    }

    public  String generateToken(UserDetails userDetails){
        return  generateToken(new HashMap<>(),userDetails);
    }
    public  String generateToken(
            Map<String,Object>extraClaims,
            UserDetails userDetails
    ){
        return  Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // to take the email
                .setIssuedAt(new Date(System.currentTimeMillis()))   // needed to calculate the date of expiration
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public  Boolean isTokenValid(String token , UserDetails userDetails ){
        final String username = extractUsername(token) ;
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
       return extractExpiration(token).before(new Date()) ;
    }


    private Date extractExpiration(String token) {
        return  extractClaim(token, Claims::getExpiration) ;
    }

    private Claims extractAllClaims(String token ){
       return Jwts.parserBuilder()
               .setSigningKey(getSignInKey()) //create the signature part of jwt thet verify
               // the content of the JWT if it has not been modified since its creation, and if the JWT has been issued by a reliable source.
               .build()
               .parseClaimsJws(token)
               .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY); //encodage en Base64
        return Keys.hmacShaKeyFor(keyBytes); // signer le le JWT
    }


}
