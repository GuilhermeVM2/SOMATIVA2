package com.example;

import javax.swing.SwingUtilities;

import com.example.view.SistemaManutencaoGUI;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SistemaManutencaoGUI().setVisible(true); // Alterado para true para tornar a GUI visível
            }
        });
    }
}
