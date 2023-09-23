package med.voll.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.usuarios.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//System.out.println("El filtro esta siendo llamado");
		var cabecera = request.getHeader("Authorization");//.replace("Bearer","");//Authorization es el nombre del header
		//si la cebecera no llega null
		if(cabecera != null) {
			var token = cabecera.replace("Bearer ","");
			System.out.println(token);
			System.out.println(tokenService.getSubject(token));
			var nombreUsuario = tokenService.getSubject(token);
			if(nombreUsuario != null) {
				//token valido
				var usuario = usuarioRepository.findByLogin(nombreUsuario);
				var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());//forzamos un inicio de session
				SecurityContextHolder.getContext().setAuthentication(authentication);			}
		}
		filterChain.doFilter(request, response);
	}

	
	
}
