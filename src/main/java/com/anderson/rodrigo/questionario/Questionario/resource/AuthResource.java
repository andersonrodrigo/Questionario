package com.anderson.rodrigo.questionario.Questionario.resource;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.rodrigo.questionario.Questionario.entity.Usuario;
import com.anderson.rodrigo.questionario.Questionario.security.TokenHelper;
import com.anderson.rodrigo.questionario.Questionario.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthResource {
	@Autowired
	private TokenHelper tokenHelper;
	

	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping(path="/token", method={RequestMethod.POST})
	public ResponseEntity<String> getToken(final HttpServletRequest request, final HttpServletResponse response,final String username, final String password){
		System.out.println("==================================");
		try {
			
			final Map<String, Object> user = autenticaUsuario(username, password);
			if (user == null){
			  throw new Exception("Acesso negado!");
			}
			final String token = tokenHelper.generateToken(username, user);
		    final HttpHeaders headers = new HttpHeaders();
	        headers.add("Authorization", "Bearer " + token);
	        return new ResponseEntity<String>(token, headers, HttpStatus.OK);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new AccessDeniedException(e.getMessage());
		}
	}
	
	
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param webserviceProdutoId2
	 * @param codigoGeosite
	 * @return
	 */
	private Map<String, Object> autenticaUsuario(final String username, final String password) {
		final Usuario usuario = usuarioService.recuperaUsuarioByLogin(username);
		Map<String, Object> retorno = null;
		if (usuario != null && usuario.getSenha().equals(password)) {
			retorno = new HashMap<String, Object>();
			retorno.put("userName", usuario.getNome());
			retorno.put("login", usuario.getLogin());
			retorno.put("id", usuario.getId());
		}
		return retorno;

	}

	@SuppressWarnings("unchecked")
	@RequestMapping( path="/refresh", method=RequestMethod.POST)
	public ResponseEntity<String> refreshToken(){
		try{
			if(SecurityContextHolder.getContext().getAuthentication() != null){
				final Map<String, Object> user = ((Map<String, Object>)SecurityContextHolder.getContext().getAuthentication().getPrincipal());

				final String token = tokenHelper.generateToken(user.get("login").toString(), user);

				final HttpHeaders headers = new HttpHeaders();
				headers.add("Authorization", "Bearer " + token);
				return new ResponseEntity<String>(token, headers, HttpStatus.OK);	
			}
		}catch (final Exception e) {
		}
		
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path="/user", method=RequestMethod.GET)
	public Map<String, Object> user(){
		return (Map<String, Object>)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@RequestMapping(path = "/salvarUsuario", method = RequestMethod.POST)
	public ResponseEntity<String> salvarUsuario(@RequestBody final Usuario usuario) {
		try {
			final Usuario usuarioExistente = usuarioService.recuperaUsuarioByLogin(usuario.getLogin());
			if (usuarioExistente == null) {
				usuarioService.salvarUsuario(usuario.getLogin(), usuario.getSenha(), usuario.getNome());
			} else {
				return new ResponseEntity<String>("Já existe Login cadastrado!", HttpStatus.OK);
			}
		} catch (final Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}
		return new ResponseEntity<String>("OK", HttpStatus.OK);

	}
	
	
	// @SuppressWarnings("unchecked")
	@RequestMapping(path="/logout", method=RequestMethod.POST)
	public ResponseEntity<String> logout(){
		// final Map<String, Object> user = (Map<String,
		// Object>)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", null);
		return new ResponseEntity<String>("", headers, HttpStatus.OK);
	}
	
	
}