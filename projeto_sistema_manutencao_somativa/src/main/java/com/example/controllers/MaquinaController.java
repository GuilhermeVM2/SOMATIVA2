package com.example.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID; // Importando UUID para gerar IDs únicos
import com.example.api.MaquinaAPI;
import com.example.model.Maquina;

public class MaquinaController {
    private List<Maquina> maquinas;

    public MaquinaController() {
        maquinas = new ArrayList<>();
    }

    // Métodos - CRUD
    public void createMaquina(Maquina maquina) {
        // Gerar um ID único usando UUID
        String novoId = UUID.randomUUID().toString(); // Gera um ID único
        maquina.setId(novoId); // Define o ID da nova máquina
        this.maquinas.add(maquina);
        saveMaquina(maquina); // Salvar no banco de dados ao criar
    }

    public List<Maquina> readMaquinas() {
        try {
            maquinas = MaquinaAPI.getMaquinas();
        } catch (Exception e) {
            System.err.println("Erro ao ler as máquinas: " + e.getMessage());
            return new ArrayList<>();
        }
        return maquinas;
    }

    public void updateMaquina(int posicao, Maquina maquina) {
        if (posicao >= 0 && posicao < maquinas.size()) {
            maquinas.set(posicao, maquina);
            saveMaquina(maquina); // Salvar ao atualizar
        } else {
            System.err.println("Erro: Índice de atualização inválido.");
        }
    }

    public void deleteMaquina(int posicao) {
        if (posicao >= 0 && posicao < maquinas.size()) {
            maquinas.remove(posicao);
        } else {
            System.err.println("Erro: Índice de exclusão inválido.");
        }
    }

    public void saveMaquina(Maquina maquina) {
        String response = MaquinaAPI.postMaquina(maquina); // Enviar a máquina para a API
        if (response != null) {
            System.out.println("Máquina salva com sucesso: " + response);
        } else {
            System.err.println("Falha ao salvar a máquina.");
        }
    }
}
