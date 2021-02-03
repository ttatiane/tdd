package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class TesteDoAvaliador {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }

    @Test(expected=RuntimeException.class)
    public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .constroi();

        leiloeiro.avalia(leilao);
    }

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        // parte 1: cenário
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 250.0)
                .lance(jose, 300.0)
                .lance(maria, 400.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3: validação
        assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
    }

    @Test
    public void deveCalcularAMedia() {
        // parte1 (cenário 1): 3 lances em ordem crescente
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 300.0)
                .lance(jose, 400.0)
                .lance(maria, 500.0)
                .constroi();

        // parte 2: ação
        leiloeiro.valorMedioDosLances(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertThat(leiloeiro.getMedia(), equalTo(400.0));
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        // parte1 (cenário 1): apenas 1 lance
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 1000)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertThat(leiloeiro.getMenorLance(), equalTo(leiloeiro.getMaiorLance()));
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        // parte1 (cenário 1): vários lances randômicos
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 200.0)
                .lance(maria, 450.0)
                .lance(joao, 120.0)
                .lance(maria, 700.0)
                .lance(joao, 630.0)
                .lance(maria, 230.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertThat(leiloeiro.getMaiorLance(), equalTo(700.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(120.0));
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        // parte1 (cenário 1): lances ordem decrescente
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 400.0)
                .lance(maria, 300.0)
                .lance(joao, 200.0)
                .lance(maria, 100.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        double licitanteJoao = leilao.getLances().get(0).getValor();
        double licitanteMaria = leilao.getLances().get(3).getValor();

        // parte 3 (validação) : comparando a saída com o esperado
        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        // parte1 (cenário 1): leilão com 4 lances, deve encontrar os três maiores
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .lance(joao, 300.0)
                .lance(maria, 400.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiro.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(3, lancesMaiores.size());
//        assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
//        assertThat(leiloeiro.getMenorLance(), equalTo(100.0));

        assertThat(lancesMaiores, hasItems(
                new Lance(maria, 400),
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo2() {
        // parte1 (cenário 1): leilão com 2 lances, deve devolver apenas os dois lances que encontrou
        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation 3 Novo")
                .lance(joao, 100.0)
                .lance(maria, 200.0)
                .constroi();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiro.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(2, lancesMaiores.size());
        assertEquals(200, lancesMaiores.get(0).getValor(), 0.0001);
        assertEquals(100, lancesMaiores.get(1).getValor(), 0.0001);
    }

    @Test
    public void deveDevolverListaVaziaCasaNaoHajaLances() {
        // parte1 (cenário 1): leilão sem nenhum lance, devolve lista vazia
        Leilao leilao = new Leilao("Playstation 3 Novo");

        // parte 2: ação
        leiloeiro.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiro.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(0, lancesMaiores.size());
    }
}


