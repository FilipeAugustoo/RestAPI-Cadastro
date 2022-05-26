package br.com.api.cadastro.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.repository.UsuarioRepository;
import br.com.api.cadastro.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;
    
    @GetMapping
    public List<Usuario> teste() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/existe/{usuario}")
    public Boolean buscarPorId(@PathVariable String usuario) {
        return cadastroUsuarioService.verificaSeExisteUsuario(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@Valid @RequestBody Usuario usuario) {
        return cadastroUsuarioService.salvar(usuario);
    }

    @PatchMapping("{usuarioId}/{senha}")
    public ResponseEntity<Usuario> atualizarSenha(@Valid @PathVariable Integer usuarioId, @PathVariable String senha) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).get();
            usuario.setSenha(senha);
            return new ResponseEntity<Usuario>(usuarioRepository.save(usuario), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
    }

    @DeleteMapping("{usuarioId}")
    public ResponseEntity<Void> remover(@PathVariable Integer usuarioId) {
        return cadastroUsuarioService.excluir(usuarioId);
    }
}
