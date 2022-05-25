package br.com.api.cadastro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.cadastro.exception.UsuarioIgualException;
import br.com.api.cadastro.model.Usuario;
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

    public ResponseEntity<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

}
