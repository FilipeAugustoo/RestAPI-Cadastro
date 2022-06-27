package br.com.api.cadastro.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.api.cadastro.model.Extrato;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ExtratoServiceTest {
    
    @Autowired
    private ExtratoService service;

    @Test
    public void deveriaDevolver404CasoUsuarioNaoExista() {
        ResponseEntity<List<Extrato>> status = service.buscaExtratoPorUsuario("carlos");

        Assert.assertEquals(HttpStatus.NOT_FOUND, status.getStatusCode());
    }

    @Test
    public void deveriaDevolver200CasoBusqueExtrato() {
        ResponseEntity<List<Extrato>> status = service.buscaExtratoPorUsuario("joao");

        Assert.assertEquals(HttpStatus.OK, status.getStatusCode());
    }
}
