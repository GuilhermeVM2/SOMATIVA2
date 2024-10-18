package com.example.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Falhas {
        private String id;
    private long maquinaId;
    private LocalDate data;
    private String problema;
    private String prioridade;
    private String operador;
}