package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    private UsuarioDao dao;

    @Test
    public void test_find_user_by_username() {
        EntityManager em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        Usuario usuario = dao.buscarPorUsername("fulano");
        assertNotNull(usuario);
    }
}
