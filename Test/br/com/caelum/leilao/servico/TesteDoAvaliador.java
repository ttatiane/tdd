package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
        Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
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

        Assert.assertEquals(lanceEsperado, leiloeiro.getMedia(), 0.00001);
        System.out.println("Média: " + lanceEsperado + " " + leiloeiro.getMedia());
    }
}
