package br.com.alura.leilao.dao;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;
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
    private Usuario usuario;

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
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Desk")
                .comValorInicial("500")
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());

        assertNotNull(salvo);
    }

    @Test
    public void should_update_leilao() {
        Usuario usuario = new UsuarioBuilder()
                .comNome("Fulano")
                .comEmail("fulano@gmail.com")
                .comSenha("123456")
                .criar();
        em.persist(usuario);

        Leilao leilao = new LeilaoBuilder()
                .comNome("Desk")
                .comValorInicial("500")
                .comData(LocalDate.now())
                .comUsuario(usuario)
                .criar();

        leilao = dao.salvar(leilao);

        leilao.setNome("Notebook");
        leilao.setValorInicial(new BigDecimal("30"));

        leilao = dao.salvar(leilao);

        Leilao salvo = dao.buscarPorId(leilao.getId());

        assertEquals("Notebook", leilao.getNome());
        assertEquals(new BigDecimal("30"), leilao.getValorInicial());
    }
}
