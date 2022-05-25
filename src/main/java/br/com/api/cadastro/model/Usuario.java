package br.com.api.cadastro.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

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
    @Size(max = 25)
    private String usuario;
    
    @NotBlank
    @Size(max = 25)
    private String senha;

    @NotBlank
    private Integer saldo;

    
}
