package com.example.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.example.api.HistoricoManutencaoAPI;
import com.example.controllers.HistoricoManutencaoController;
import com.example.model.HistoricoManutencao;

public class ManutencaoPanel extends JPanel {
    private HistoricoManutencaoController historicoController;
    private JTable historicosTable;
    private DefaultTableModel tableModel;
    private JButton btnCadastrarHistorico;
    private JButton btnAlterarHistorico;

    public ManutencaoPanel() {
        super(new BorderLayout());
        historicoController = new HistoricoManutencaoController();

        List<HistoricoManutencao> historicos = historicoController.readHistoricos();
        tableModel = new DefaultTableModel(new Object[]{
            "ID", "Máquina ID", "Data", "Tipo", "Peças Trocadas", "Tempo de Parada", "Técnico ID", "Observações"
        }, 0);

        historicosTable = new JTable(tableModel);

        for (HistoricoManutencao historico : historicos) {
            tableModel.addRow(new Object[]{
                historico.getId(),
                historico.getMaquinaId(),
                historico.getData(),
                historico.getTipo(),
                historico.getPecasTrocadas(),
                historico.getTempoDeParada(),
                historico.getTecnicoID(),
                historico.getObservacoes()
            });
        }

        JScrollPane scrollPane = new JScrollPane(historicosTable);
        this.add(scrollPane, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCadastrarHistorico = new JButton("Cadastrar");
        btnAlterarHistorico = new JButton("Alterar");
        painelInferior.add(btnCadastrarHistorico);
        painelInferior.add(btnAlterarHistorico);
        this.add(painelInferior, BorderLayout.SOUTH);

        btnCadastrarHistorico.addActionListener(e -> {
            String maquinaIDStr = JOptionPane.showInputDialog("Máquina ID:");
            Long maquinaId = maquinaIDStr != null ? Long.parseLong(maquinaIDStr) : 0L;
            String tipo = JOptionPane.showInputDialog("Tipo:");
            String pecasTrocadas = JOptionPane.showInputDialog("Peças Trocadas:");
            String tempoParadaStr = JOptionPane.showInputDialog("Tempo de Parada:");
            Long tempoParada = tempoParadaStr != null ? Long.parseLong(tempoParadaStr) : 0L;
            String tecnicoID = JOptionPane.showInputDialog("Técnico ID:");
            String observacoes = JOptionPane.showInputDialog("Observações:");

            HistoricoManutencao novoHistorico = new HistoricoManutencao(
                null,
                maquinaId,
                LocalDate.now(),
                tipo,
                pecasTrocadas,
                tempoParada,
                tecnicoID,
                observacoes
            );

            historicoController.createHistorico(novoHistorico);
            tableModel.addRow(new Object[]{
                novoHistorico.getId(),
                novoHistorico.getMaquinaId(),
                novoHistorico.getData(),
                novoHistorico.getTipo(),
                novoHistorico.getPecasTrocadas(),
                novoHistorico.getTempoDeParada(),
                novoHistorico.getTecnicoID(),
                novoHistorico.getObservacoes()
            });
        });

        btnAlterarHistorico.addActionListener(e -> {
            int selectedRow = historicosTable.getSelectedRow();
            if (selectedRow >= 0) {
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                Long maquinaId = (Long) tableModel.getValueAt(selectedRow, 1);
                LocalDate data = (LocalDate) tableModel.getValueAt(selectedRow, 2);
                String tipo = JOptionPane.showInputDialog("Tipo:", tableModel.getValueAt(selectedRow, 3));
                String pecasTrocadas = JOptionPane.showInputDialog("Peças Trocadas:", tableModel.getValueAt(selectedRow, 4));
                String tempoParadaStr = JOptionPane.showInputDialog("Tempo de Parada:", tableModel.getValueAt(selectedRow, 5));
                Long tempoParada = tempoParadaStr != null ? Long.parseLong(tempoParadaStr) : 0L;
                String tecnicoID = JOptionPane.showInputDialog("Técnico ID:", tableModel.getValueAt(selectedRow, 6));
                String observacoes = JOptionPane.showInputDialog("Observações:", tableModel.getValueAt(selectedRow, 7));

                HistoricoManutencao historicoAlterado = new HistoricoManutencao(
                    id,
                    maquinaId,
                    data,
                    tipo,
                    pecasTrocadas,
                    tempoParada,
                    tecnicoID,
                    observacoes
                );

                // Atualiza o histórico no banco de dados
                historicoController.updateHistorico(selectedRow, historicoAlterado);
                HistoricoManutencaoAPI.putManutencao(id, historicoAlterado); // Salva no banco

                // Atualiza a tabela
                tableModel.setValueAt(tipo, selectedRow, 3);
                tableModel.setValueAt(pecasTrocadas, selectedRow, 4);
                tableModel.setValueAt(tempoParada, selectedRow, 5);
                tableModel.setValueAt(tecnicoID, selectedRow, 6);
                tableModel.setValueAt(observacoes, selectedRow, 7);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um histórico para alterar.");
            }
        });
    }
}
