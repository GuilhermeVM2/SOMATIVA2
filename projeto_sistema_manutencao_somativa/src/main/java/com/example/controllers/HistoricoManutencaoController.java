package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import com.example.api.HistoricoManutencaoAPI;
import com.example.model.HistoricoManutencao;

public class HistoricoManutencaoController {
    private List<HistoricoManutencao> historicos;

    public HistoricoManutencaoController() {
        historicos = new ArrayList<>();
    }

    public void createHistorico(HistoricoManutencao historico) {
        HistoricoManutencaoAPI.postManutencao(historico); // Salva automaticamente no banco de dados
        this.historicos.add(historico);
    }

    public List<HistoricoManutencao> readHistoricos() {
        historicos = HistoricoManutencaoAPI.getHistoricos();
        return historicos;
    }

    public void updateHistorico(int posicao, HistoricoManutencao historico) {
        historicos.set(posicao, historico);
    }

    public void deleteHistorico(int posicao) {
        historicos.remove(posicao);
    }
}
