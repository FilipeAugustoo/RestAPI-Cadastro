package br.com.api.cadastro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.cadastro.exception.UsuarioIgualException;
import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.model.UsuarioLogin;
import br.com.api.cadastro.model.UsuarioSaldo;
import br.com.api.cadastro.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) {

        boolean usuarioEhIgual = usuarioRepository
        .findByUsuario(usuario.getUsuario())
            .stream()
            .allMatch(user -> user.equals(usuario));

        if (!usuarioEhIgual) {
			throw new UsuarioIgualException("Já existe um usuário cadastrado com esse nome");
		}

        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<Void> excluir(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        usuarioRepository.deleteById(id);


        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Usuario> atualizar(Integer id, String senha) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        Usuario usuario = new Usuario();
        
        usuario.setId(id);
        usuario.setSenha(senha);
        usuario = salvar(usuario);

        return ResponseEntity.ok(usuario);
    }

    public Boolean verificaSeExisteUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario).isPresent();
    }

    public ResponseEntity<Usuario> logar(UsuarioLogin usuarioLogin) {
        Usuario user = usuarioRepository.findByUsuarioAndSenha(usuarioLogin.getUsuario(), usuarioLogin.getSenha());

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<Usuario> atualizaSaldo(String usuario, UsuarioSaldo usuarioSaldo, String tipo) {
        if(tipo.equals("add")) {
            try {
                Usuario user = usuarioRepository.findByUsuario(usuario).get();
                user.adicionaSaldo(usuarioSaldo.getSaldo());
                return new ResponseEntity<Usuario>(usuarioRepository.save(user),
                        HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else if(tipo.equals("rem")) {
            try {
                Usuario user = usuarioRepository.findByUsuario(usuario).get();
                user.removeSaldo(usuarioSaldo.getSaldo());
                return new ResponseEntity<>(usuarioRepository.save(user), HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
           
    }

}
