package com.anderson.rodrigo.questionario.Questionario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anderson.rodrigo.questionario.Questionario.entity.Usuario;
import com.anderson.rodrigo.questionario.Questionario.repositorio.UsuarioRepositorio;

@Service
public class UsuarioService {
	@Autowired
	UsuarioRepositorio usuarioRepositorio;

	/**
	 * Recupera o usuario pelo login
	 * 
	 * @param login
	 * @return
	 */
	public Usuario recuperaUsuarioByLogin(final String login) {
		final List<Usuario> listaUsuario = usuarioRepositorio.recuperaUsuarioByLogin(login);
		if (listaUsuario != null && !listaUsuario.isEmpty()) {
			return listaUsuario.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @param login
	 * @param senha
	 */
	public void salvarUsuario(final String login, final String senha, final String nome) {
		final Usuario usuario = new Usuario();
		usuario.setLogin(login);
		usuario.setSenha(senha);
		usuario.setNome(nome);
		usuarioRepositorio.save(usuario);
	}

}
