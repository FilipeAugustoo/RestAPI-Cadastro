package br.com.api.cadastro.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.model.UsuarioSaldo;
import br.com.api.cadastro.repository.UsuarioRepository;
import br.com.api.cadastro.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "https://filipeaugustoo.github.io", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/existe/{usuario}")
    public Boolean verificaSeExixste(@PathVariable String usuario) {
        return cadastroUsuarioService.verificaSeExisteUsuario(usuario);
    }

    @GetMapping("{usuario}")
    @Cacheable(value = "listaDeUsuarios")
    public ResponseEntity<Usuario> buscaPorUsuario(@PathVariable String usuario) {
        return usuarioRepository.findByUsuario(usuario).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar")
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public ResponseEntity<Usuario> salvar(@Valid @RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return cadastroUsuarioService.salvar(usuario);
    }

    @PatchMapping("{usuario}/saldo")
    public ResponseEntity<Usuario> adicionaSaldo(@Valid @PathVariable String usuario,
            @RequestBody UsuarioSaldo usuarioSaldo, @RequestParam String tipo) {
        return cadastroUsuarioService.atualizaSaldo(usuario, usuarioSaldo, tipo);
    }
}
