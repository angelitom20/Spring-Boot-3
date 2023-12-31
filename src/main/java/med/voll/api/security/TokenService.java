package med.voll.api.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import med.voll.api.usuarios.Usuario;

@Service
public class TokenService {
	
	@Value("${api.security.secret}")
	private String apiSecret;
	
	public Instant generarFechaExpiracion() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
	}
	
	public String generarToken(Usuario usuario) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret);
		    return JWT.create()
		        .withIssuer("voll med")
		        .withSubject(usuario.getLogin())
		        .withClaim("id", usuario.getId())
		        .withExpiresAt(generarFechaExpiracion())
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    // Invalid Signing configuration / Couldn't convert Claims.
			throw new RuntimeException();
		}
		
	}
	
	public String getSubject(String token) {
		if(token == null) {
			throw new RuntimeException();
		}
		DecodedJWT verifier = null;
		try {
		    Algorithm algorithm = Algorithm.HMAC256(apiSecret);
		    verifier = JWT.require(algorithm)
		        // specify an specific claim validations
		        .withIssuer("voll med")
		        .build()
		        .verify(token);
		    verifier.getSubject();
		} catch (JWTVerificationException exception){
		    System.out.println("A ocurrido una excepcion");
		}
		if(verifier.getSubject()==null || verifier == null) {
			throw new RuntimeException("Verifier invalido");
		}
		return verifier.getSubject();		
	}

}
