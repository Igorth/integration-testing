package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    private UsuarioDao dao;

    @Test
    public void test_find_user_by_username() {
        this.dao = new UsuarioDao();
        Usuario usuario = dao.buscarPorUsername("fulano");
        assertNotNull(usuario);
    }
}
