package com.sistema.blog.seguridad;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.security.SignatureException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.sistema.blog.excepciones.BlogAppException;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private int jwtExpirationInMs;
	
	public String generarToken(Authentication authentication) {
		String username = authentication.getName();
		Date fechaActual = new Date();
		Date fechaExpiracion = new Date(fechaActual.getTime() + jwtExpirationInMs);
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		String token = Jwts.builder()
				.claim("sujeto", username)
				.claim("emite", fechaActual.getTime() / 1000)
				.claim("expira", fechaExpiracion.getTime() / 1000)
				.signWith(key)
				.compact();
		
		return token;
	}
	
	/*De momento no sé cómo solucionar los métodos obsoletos
	 * en la versión 0.12.6 de io.jsonwebtoken jjwt-api...*/
	
	public String obtenerUsernameDelJWT(String token) {
		Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		Claims claims = Jwts.parser()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validarToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token);
			return true;
		// Eliminado el catch inalcanzable de SignatureException
		}
		 catch (MalformedJwtException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no válida");
		}
		 catch (ExpiredJwtException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT caducado");
		}
		 catch (UnsupportedJwtException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "Token JWT no compatible");
		}
		 catch (IllegalArgumentException e) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "La cadena claims JWT está vacía");
		}
	}
}
