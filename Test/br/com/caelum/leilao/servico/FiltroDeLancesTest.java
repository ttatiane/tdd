package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FiltroDeLancesTest {

    @Test
    public void deveSelecionarLancesEntre1000E3000() {
        // cenário
        Usuario joao = new Usuario("João");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao, 2000),
                new Lance(joao, 1000),
                new Lance(joao, 3000),
                new Lance(joao, 800)));

        //ação e validação
        assertEquals(1, resultado.size());
        assertEquals(2000, resultado.get(0).getValor(), 0.00001);
    }

    @Test
    public void deveSelecionarLancesEntre500E700() {
        // cenário
        Usuario joao = new Usuario("João");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao, 600),
                new Lance(joao, 500),
                new Lance(joao, 700),
                new Lance(joao, 800)));

        //ação e validação
        assertEquals(1, resultado.size());
        assertEquals(600, resultado.get(0).getValor(), 0.00001);
    }

    @Test
    public void deveSelecionarLancesMaioresQue5000() {
        // cenário: Lances maiores que 5000 devem ser selecionados
        Usuario joao = new Usuario("João");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao, 3750),
                new Lance(joao, 4500),
                new Lance(joao, 15000),
                new Lance(joao, 5000)));

        //ação e validação
        assertEquals(1, resultado.size());
        assertEquals(15000, resultado.get(0).getValor(), 0.00001);
    }

    @Test
    public void deveEliminarLancesMenoresQue500() {
        // cenário: Lances menores que 500 devem ser eliminados
        Usuario joao = new Usuario("João");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao, 400),
                new Lance(joao, 300)));

        //ação // validação
        assertEquals(0, resultado.size());
    }

    @Test
    public void deveEliminarLancesEntre700E1000() {
        //cenário: lances entre 700 e 1000 devem ser eliminados
        Usuario joao = new Usuario("João");

        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao, 700),
                new Lance(joao, 1000),
                new Lance(joao, 800),
                new Lance(joao, 900)));

        //ação // validação
        assertEquals(0, resultado.size());
    }

    @Test
    public void deveEliminarLancesEntre3000E5000() {
        //cenário: lances entre 3000 e 5000 devem ser eliminados
        Usuario joao = new Usuario("João");
        FiltroDeLances filtro = new FiltroDeLances();
        List<Lance> resultado = filtro.filtra(Arrays.asList(
                new Lance(joao, 5000),
                new Lance(joao, 3001)));
        assertEquals(0, resultado.size());
    }
}