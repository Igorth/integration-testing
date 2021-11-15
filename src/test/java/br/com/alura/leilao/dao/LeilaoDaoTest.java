package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LeilaoDaoTest {

    private LeilaoDao dao;
    private EntityManager em;

    @BeforeEach
    public void beforeEach() {
        this.em = JPAUtil.getEntityManager();
        this.dao = new LeilaoDao(em);
        em.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        em.getTransaction().rollback();
    }

    @Test
    public void should_register_leilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Bag", new BigDecimal("70"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());

        assertNotNull(salvo);
    }

    @Test
    public void should_update_leilao() {
        Usuario usuario = criarUsuario();
        Leilao leilao = new Leilao("Bag", new BigDecimal("70"), LocalDate.now(), usuario);

        leilao = dao.salvar(leilao);

        leilao.setNome("Notebook");
        leilao.setValorInicial(new BigDecimal("30"));

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());

        assertEquals("Notebook", leilao.getNome());
        assertEquals(new BigDecimal("30"), leilao.getValorInicial());
    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario("fulano", "fulano@gmail.com", "12345678");
        em.persist(usuario);
        return usuario;
    }
}
