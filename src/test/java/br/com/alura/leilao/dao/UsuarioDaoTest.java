package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioDaoTest {

    private UsuarioDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new UsuarioDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    public void should_find_registered_user() {
        Usuario usuario = criarUsuario();
        Usuario encontrado = dao.buscarPorUsername(usuario.getNome());
        assertNotNull(encontrado);
    }

    @Test
    public void should_not_find_user_registered() {
        criarUsuario();
        assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
