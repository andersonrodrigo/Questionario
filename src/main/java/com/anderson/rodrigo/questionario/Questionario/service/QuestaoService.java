package com.anderson.rodrigo.questionario.Questionario.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.anderson.rodrigo.questionario.Questionario.entity.ImageQuestao;
import com.anderson.rodrigo.questionario.Questionario.entity.Pontuacao;
import com.anderson.rodrigo.questionario.Questionario.entity.Questao;
import com.anderson.rodrigo.questionario.Questionario.entity.RespostaUsuario;
import com.anderson.rodrigo.questionario.Questionario.entity.Usuario;
import com.anderson.rodrigo.questionario.Questionario.repositorio.ImageQuestaoRepositorio;
import com.anderson.rodrigo.questionario.Questionario.repositorio.QuestaoRepositorio;
import com.anderson.rodrigo.questionario.Questionario.repositorio.RespostaUsuarioRepositorio;

@Service
public class QuestaoService {

    @Autowired
    QuestaoRepositorio questaoRepositorio;

    @Autowired
    ImageQuestaoRepositorio imageQuestaoRepositorio;

	@Autowired
	RespostaUsuarioRepositorio respostaUsuarioRepositorio;

    @PersistenceContext
    private EntityManager em;

    /**
     * Permet de créer un Blob à partir d'un stream sans indiquer/connaître la taille. Fonctionne
     * uniquement avec jdbc4. Une exception est
     * lancée si jdbc4 n'est pas présent.
     * 
     * @param aInputStream
     * @return
     */
    public Blob createBlob(final InputStream aInputStream) {

        final LobCreator lobCreator = Hibernate.getLobCreator((Session) em.getDelegate());
        // // JDBC4 -> pas besoin de la taille
        // if (lobCreator instanceof ContextualLobCreator) {
        // // Passage de -1 comme taille: De toutes façons cette valeur n'est pas utilisée en jdbc4
        // return lobCreator.createBlob(aInputStream, -1);
        // }
        // else {
        // Fallback JDBC3
        // On récupère le stream pour connaitre la taille
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(aInputStream, bos);
            return lobCreator.createBlob(new ByteArrayInputStream(bos.toByteArray()), bos.size());
        } catch (final IOException ioe) {
            throw new RuntimeException(ioe);
        }
        // }
    }


    /**
     * 
     * @param id
     * @return
     */
    public Optional<Questao> findById(final Long id) {
        return questaoRepositorio.findById(id);
    }

    /**
     * 
     * @param id
     * @return
     */
    public List<Questao> getQuestaoByModulo(final Long idModulo) {
        return questaoRepositorio.getQuestaoByModulo(idModulo);
    }

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Questao getProximaQuestaoByModulo(final Long idModulo) {
		final List<Questao> listaQuestao = questaoRepositorio.getQuestaoByModulo(idModulo);
		if (!listaQuestao.isEmpty()) {
			if (listaQuestao.size() == 1) {
				return listaQuestao.get(0);
			} else {
				final Random gerador = new Random();
				return listaQuestao.get(gerador.nextInt((listaQuestao.size() - 1)));
			}
		} else {
			return null;
		}

	}

    /**
     * 
     * @return
     */
    public List<Questao> findAll() {
        return questaoRepositorio.findAll();
    }

    /**
     * 
     * @param file
     * @return
     */
    public ImageQuestao saveImageQuestao(final MultipartFile file) {
        final ImageQuestao imageQuestao = new ImageQuestao();
        try {
            imageQuestao.setContent(file.getBytes());
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageQuestaoRepositorio.save(imageQuestao);
    }

    /**
     * 
     * @param questao
     * @return
     */
    public Questao salvar(final Questao questao) {
        return questaoRepositorio.save(questao);
    }


    /**
     * 
     * @param file
     * @return
     */
    public ImageQuestao saveImagensQuestao(final MultipartFile[] file) {
        final ImageQuestao imageQuestao = new ImageQuestao();
        try {
            imageQuestao.setContent(file[0].getBytes());
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return imageQuestaoRepositorio.save(imageQuestao);

    }

	/**
	 * 
	 * @param respostaUsuario
	 * @return
	 */
	public Questao salvaResposta(final RespostaUsuario respostaUsuario) {
		respostaUsuarioRepositorio.save(respostaUsuario);
		System.out.println("Modulo que chegoiu" + respostaUsuario.getIdModulo());
		final List<Questao> listasQuestoes = questaoRepositorio.getQuestaoByModulo(respostaUsuario.getIdModulo());
		System.out.println("achou :" + listasQuestoes.size());
		int cont = 0;
		Questao questaoSelecionada = null;
		int indice = 0;
		while (questaoSelecionada == null) {
			final Random gerador = new Random();
			indice = gerador.nextInt(listasQuestoes.size() - 1);
			System.out.println(indice);
			if (indice > 0) {
				questaoSelecionada = listasQuestoes.get(indice);
			}
			if (cont > 5) {
				break;
			}
			cont++;
			if (questaoSelecionada != null
					&& questaoSelecionada.getId().intValue() == respostaUsuario.getQuestao().getId().intValue()) {
				questaoSelecionada = null;
			}
		}
		return questaoSelecionada;

	}

	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public Pontuacao pontuacaoUsuario(final Usuario usuario) {
		final Pontuacao pontuacao = new Pontuacao();
		pontuacao.setCertas(questaoRepositorio.quantidadeCertas(usuario.getId()));
		pontuacao.setErradas(questaoRepositorio.quantidadeErradas(usuario.getId()));
		return pontuacao;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteQuestao(final Long id) {
		questaoRepositorio.deleteById(id);
		return true;
	}

}
