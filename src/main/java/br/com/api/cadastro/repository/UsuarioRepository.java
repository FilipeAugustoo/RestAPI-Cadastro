package br.com.api.cadastro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.cadastro.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>  {
    Optional<Usuario> findByUsuario(String usuario);
    
}
