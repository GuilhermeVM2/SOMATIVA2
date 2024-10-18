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

import com.example.controllers.FalhasController;
import com.example.model.Falhas;

public class FalhasPanel extends JPanel {
    private FalhasController falhasController;
    private JTable falhasTable;
    private DefaultTableModel tableModel;
    private JButton btnCadastrarFalha;
    private JButton btnAlterarFalha;

    // Construtor
    public FalhasPanel() {
        super(new BorderLayout());
        falhasController = new FalhasController();

        // Criar Tabela
        List<Falhas> falhas = falhasController.readFalhas();
        tableModel = new DefaultTableModel(new Object[] {
            "ID", "ID Máquina", "Data", "Problema", "Prioridade", "Operador"
        }, 0);

        falhasTable = new JTable(tableModel);

        for (Falhas falha : falhas) {
            tableModel.addRow(new Object[] {
                falha.getId(),
                falha.getMaquinaId(),
                falha.getData(),
                falha.getProblema(),
                falha.getPrioridade(),
                falha.getOperador()
            });
        }

        JScrollPane scrollPane = new JScrollPane(falhasTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Adicionar os botões
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCadastrarFalha = new JButton("Cadastrar");
        btnAlterarFalha = new JButton("Alterar");
        painelInferior.add(btnCadastrarFalha);
        painelInferior.add(btnAlterarFalha);
        this.add(painelInferior, BorderLayout.SOUTH);

        // Criar as ActionsListener para os Botões
        btnCadastrarFalha.addActionListener(e -> {
            // Coletar dados da nova falha
            String maquinaIdStr = JOptionPane.showInputDialog("ID Máquina:");
            long maquinaId = maquinaIdStr != null ? Long.parseLong(maquinaIdStr) : 0L;
            String problema = JOptionPane.showInputDialog("Problema:");
            String prioridade = JOptionPane.showInputDialog("Prioridade:");
            String operador = JOptionPane.showInputDialog("Operador:");

            // Criar um novo objeto Falhas
            Falhas novaFalha = new Falhas(
                null, // ID deve ser gerado ou fornecido de alguma forma
                maquinaId,
                LocalDate.now(),
                problema,
                prioridade,
                operador
            );

            // Adicionar a nova falha ao controlador
            falhasController.createFalha(novaFalha);

            // Atualizar a tabela
            tableModel.addRow(new Object[] {
                novaFalha.getId(),
                novaFalha.getMaquinaId(),
                novaFalha.getData(),
                novaFalha.getProblema(),
                novaFalha.getPrioridade(),
                novaFalha.getOperador()
            });
        });

        btnAlterarFalha.addActionListener(e -> {
            int selectedRow = falhasTable.getSelectedRow();
            if (selectedRow != -1) {
                // Obter os valores atuais
                String id = (String) tableModel.getValueAt(selectedRow, 0);
                Long maquinaId = (Long) tableModel.getValueAt(selectedRow, 1);
                LocalDate data = (LocalDate) tableModel.getValueAt(selectedRow, 2);
                String problema = (String) tableModel.getValueAt(selectedRow, 3);
                String prioridade = (String) tableModel.getValueAt(selectedRow, 4);
                String operador = (String) tableModel.getValueAt(selectedRow, 5);

                // Pedir ao usuário para alterar os valores
                String novoProblema = JOptionPane.showInputDialog("Problema:", problema);
                String novaPrioridade = JOptionPane.showInputDialog("Prioridade:", prioridade);
                String novoOperador = JOptionPane.showInputDialog("Operador:", operador);

                // Criar um objeto Falhas com os valores atualizados
                Falhas falhaAtualizada = new Falhas(
                    id,
                    maquinaId,
                    data,
                    novoProblema,
                    novaPrioridade,
                    novoOperador
                );

                // Atualizar a falha no controlador
                boolean sucesso = falhasController.updateFalha(falhaAtualizada);
                if (sucesso) {
                    // Atualizar os valores na tabela se a atualização foi bem-sucedida
                    tableModel.setValueAt(novoProblema, selectedRow, 3);
                    tableModel.setValueAt(novaPrioridade, selectedRow, 4);
                    tableModel.setValueAt(novoOperador, selectedRow, 5);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar a falha no banco de dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma falha para alterar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
