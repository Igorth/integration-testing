package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

import org.junit.jupiter.api.AfterEach;
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
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);

        Usuario encontrado = dao.buscarPorUsername(usuario.getNome());
        assertNotNull(encontrado);
    }

    @Test
    public void should_not_find_user_registered() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);

        assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername("beltrano"));
    }

    @Test
    public void should_remover_user() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);

        dao.deletar(usuario);

        assertThrows(NoResultException.class, () -> this.dao.buscarPorUsername(usuario.getNome()));
    }
}
