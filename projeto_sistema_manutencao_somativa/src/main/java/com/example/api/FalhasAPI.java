package com.example.api;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.model.Falhas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FalhasAPI {

    public static List<Falhas> getFalhas() {
        String json = ApiConnection.getData("falhas");
        List<Falhas> falhas = new ArrayList<>();

        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                
                // Converter data para LocalDate
                String dataStr = jsonObject.getString("data");
                LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ISO_DATE);

                Falhas falha = new Falhas(
                    jsonObject.getString("id"),
                    jsonObject.getLong("maquinaId"),
                    data,
                    jsonObject.getString("problema"),
                    jsonObject.getString("prioridade"),
                    jsonObject.getString("operador")
                );
                falhas.add(falha);
            }
        }
        return falhas;
    }

    // Método POST para adicionar uma nova falha
    public static String postFalha(Falhas falha) {
        try {
            // Converter LocalDate para String
            String dataStr = falha.getData().format(DateTimeFormatter.ISO_DATE);

            // Criar um JSONObject para a falha
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", falha.getId());
            jsonObject.put("maquinaId", falha.getMaquinaId());
            jsonObject.put("data", dataStr);
            jsonObject.put("problema", falha.getProblema());
            jsonObject.put("prioridade", falha.getPrioridade());
            jsonObject.put("operador", falha.getOperador());

            // Enviar dados para a API
            String response = ApiConnection.postData("falhas", jsonObject.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Método PUT para atualizar uma falha existente
    public static String putFalha(String id, Falhas falha) {
        try {
            // Converter LocalDate para String
            String dataStr = falha.getData().format(DateTimeFormatter.ISO_DATE);

            // Criar um JSONObject para a falha
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", falha.getId());
            jsonObject.put("maquinaId", falha.getMaquinaId());
            jsonObject.put("data", dataStr);
            jsonObject.put("problema", falha.getProblema());
            jsonObject.put("prioridade", falha.getPrioridade());
            jsonObject.put("operador", falha.getOperador());

            // Enviar os dados atualizados para a API usando o método PUT
            String response = ApiConnection.putData("falhas/" + id, jsonObject.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}