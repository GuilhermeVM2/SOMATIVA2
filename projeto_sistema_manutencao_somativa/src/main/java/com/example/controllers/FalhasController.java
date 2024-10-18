package com.example.controllers;

import java.util.ArrayList;
import java.util.List;

import com.example.api.FalhasAPI;
import com.example.model.Falhas;

public class FalhasController {
    private List<Falhas> falhas;

    public FalhasController() {
        falhas = new ArrayList<>();
    }

    // Métodos - CRUD
    public String createFalha(Falhas falha) {
        String response = FalhasAPI.postFalha(falha);
        if (response != null) {
            falhas.add(falha);
        }
        return response;
    }

    public List<Falhas> readFalhas() {
        falhas = FalhasAPI.getFalhas();
        return falhas;
    }

    public boolean updateFalha(Falhas falha) {
        // Chama a API para atualizar a falha no banco de dados
        String response = FalhasAPI.putFalha(falha.getId(), falha);
        if (response != null) {
            // Atualiza a lista local apenas se a API retornar sucesso
            for (int i = 0; i < falhas.size(); i++) {
                if (falhas.get(i).getId().equals(falha.getId())) {
                    falhas.set(i, falha);
                    return true;
                }
            }
        }
        return false;
    }

    public void deleteFalha(int posicao) {
        falhas.remove(posicao);
    }
}
