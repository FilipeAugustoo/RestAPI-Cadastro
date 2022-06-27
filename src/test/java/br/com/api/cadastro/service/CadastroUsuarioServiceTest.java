package br.com.api.cadastro.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.api.cadastro.model.Usuario;
import br.com.api.cadastro.model.UsuarioSaldo;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CadastroUsuarioServiceTest {

    @Autowired
    private CadastroUsuarioService service;
    
    @Test
    public void deveriaDevolver400CasoUsuarioJaExista() {
        Usuario user = new Usuario();
        user.setUsuario("joao");
        user.setSenha("123456");

        ResponseEntity<Usuario> status = service.salvar(user);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, status.getStatusCode());
    }

    @Test
    public void deveriaDevolver201AoCriarUsuario() {
        Usuario user = new Usuario();
        user.setUsuario("filipe");
        user.setSenha("123456");

        ResponseEntity<Usuario> status = service.salvar(user);

        Assert.assertEquals(HttpStatus.CREATED, status.getStatusCode());
    }

    @Test
    public void deveriaDevolverTrueCasoExistaUsuario() {
        Boolean estado = service.verificaSeExisteUsuario("joao");
        Assert.assertTrue(estado);
    }

    @Test
    public void deveriaDevolverFalseCasoUsuarioNaoExista() {
        Boolean estado = service.verificaSeExisteUsuario("carlos");
        Assert.assertFalse(estado);
    }

    @Test
    public void deveriaDevolver200CasoUsuarioAdicionaOuRemoveSaldo() {
        UsuarioSaldo saldo = new UsuarioSaldo();
        saldo.setSaldo(200);

        ResponseEntity<Usuario> user = service.atualizaSaldo("joao", saldo, "add");

        Assert.assertEquals(HttpStatus.OK, user.getStatusCode());
    }

    @Test
    public void deveriaDevolver404CasoUsuarioNaoExista() {
        UsuarioSaldo saldo = new UsuarioSaldo();
        saldo.setSaldo(200);

        ResponseEntity<Usuario> user = service.atualizaSaldo("carlos", saldo, "add");

        Assert.assertEquals(HttpStatus.NOT_FOUND, user.getStatusCode());
    }

    @Test
    public void deveriaDevolver400CasoUsuarioErraOTipo() {
        UsuarioSaldo saldo = new UsuarioSaldo();
        saldo.setSaldo(200);

        ResponseEntity<Usuario> user = service.atualizaSaldo("joao", saldo, "adds");

        Assert.assertEquals(HttpStatus.BAD_REQUEST, user.getStatusCode());
    }
    
}
