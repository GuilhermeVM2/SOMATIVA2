package com.example.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.example.controllers.TecnicosController;
import com.example.model.Tecnicos;

public class TecnicosPanel extends JPanel {
    private TecnicosController tecnicosController;
    private JTable tecnicosTable;
    private DefaultTableModel tableModel;
    private JButton btnCadastrarTecnico;
    private JButton btnAlterarTecnico;
    private JButton btnExcluirTecnico;

    // Construtor
    public TecnicosPanel() {
        super(new BorderLayout());
        tecnicosController = new TecnicosController();

        // Criar Tabela
        List<Tecnicos> tecnicos = tecnicosController.readTecnicos();
        tableModel = new DefaultTableModel(new Object[]{
            "ID", "Nome", "Especialidade", "Disponibilidade"
        }, 0);

        tecnicosTable = new JTable(tableModel);

        for (Tecnicos tecnico : tecnicos) {
            tableModel.addRow(new Object[]{
                tecnico.getId(),
                tecnico.getNome(),
                tecnico.getEspecialidade(),
                tecnico.getDisponibilidade()
            });
        }

        JScrollPane scrollPane = new JScrollPane(tecnicosTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Adicionar os botões
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCadastrarTecnico = new JButton("Cadastrar");
        btnAlterarTecnico = new JButton("Alterar");
        btnExcluirTecnico = new JButton("Excluir");
        painelInferior.add(btnCadastrarTecnico);
        painelInferior.add(btnAlterarTecnico);
        painelInferior.add(btnExcluirTecnico);
        this.add(painelInferior, BorderLayout.SOUTH);

        // ActionListener para o botão "Cadastrar"
        btnCadastrarTecnico.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Nome:");
            String especialidade = JOptionPane.showInputDialog("Especialidade:");
            String disponibilidade = JOptionPane.showInputDialog("Disponibilidade:");

            Tecnicos novoTecnico = new Tecnicos(
                null, // O ID será gerado pela API após o cadastro
                nome,
                especialidade,
                disponibilidade
            );

            // Adicionar o novo técnico ao controlador e persistir no banco via API
            tecnicosController.createTecnico(novoTecnico);

            // Atualizar a tabela com os dados do técnico
            tableModel.addRow(new Object[]{
                novoTecnico.getId(),
                novoTecnico.getNome(),
                novoTecnico.getEspecialidade(),
                novoTecnico.getDisponibilidade()
            });
        });

        // ActionListener para o botão "Alterar"
        btnAlterarTecnico.addActionListener(e -> {
            int selectedRow = tecnicosTable.getSelectedRow();
            if (selectedRow != -1) {
                String nome = (String) tableModel.getValueAt(selectedRow, 1);
                String especialidade = (String) tableModel.getValueAt(selectedRow, 2);
                String disponibilidade = (String) tableModel.getValueAt(selectedRow, 3);

                String novoNome = JOptionPane.showInputDialog("Nome:", nome);
                String novaEspecialidade = JOptionPane.showInputDialog("Especialidade:", especialidade);
                String novaDisponibilidade = JOptionPane.showInputDialog("Disponibilidade:", disponibilidade);

                tableModel.setValueAt(novoNome, selectedRow, 1);
                tableModel.setValueAt(novaEspecialidade, selectedRow, 2);
                tableModel.setValueAt(novaDisponibilidade, selectedRow, 3);

                Tecnicos tecnicoAtualizado = new Tecnicos(
                    (String) tableModel.getValueAt(selectedRow, 0), // ID
                    novoNome,
                    novaEspecialidade,
                    novaDisponibilidade
                );

                // Atualizar o técnico no controlador e persistir no banco via API
                tecnicosController.updateTecnico(selectedRow, tecnicoAtualizado);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para alterar.");
            }
        });

        // ActionListener para o botão "Excluir"
        btnExcluirTecnico.addActionListener(e -> {
            int selectedRow = tecnicosTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja excluir este técnico?", "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    String idTecnico = (String) tableModel.getValueAt(selectedRow, 0);

                    tecnicosController.deleteTecnico(idTecnico);

                    tableModel.removeRow(selectedRow);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione uma linha para excluir.");
            }
        });
    }
}
