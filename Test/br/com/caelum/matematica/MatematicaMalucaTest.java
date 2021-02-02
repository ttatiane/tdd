package br.com.caelum.matematica;

import org.junit.Test;

import static org.junit.Assert.*;

public class MatematicaMalucaTest {

    @Test
    public void deveMultiplicarNumerosMaioresQue30() {
        //cenário: deve retornar valor maior que 30 * 4
        int numero = 31;
        //ação
        MatematicaMaluca matematicaMaluca = new MatematicaMaluca();
        //validação
        assertEquals(30*4, matematicaMaluca.contaMaluca(numero));
        System.out.println("Maior: 30x4 == " + matematicaMaluca.contaMaluca(numero));
    }

    @Test
    public void deveMultiplicarNumerosMaioresQue10EMenoresQue30() {
        MatematicaMaluca matematica = new MatematicaMaluca();
        assertEquals(20*3, matematica.contaMaluca(20));
    }

    @Test
    public void deveMultiplicarNumerosMenoresQue10() {
        MatematicaMaluca matematica = new MatematicaMaluca();
        assertEquals(5*2, matematica.contaMaluca(5));
    }

}