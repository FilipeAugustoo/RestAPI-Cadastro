package br.com.api.cadastro.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.http.ResponseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Usuario {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @NotBlank
    @Pattern(regexp = "^[a-z0-9_]+$")
    private String usuario;
    
    @NotBlank
    private String senha;

    @NotNull
    private Integer saldo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Extrato> extrato;

    
    public void adicionaSaldo(Integer valor) {
        this.saldo += valor;
    }


    public ResponseEntity<?> removeSaldo(Integer valor) {
        if(this.saldo > valor) {
            this.saldo -= valor;
        }

        return ResponseEntity.badRequest().build();
    }
}
