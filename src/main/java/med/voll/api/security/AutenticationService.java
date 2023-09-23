package med.voll.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import med.voll.api.usuarios.UsuarioRepository;

@Service
public class AutenticationService implements UserDetailsService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return usuarioRepository.findByLogin(username);
	}

}