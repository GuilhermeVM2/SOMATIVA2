package com.example.api;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.model.HistoricoManutencao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoricoManutencaoAPI {

    public static List<HistoricoManutencao> getHistoricos() {
        String json = ApiConnection.getData("historicoManutencao");
        List<HistoricoManutencao> historicos = new ArrayList<>();

        if (json != null && !json.isEmpty()) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                try {
                    // Verifica se o campo "data" existe e é válido
                    String dataStr = jsonObject.optString("data", null);
                    LocalDate data = (dataStr != null) ? LocalDate.parse(dataStr, DateTimeFormatter.ISO_DATE) : null;

                    // Verifica se "maquinaId" existe
                    Long maquinaId = jsonObject.has("maquinaId") ? jsonObject.getLong("maquinaId") : 0L;

                    // Cria um novo objeto HistoricoManutencao
                    HistoricoManutencao historico = new HistoricoManutencao(
                        jsonObject.optString("id", ""),
                        maquinaId,
                        data,
                        jsonObject.optString("tipo", ""),
                        jsonObject.optString("pecasTrocadas", ""),
                        jsonObject.optLong("tempoDeParada", 0L),
                        jsonObject.optString("tecnicoId", ""),
                        jsonObject.optString("observacoes", "")
                    );
                    historicos.add(historico);

                } catch (Exception e) {
                    // Loga ou trata a exceção para que um erro específico no JSON não quebre o loop inteiro
                    e.printStackTrace();
                }
            }
        }
        return historicos;
    }

    public static String postManutencao(HistoricoManutencao manutencao) {
        try {
            String dataStr = manutencao.getData().format(DateTimeFormatter.ISO_DATE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", manutencao.getId());
            jsonObject.put("maquinaId", manutencao.getMaquinaId());
            jsonObject.put("data", dataStr);
            jsonObject.put("tipo", manutencao.getTipo());
            jsonObject.put("pecasTrocadas", manutencao.getPecasTrocadas());
            jsonObject.put("tempoDeParada", manutencao.getTempoDeParada());
            jsonObject.put("tecnicoId", manutencao.getTecnicoID());
            jsonObject.put("observacoes", manutencao.getObservacoes());

            return ApiConnection.postData("historicoManutencao", jsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String putManutencao(String id, HistoricoManutencao manutencao) {
        try {
            String dataStr = manutencao.getData().format(DateTimeFormatter.ISO_DATE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", manutencao.getId());
            jsonObject.put("maquinaId", manutencao.getMaquinaId());
            jsonObject.put("data", dataStr);
            jsonObject.put("tipo", manutencao.getTipo());
            jsonObject.put("pecasTrocadas", manutencao.getPecasTrocadas());
            jsonObject.put("tempoDeParada", manutencao.getTempoDeParada());
            jsonObject.put("tecnicoId", manutencao.getTecnicoID());
            jsonObject.put("observacoes", manutencao.getObservacoes());

            return ApiConnection.putData("historicoManutencao/" + id, jsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String deleteManutencao(String id) {
        try {
            return ApiConnection.deleteData("manutencoes/" + id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
