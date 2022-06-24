package br.com.api.cadastro.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Extrato {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @NotBlank
    private String tipo;

    @NotNull
    private Integer valor;
    
    private LocalDateTime data;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario user;


}
