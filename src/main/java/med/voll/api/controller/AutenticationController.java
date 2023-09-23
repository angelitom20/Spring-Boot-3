package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.security.DatosJWTToken;
import med.voll.api.security.TokenService;
import med.voll.api.usuarios.DatosAutenticationUsuario;
import med.voll.api.usuarios.Usuario;



@RestController
@RequestMapping("/login")
public class AutenticationController {
	
	@Autowired
	private AuthenticationManager autenticationManager;
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity autenticarUsuario(@RequestBody DatosAutenticationUsuario datosAutenticationUsuario) {
		
		Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticationUsuario.login(), datosAutenticationUsuario.clave());
		var usuarioAuntenticado = autenticationManager.authenticate(authToken);
		var JWTtoken = tokenService.generarToken((Usuario)usuarioAuntenticado.getPrincipal());
		return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
		
	}
	
}
