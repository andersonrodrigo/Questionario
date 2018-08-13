package com.anderson.rodrigo.questionario.Questionario.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.anderson.rodrigo.questionario.Questionario.entity.Questao;

public interface QuestaoRepositorio extends JpaRepository<Questao, Long> {

    List<Questao> getQuestaoByModulo(@Param("idModulo") Long idModulo);

}
