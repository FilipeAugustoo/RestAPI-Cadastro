package br.com.api.cadastro.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtratoEnviado {
    
    @NotBlank
    private String tipo;

    @NotNull
    private Integer valor;

    private LocalDateTime data;

    public Extrato toExtrato() {

        LocalDateTime data = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "dd/MM/yyyy HH:mm");

        String dataString = data.format(formatter);

        LocalDateTime dataFormatada = LocalDateTime.parse(dataString, formatter);

        Extrato extrato = new Extrato();
        extrato.setTipo(tipo);
        extrato.setValor(valor);
        extrato.setData(dataFormatada);

        return extrato;
    }
}
