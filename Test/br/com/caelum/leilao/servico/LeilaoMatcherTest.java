package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import org.junit.Test;

import static br.com.caelum.leilao.servico.LeilaoMatcher.temUmLance;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeilaoMatcherTest {

    @Test
    public void deveReceberUmLance() {
        Leilao leilao = new CriadorDeLeilao().para("Macbook Pro 15").constroi();
        assertEquals(0, leilao.getLances().size());

        Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
        leilao.propoe(lance);

        assertThat(leilao.getLances().size(), equalTo(1));
        assertThat(leilao, temUmLance(lance));
    }

}