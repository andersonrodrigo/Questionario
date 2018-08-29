package com.anderson.rodrigo.questionario.Questionario.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.anderson.rodrigo.questionario.Questionario.entity.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

	List<Usuario> recuperaUsuarioByLogin(@Param("login") String login);

}
