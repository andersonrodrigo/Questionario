package com.anderson.rodrigo.questionario.Questionario.resource;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anderson.rodrigo.questionario.Questionario.entity.ImageQuestao;
import com.anderson.rodrigo.questionario.Questionario.entity.Questao;
import com.anderson.rodrigo.questionario.Questionario.service.QuestaoService;

@RestController

@RequestMapping("/api/questao")
public class QuestaoResource {

    @Autowired
    private QuestaoService questaoService;


    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/salvarImagemQuestao")
    public ImageQuestao salvarImagemQuestao(@RequestParam("file") final MultipartFile file)
        throws Exception {
        return questaoService.saveImageQuestao(file);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/salvarImagensQuestao")
    public ImageQuestao salvarImagensQuestao(@RequestParam("file") final MultipartFile[] file)
        throws Exception {
        return questaoService.saveImagensQuestao(file);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/salvar")
    public Questao salvar(@RequestBody final Questao questao) throws Exception {
        return questaoService.salvar(questao);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/getQuestaoById/{id}")
    public Optional<Questao> getModuloById(@PathVariable("id") final Long id) {
        return questaoService.findById(id);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/getQuestaoByModulo/{id}")
    public List<Questao> getQuestaoByModulo(@PathVariable("id") final Long id) {
        return questaoService.getQuestaoByModulo(id);
    }

    @Transactional
    @RequestMapping(method = RequestMethod.GET, path = "/getAllQuestoes")
    public List<Questao> getAllModulos() {
        return questaoService.findAll();
    }


}
