package com.example.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.model.Tecnicos;
import java.util.ArrayList;
import java.util.List;

public class TecnicosAPI {

    public static List<Tecnicos> getTecnicos() {
        String json = ApiConnection.getData("tecnicos");
        List<Tecnicos> tecnicos = new ArrayList<>();

        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Tecnicos tecnico = new Tecnicos(
                    jsonObject.getString("id"),
                    jsonObject.getString("nome"),
                    jsonObject.getString("especialidade"),
                    jsonObject.getString("disponibilidade")
                );
                tecnicos.add(tecnico);
            }
        }
        return tecnicos;
    }

    // Método POST para adicionar um novo técnico
    public static String postTecnico(Tecnicos tecnico) {
        try {
            // Criar um JSONObject para o técnico
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tecnico.getId());
            jsonObject.put("nome", tecnico.getNome());
            jsonObject.put("especialidade", tecnico.getEspecialidade());
            jsonObject.put("disponibilidade", tecnico.getDisponibilidade());

            // Enviar dados para a API
            String response = ApiConnection.postData("tecnicos", jsonObject.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método PUT para atualizar um técnico existente
    public static String putTecnico(String id, Tecnicos tecnico) {
        try {
            // Criar um JSONObject para o técnico
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tecnico.getId());
            jsonObject.put("nome", tecnico.getNome());
            jsonObject.put("especialidade", tecnico.getEspecialidade());
            jsonObject.put("disponibilidade", tecnico.getDisponibilidade());

            // Enviar os dados atualizados para a API usando o método PUT
            String response = ApiConnection.putData("tecnicos/" + id, jsonObject.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método DELETE para remover um técnico existente
    public static String deleteTecnico(String id) {
        try {
            // Enviar a requisição DELETE para a API
            String response = ApiConnection.deleteData("tecnicos/" + id);
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
