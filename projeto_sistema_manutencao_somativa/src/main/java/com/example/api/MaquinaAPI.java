package com.example.api;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.model.Maquina;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MaquinaAPI {
    public static List<Maquina> getMaquinas() {
        String json = ApiConnection.getData("maquinas");
        List<Maquina> maquinas = new ArrayList<>();

        if (json != null) {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String dataAquisicaoStr = jsonObject.getString("dataAquisicao");
                LocalDate dataAquisicao = LocalDate.parse(dataAquisicaoStr, DateTimeFormatter.ISO_DATE);

                Maquina maquina = new Maquina(
                    jsonObject.getString("id"),
                    jsonObject.getString("codigo"),
                    jsonObject.getString("nome"),
                    jsonObject.getString("modelo"),
                    jsonObject.getString("fabricante"),
                    dataAquisicao,
                    jsonObject.getLong("tempoVidaEstimado"),
                    jsonObject.getString("localizacao"),
                    jsonObject.getString("detalhes"),
                    jsonObject.getString("manual")
                );
                maquinas.add(maquina);
            }
        }
        return maquinas;
    }

    public static String postMaquina(Maquina maquina) {
        try {
            String dataAquisicaoStr = maquina.getDataAquisicao().format(DateTimeFormatter.ISO_DATE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", maquina.getId());
            jsonObject.put("codigo", maquina.getCodigo());
            jsonObject.put("nome", maquina.getNome());
            jsonObject.put("modelo", maquina.getModelo());
            jsonObject.put("fabricante", maquina.getFabricante());
            jsonObject.put("dataAquisicao", dataAquisicaoStr);
            jsonObject.put("tempoVidaEstimado", maquina.getTempoVidaEstimado());
            jsonObject.put("localizacao", maquina.getLocalizacao());
            jsonObject.put("detalhes", maquina.getDetalhes());
            jsonObject.put("manual", maquina.getManual());

            String response = ApiConnection.postData("maquinas", jsonObject.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String putMaquina(String id, Maquina maquina) {
        try {
            String dataAquisicaoStr = maquina.getDataAquisicao().format(DateTimeFormatter.ISO_DATE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", maquina.getId());
            jsonObject.put("codigo", maquina.getCodigo());
            jsonObject.put("nome", maquina.getNome());
            jsonObject.put("modelo", maquina.getModelo());
            jsonObject.put("fabricante", maquina.getFabricante());
            jsonObject.put("dataAquisicao", dataAquisicaoStr);
            jsonObject.put("tempoVidaEstimado", maquina.getTempoVidaEstimado());
            jsonObject.put("localizacao", maquina.getLocalizacao());
            jsonObject.put("detalhes", maquina.getDetalhes());
            jsonObject.put("manual", maquina.getManual());

            String response = ApiConnection.putData("maquinas/" + id, jsonObject.toString());
            return response;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
