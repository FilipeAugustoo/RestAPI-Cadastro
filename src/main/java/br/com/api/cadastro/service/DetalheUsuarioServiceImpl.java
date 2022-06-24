package br.com.api.cadastro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.api.cadastro.data.DetalheUsuarioData;
import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.repository.UsuarioRepository;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<Usuario> usuario = repository.findByUsuario(username);
         if (usuario.isEmpty()) {
             throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado");
         } 

        return new DetalheUsuarioData(usuario);
    }
    
}
