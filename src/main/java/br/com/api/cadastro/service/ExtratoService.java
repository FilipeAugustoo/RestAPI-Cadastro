package br.com.api.cadastro.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.api.cadastro.model.Extrato;
import br.com.api.cadastro.model.ExtratoEnviado;
import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.repository.ExtratoRepository;
import br.com.api.cadastro.repository.UsuarioRepository;

@Service
public class ExtratoService {
    
    @Autowired
    private ExtratoRepository extratoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Extrato salvar(ExtratoEnviado extratoEnviado) {
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

        Usuario user = usuarioRepository.findByUsuario(usuario).get();

        Extrato extrato = extratoEnviado.toExtrato();
        extrato.setUser(user);

        System.out.println(extrato);
        return extratoRepository.save(extrato);
    }

    public ResponseEntity<List<Extrato>> buscaExtratoPorUsuario(String usuario) {
        Boolean estaPresente = usuarioRepository.findByUsuario(usuario).isPresent();

        if(!estaPresente) {
            return ResponseEntity.notFound().build();
        }

        List<Extrato> extrato = extratoRepository.findByUserUsuario(usuario);

        return ResponseEntity.ok(extrato);
    }
}
