package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.api.TecnicosAPI;
import com.example.model.Tecnicos;

public class TecnicosController {
    private List<Tecnicos> tecnicos;

    public TecnicosController() {
        tecnicos = new ArrayList<>();
    }

    // Métodos - CRUD
    public void createTecnico(Tecnicos tecnico) {
        String response = TecnicosAPI.postTecnico(tecnico);
        if (response != null) {
            // Atualiza a lista local se a API retornar sucesso
            tecnicos.add(tecnico);
        }
    }

    public List<Tecnicos> readTecnicos() {
        tecnicos = TecnicosAPI.getTecnicos();
        return tecnicos;
    }

    public void updateTecnico(int posicao, Tecnicos tecnico) {
        String id = tecnico.getId();
        String response = TecnicosAPI.putTecnico(id, tecnico);
        if (response != null) {
            tecnicos.set(posicao, tecnico);
        }
    }

    public void deleteTecnico(String id) {
        Optional<Tecnicos> tecnicoParaRemover = tecnicos.stream()
            .filter(tecnico -> tecnico.getId().equals(id))
            .findFirst();

        if (tecnicoParaRemover.isPresent()) {
            TecnicosAPI.deleteTecnico(id);
            tecnicos.remove(tecnicoParaRemover.get());
        } else {
            System.out.println("Técnico não encontrado com ID: " + id);
        }
    }
}
