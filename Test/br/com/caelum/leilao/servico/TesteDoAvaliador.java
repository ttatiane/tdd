package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TesteDoAvaliador {

    private Avaliador leiloeiro;
    private Usuario joao;
    private Usuario jose;
    private Usuario maria;

    @Before
    public void setUp() {
        this.leiloeiro = new Avaliador();
        System.out.println("cria avaliador");
        this.joao = new Usuario("João");
        this.jose = new Usuario("José");
        this.maria = new Usuario("Maria");
    }

    @After
    public void finaliza() {
        System.out.println("fim");
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
        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
    }

    @Test
    public void deveCalcularAMedia() {
        // parte1 (cenário 1): 3 lances em ordem crescente
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(jose, 400.0));
        leilao.propoe(new Lance(maria, 500.0));

        // parte 2: ação
        leiloeiro.valorMedioDosLances(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        double lanceEsperado = leilao.getLances().get(1).getValor();

        assertEquals(lanceEsperado, leiloeiro.getMedia(), 0.00001);
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        // parte1 (cenário 1): apenas 1 lance
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 1000.0));
        double lanceJoao = leilao.getLances().get(0).getValor();

        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(lanceJoao, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(lanceJoao, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        // parte1 (cenário 1): vários lances randômicos
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 450.0));
        leilao.propoe(new Lance(joao, 120.0));
        leilao.propoe(new Lance(maria, 700.0));
        leilao.propoe(new Lance(joao, 630.0));
        leilao.propoe(new Lance(maria, 230.0));

        Double licitanteJoao = leilao.getLances().get(2).getValor();
        Double licitanteMaria = leilao.getLances().get(3).getValor();


        // parte 2: ação
        leiloeiro.avalia(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(licitanteMaria, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(licitanteJoao, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        // parte1 (cenário 1): lances ordem decrescente
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 400.0));
        leilao.propoe(new Lance(maria, 300.0));
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 100.0));

        // parte 2: ação
        leiloeiro.avalia(leilao);

        double licitanteJoao = leilao.getLances().get(0).getValor();
        double licitanteMaria = leilao.getLances().get(3).getValor();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(licitanteJoao, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(licitanteMaria, leiloeiro.getMenorLance(), 0.0001);
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        // parte1 (cenário 1): leilão com 4 lances, deve encontrar os três maiores
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        // parte 2: ação
        leiloeiro.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiro.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(3, lancesMaiores.size());
        assertEquals(400, lancesMaiores.get(0).getValor(), 0.0001);
        assertEquals(300, lancesMaiores.get(1).getValor(), 0.0001);
        assertEquals(200, lancesMaiores.get(2).getValor(), 0.0001);
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo2() {
        // parte1 (cenário 1): leilão com 2 lances, deve devolver apenas os dois lances que encontrou
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

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


