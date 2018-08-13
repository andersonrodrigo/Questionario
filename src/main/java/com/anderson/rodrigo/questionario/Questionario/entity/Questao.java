package com.anderson.rodrigo.questionario.Questionario.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(
        name = "Questao.getQuestaoByModulo",
        query = "SELECT p FROM Questao p WHERE p.modulo.id = :idModulo") })
public class Questao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Modulo modulo;

    @ManyToOne
    private ImageQuestao imagemQuestao;

    @Column
    private String resposta;

    @Column
    private String tipoQuestao;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(final Modulo modulo) {
        this.modulo = modulo;
    }

    public ImageQuestao getImagemQuestao() {
        return imagemQuestao;
    }

    public void setImagemQuestao(final ImageQuestao imagemQuestao) {
        this.imagemQuestao = imagemQuestao;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(final String resposta) {
        this.resposta = resposta;
    }

    public String getTipoQuestao() {
        return tipoQuestao;
    }

    public void setTipoQuestao(final String tipoQuestao) {
        this.tipoQuestao = tipoQuestao;
    }

}
