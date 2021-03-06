package com.anderson.rodrigo.questionario.Questionario.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity

public class RespostaUsuario {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SQ_RESPOSTA_USUARIO")
	private Long id;

	@Column
	private String resposta;

	@Column
	private String respostaCerta;

	@ManyToOne
	private Usuario usuario;

	@ManyToOne
	private Questao questao;

	@Transient
	private Long idModulo;

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(final String resposta) {
		this.resposta = resposta;
	}

	public String getRespostaCerta() {
		return respostaCerta;
	}

	public void setRespostaCerta(final String respostaCerta) {
		this.respostaCerta = respostaCerta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(final Usuario usuario) {
		this.usuario = usuario;
	}

	public Questao getQuestao() {
		return questao;
	}

	public void setQuestao(final Questao questao) {
		this.questao = questao;
	}

	public Long getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(final Long idModulo) {
		this.idModulo = idModulo;
	}

}
