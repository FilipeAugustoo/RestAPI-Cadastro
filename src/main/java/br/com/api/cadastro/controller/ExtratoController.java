package br.com.api.cadastro.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.cadastro.model.Extrato;
import br.com.api.cadastro.model.ExtratoEnviado;
import br.com.api.cadastro.service.ExtratoService;

@RestController
@RequestMapping("/extrato")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class ExtratoController {


    @Autowired
    private ExtratoService extratoService;

    @GetMapping("{user}")
    public ResponseEntity<List<Extrato>> teste(@PathVariable String user) {
        return extratoService.buscaExtratoPorUsuario(user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Extrato adiciona(@Valid @RequestBody ExtratoEnviado extratoEnviado) {    
        return extratoService.salvar(extratoEnviado);
    }
    
}
