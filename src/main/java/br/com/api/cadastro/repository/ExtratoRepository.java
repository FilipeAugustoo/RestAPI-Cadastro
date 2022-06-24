package br.com.api.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.cadastro.model.Extrato;

public interface ExtratoRepository extends JpaRepository<Extrato, Integer>  {
    
    List<Extrato> findByUserUsuario(String usuario);
}
