package br.com.api.cadastro.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.model.UsuarioSaldo;
import br.com.api.cadastro.repository.UsuarioRepository;
import br.com.api.cadastro.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public List<Usuario> teste() {
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

    @GetMapping("/validaSenha")
    public ResponseEntity<Boolean> validaSenha(@RequestParam String usuario,
            @RequestParam String senha) {

        Optional<Usuario> optUsuario = usuarioRepository.findByUsuario(usuario);
        
        if(optUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Usuario user = optUsuario.get();
        Boolean valid = encoder.matches(senha, user.getSenha());
        
        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);
    }

    @PostMapping("/cadastrar")
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = "listaDeTopicos", allEntries = true)
    public Usuario salvar(@Valid @RequestBody Usuario usuario) {
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return cadastroUsuarioService.salvar(usuario);
    }

    @PatchMapping("{usuarioId}/{senha}")
    public ResponseEntity<Usuario> atualizarSenha(@Valid @PathVariable Integer usuarioId, @PathVariable String senha) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).get();
            usuario.setSenha(senha);
            return new ResponseEntity<Usuario>(usuarioRepository.save(usuario), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PatchMapping("{usuario}/saldo")
    public ResponseEntity<Usuario> adicionaSaldo(@Valid @PathVariable String usuario,
            @RequestBody UsuarioSaldo usuarioSaldo, @RequestParam String tipo) {
        return cadastroUsuarioService.atualizaSaldo(usuario, usuarioSaldo, tipo);
    }

    @DeleteMapping("{usuarioId}")
    public ResponseEntity<Void> remover(@PathVariable Integer usuarioId) {
        return cadastroUsuarioService.excluir(usuarioId);
    }
}
