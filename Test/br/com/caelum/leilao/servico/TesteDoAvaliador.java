package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TesteDoAvaliador {

    @Test
    public void deveEntenderLancesEmOrdemCrescente() {
        // parte 1: cenário
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 250.0));
        leilao.propoe(new Lance(jose, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        // parte 2: ação
        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        // parte 3: validação
        double maiorEsperado = 400;
        double menorEsperado = 250;

        assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
        assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
        System.out.println("Maior: " + maiorEsperado + " " + leiloeiro.getMaiorLance());
        System.out.println("Menor: " + menorEsperado + " " + leiloeiro.getMenorLance());
    }

    @Test
    public void deveCalcularAMedia() {
        // parte1 (cenário 1): 3 lances em ordem crescente
        Usuario joao = new Usuario("João");
        Usuario jose = new Usuario("José");
        Usuario maria = new Usuario("Maria");

        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(jose, 400.0));
        leilao.propoe(new Lance(maria, 500.0));

        // parte 2: ação
        Avaliador leiloeiro = new Avaliador();
        leiloeiro.valorMedioDosLances(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        double lanceEsperado = leilao.getLances().get(1).getValor();

        assertEquals(lanceEsperado, leiloeiro.getMedia(), 0.00001);
        System.out.println("Média: " + lanceEsperado + " " + leiloeiro.getMedia());
    }

    @Test
    public void deveEntenderLeilaoComApenasUmLance() {
        // parte1 (cenário 1): apenas 1 lance
        Usuario joao = new Usuario("Joao");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 1000.0));
        double lanceJoao = leilao.getLances().get(0).getValor();

        // parte 2: ação
        Avaliador leiloeiroAndre = new Avaliador();
        leiloeiroAndre.avalia(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(lanceJoao, leiloeiroAndre.getMaiorLance(), 0.0001);
        assertEquals(lanceJoao, leiloeiroAndre.getMenorLance(), 0.0001);

        System.out.println("Maior: " + lanceJoao + leiloeiroAndre.getMaiorLance());
        System.out.println("Menor: " + lanceJoao + leiloeiroAndre.getMenorLance());
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        // parte1 (cenário 1): vários lances randômicos
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");

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
        Avaliador leiloeiroSimao = new Avaliador();
        leiloeiroSimao.avalia(leilao);

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(licitanteMaria, leiloeiroSimao.getMaiorLance(), 0.0001);
        assertEquals(licitanteJoao, leiloeiroSimao.getMenorLance(), 0.0001);

        System.out.println("Maior: " + licitanteMaria + " " + leiloeiroSimao.getMaiorLance());
        System.out.println("Menor: " + licitanteJoao + " " + leiloeiroSimao.getMenorLance());
    }

    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        // parte1 (cenário 1): lances ordem decrescente
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 400.0));
        leilao.propoe(new Lance(maria, 300.0));
        leilao.propoe(new Lance(joao, 200.0));
        leilao.propoe(new Lance(maria, 100.0));

        // parte 2: ação
        Avaliador leiloeiroRenata = new Avaliador();
        leiloeiroRenata.avalia(leilao);

        double licitanteJoao = leilao.getLances().get(0).getValor();
        double licitanteMaria = leilao.getLances().get(3).getValor();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(licitanteJoao, leiloeiroRenata.getMaiorLance(), 0.0001);
        assertEquals(licitanteMaria, leiloeiroRenata.getMenorLance(), 0.0001);

        System.out.println("Maior: " + licitanteJoao + " " + leiloeiroRenata.getMaiorLance());
        System.out.println("Menor: " + licitanteMaria + " " + leiloeiroRenata.getMenorLance());
    }

    @Test
    public void deveEncontrarOsTresMaioresLances() {
        // parte1 (cenário 1): leilão com 4 lances, deve encontrar os três maiores
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));
        leilao.propoe(new Lance(joao, 300.0));
        leilao.propoe(new Lance(maria, 400.0));

        // parte 2: ação
        Avaliador leiloeiroAmanda = new Avaliador();
        leiloeiroAmanda.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiroAmanda.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(3, lancesMaiores.size());
        assertEquals(400, lancesMaiores.get(0).getValor(), 0.0001);
        assertEquals(300, lancesMaiores.get(1).getValor(), 0.0001);
        assertEquals(200, lancesMaiores.get(2).getValor(), 0.0001);

        System.out.println("Leilões: 3 " +  lancesMaiores.size());
        System.out.println("Maiores: 400.0 " +  lancesMaiores.get(0).getValor());
        System.out.println("Maiores: 300.0 " +  lancesMaiores.get(1).getValor());
        System.out.println("Maiores: 200.0 " +  lancesMaiores.get(2).getValor());
    }

    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo2() {
        // parte1 (cenário 1): leilão com 2 lances, deve devolver apenas os dois lances que encontrou
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

        // parte 2: ação
        Avaliador leiloeiroRicardo = new Avaliador();
        leiloeiroRicardo.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiroRicardo.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(2, lancesMaiores.size());
        assertEquals(200, lancesMaiores.get(0).getValor(), 0.0001);
        assertEquals(100, lancesMaiores.get(1).getValor(), 0.0001);

        System.out.println("Leilões: 2 " +  lancesMaiores.size());
        System.out.println("Maiores: 200.0 " +  lancesMaiores.get(0).getValor());
        System.out.println("Maiores: 100.0 " +  lancesMaiores.get(1).getValor());
    }

    @Test
    public void deveDevolverListaVaziaCasaNaoHajaLances() {
        // parte1 (cenário 1): leilão sem nenhum lance, devolve lista vazia
        Leilao leilao = new Leilao("Playstation 3 Novo");

        // parte 2: ação
        Avaliador leiloeiroMarcelo = new Avaliador();
        leiloeiroMarcelo.avalia(leilao);

        List<Lance> lancesMaiores = leiloeiroMarcelo.getTresMaiores();

        // parte 3 (validação) : comparando a saída com o esperado
        assertEquals(0, lancesMaiores.size());

        System.out.println("Leilões: 0 " +  lancesMaiores.size());
    }
}


