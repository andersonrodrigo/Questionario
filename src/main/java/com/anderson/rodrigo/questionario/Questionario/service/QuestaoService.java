package com.anderson.rodrigo.questionario.Questionario.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;
import java.util.Optional;

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
import com.anderson.rodrigo.questionario.Questionario.entity.Questao;
import com.anderson.rodrigo.questionario.Questionario.repositorio.ImageQuestaoRepositorio;
import com.anderson.rodrigo.questionario.Questionario.repositorio.QuestaoRepositorio;

@Service
public class QuestaoService {

    @Autowired
    QuestaoRepositorio questaoRepositorio;

    @Autowired
    ImageQuestaoRepositorio imageQuestaoRepositorio;

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

}
