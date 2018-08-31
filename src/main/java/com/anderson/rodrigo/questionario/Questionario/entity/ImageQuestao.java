package com.anderson.rodrigo.questionario.Questionario.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

@Entity
public class ImageQuestao {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "SQ_IMAGEM_QUESTAO")
    private Long id;

    @Lob
    private byte[] content;

    public byte[] getContent() {
        return content;
    }

    public void setContent(final byte[] content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
