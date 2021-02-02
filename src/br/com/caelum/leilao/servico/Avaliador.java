package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Avaliador {

    private double maiorDeTodos = Double.NEGATIVE_INFINITY;
    private double menorDeTodos = Double.POSITIVE_INFINITY;
    private double media;
    private List<Lance> maioresList;

    private void retornaExceptionSeListaVazia(Leilao leilao) {
        if(leilao.getLances().size() == 0)
            throw new RuntimeException("Não é possível avaliar um leilão sem lances");
    }
    
    public void avalia(Leilao leilao) {
        retornaExceptionSeListaVazia(leilao);
        for(Lance lance : leilao.getLances()) {
            if(lance.getValor() > maiorDeTodos) maiorDeTodos = lance.getValor();
            if(lance.getValor() < menorDeTodos) menorDeTodos = lance.getValor();
        }
        pegaOsMaiores(leilao);
    }


    private void pegaOsMaiores(Leilao leilao) {
        retornaExceptionSeListaVazia(leilao);
        maioresList = new ArrayList<>(leilao.getLances());
        Collections.sort(maioresList, new Comparator<Lance>() {
            @Override
            public int compare(Lance o1, Lance o2) {
                if(o1.getValor() < o2.getValor()) return 1;
                if(o1.getValor() > o2.getValor()) return -1;
                return 0; // se 0, retorna 0
            }
        });
        //maioresList = maioresList.subList(0, 3);
        maioresList = maioresList
                .subList(0, maioresList.size() > 3 ? 3 : maioresList.size());
    }

    public void valorMedioDosLances(Leilao leilao) {
        retornaExceptionSeListaVazia(leilao);
        double total = 0;

        for(Lance lance : leilao.getLances()) {
            total+= lance.getValor();
        }

        if(total == 0) {
            media = 0;
            return;
        }

        media = total / leilao.getLances().size();
    }



    public double getMaiorLance() {
        return maiorDeTodos;
    }

    public double getMenorLance() {
        return menorDeTodos;
    }

    public double getMedia() {
        return media;
    }

    public List<Lance> getTresMaiores() {
        return maioresList;
    }
}
