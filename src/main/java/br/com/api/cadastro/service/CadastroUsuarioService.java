package br.com.api.cadastro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.model.UsuarioSaldo;
import br.com.api.cadastro.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public ResponseEntity<Usuario> salvar(Usuario usuario) {

        boolean usuarioEhIgual = usuarioRepository
        .findByUsuario(usuario.getUsuario())
            .stream()
            .allMatch(user -> user.equals(usuario));

        if (!usuarioEhIgual) {
			return ResponseEntity.badRequest().build();
		}

        return new ResponseEntity<Usuario>(usuario, HttpStatus.CREATED);
    }

    public Boolean verificaSeExisteUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario).isPresent();
    }

    public ResponseEntity<Usuario> atualizaSaldo(String usuario, UsuarioSaldo usuarioSaldo, String tipo) {
        Boolean estaPresente = usuarioRepository.findByUsuario(usuario).isPresent();
        if(!estaPresente) {
            return ResponseEntity.notFound().build();
        }
        
        if(tipo.equals("add")) {
                Usuario user = usuarioRepository.findByUsuario(usuario).get();
                user.adicionaSaldo(usuarioSaldo.getSaldo());
                return new ResponseEntity<Usuario>(usuarioRepository.save(user), HttpStatus.OK);
             
        } else if(tipo.equals("rem")) {
                Usuario user = usuarioRepository.findByUsuario(usuario).get();
                user.removeSaldo(usuarioSaldo.getSaldo());
                return new ResponseEntity<>(usuarioRepository.save(user), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           
    }

}
