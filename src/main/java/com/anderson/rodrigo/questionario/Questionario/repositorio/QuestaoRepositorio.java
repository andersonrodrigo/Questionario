package com.anderson.rodrigo.questionario.Questionario.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anderson.rodrigo.questionario.Questionario.entity.Questao;

public interface QuestaoRepositorio extends JpaRepository<Questao, Long> {

    List<Questao> getQuestaoByModulo(@Param("idModulo") Long idModulo);

	@Query(nativeQuery = true, value = "SELECT  COUNT(1) FROM RESPOSTA_USUARIO WHERE USUARIO_ID = :idUsuario and resposta = resposta_certa ")
	Long quantidadeCertas(@Param("idUsuario") Long idUsuarioo);

	@Query(nativeQuery = true, value = "SELECT  COUNT(1) FROM RESPOSTA_USUARIO WHERE USUARIO_ID = :idUsuario and resposta <> resposta_certa ")
	Long quantidadeErradas(@Param("idUsuario") Long idUsuarioo);


}
