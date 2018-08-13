package com.anderson.rodrigo.questionario.Questionario.resource;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.anderson.rodrigo.questionario.Questionario.entity.Modulo;
import com.anderson.rodrigo.questionario.Questionario.repositorio.ModuloRepositorio;

@RestController

@RequestMapping("/api/modulo")
public class ModuloResource {

    @Autowired
    private ModuloRepositorio moduloRepositorio;

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/salvar")
    public Modulo salvar(@RequestBody final Modulo modulo) throws Exception {
        return moduloRepositorio.save(modulo);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getModuloById/{id}")
    public Optional<Modulo> getModuloById(@PathVariable("id") final Long id) {
        return moduloRepositorio.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/getAllModulos")
    public List<Modulo> getAllModulos() {
        return moduloRepositorio.findAll();
    }
}
