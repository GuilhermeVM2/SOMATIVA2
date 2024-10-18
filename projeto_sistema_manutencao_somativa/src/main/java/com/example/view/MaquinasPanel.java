package com.example.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.example.controllers.MaquinaController;
import com.example.model.Maquina;

public class MaquinasPanel extends JPanel {
    private MaquinaController maquinaController;
    private JTable maquinasTable;
    private DefaultTableModel tableModel;
    private JButton btnCadastrarMaquina;
    private JButton btnAlterarMaquina;

    // Construtor
    public MaquinasPanel() {
        super(new BorderLayout());
        maquinaController = new MaquinaController();

        // Criar Tabela
        List<Maquina> maquinas = maquinaController.readMaquinas();
        tableModel = new DefaultTableModel(new Object[] {
            "ID", "Código", "Nome", "Modelo", "Fabricante", "Data de Aquisição", "Tempo de Vida Estimado", "Localização", "Detalhes", "Manual"
        }, 0); 

        maquinasTable = new JTable(tableModel); 

        for (Maquina maquina : maquinas) {
            tableModel.addRow(new Object[]{
                maquina.getId(),
                maquina.getCodigo(),
                maquina.getNome(),
                maquina.getModelo(),
                maquina.getFabricante(),
                maquina.getDataAquisicao(),
                maquina.getTempoVidaEstimado(),
                maquina.getLocalizacao(),
                maquina.getDetalhes(),
                maquina.getManual()
            });
        }

        JScrollPane scrollPane = new JScrollPane(maquinasTable);
        this.add(scrollPane, BorderLayout.CENTER);

        // Adicionar os botões
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCadastrarMaquina = new JButton("Cadastrar");
        btnAlterarMaquina = new JButton("Alterar"); 
        painelInferior.add(btnCadastrarMaquina);
        painelInferior.add(btnAlterarMaquina); 
        this.add(painelInferior, BorderLayout.SOUTH);

        // Criar as ActionListeners para os Botões
        btnCadastrarMaquina.addActionListener(e -> {
            String codigo = JOptionPane.showInputDialog("Código:");
            String nome = JOptionPane.showInputDialog("Nome:");
            String modelo = JOptionPane.showInputDialog("Modelo:");
            String fabricante = JOptionPane.showInputDialog("Fabricante:");
            String tempoVidaEstimadoStr = JOptionPane.showInputDialog("Tempo de Vida Estimado:");
            Long tempoVidaEstimado = tempoVidaEstimadoStr != null ? Long.parseLong(tempoVidaEstimadoStr) : 0L;
            String localizacao = JOptionPane.showInputDialog("Localização:");
            String detalhes = JOptionPane.showInputDialog("Detalhes:");
            String manual = JOptionPane.showInputDialog("Manual:");

            Maquina novaMaquina = new Maquina(
                null, 
                codigo,
                nome,
                modelo,
                fabricante,
                LocalDate.now(), 
                tempoVidaEstimado,
                localizacao,
                detalhes,
                manual
            );

            maquinaController.createMaquina(novaMaquina);
            tableModel.addRow(new Object[]{
                novaMaquina.getId(),
                novaMaquina.getCodigo(),
                novaMaquina.getNome(),
                novaMaquina.getModelo(),
                novaMaquina.getFabricante(),
                novaMaquina.getDataAquisicao(),
                novaMaquina.getTempoVidaEstimado(),
                novaMaquina.getLocalizacao(),
                novaMaquina.getDetalhes(),
                novaMaquina.getManual()
            });
        });

        btnAlterarMaquina.addActionListener(e -> {
            int linhaSelecionada = maquinasTable.getSelectedRow();
            if (linhaSelecionada >= 0) {
                Maquina maquinaSelecionada = maquinaController.readMaquinas().get(linhaSelecionada);

                String novoCodigo = JOptionPane.showInputDialog("Código:", maquinaSelecionada.getCodigo());
                String novoNome = JOptionPane.showInputDialog("Nome:", maquinaSelecionada.getNome());
                String novoModelo = JOptionPane.showInputDialog("Modelo:", maquinaSelecionada.getModelo());
                String novoFabricante = JOptionPane.showInputDialog("Fabricante:", maquinaSelecionada.getFabricante());
                String novoTempoVidaEstimadoStr = JOptionPane.showInputDialog("Tempo de Vida Estimado:", maquinaSelecionada.getTempoVidaEstimado());
                Long novoTempoVidaEstimado = novoTempoVidaEstimadoStr != null ? Long.parseLong(novoTempoVidaEstimadoStr) : 0L;
                String novaLocalizacao = JOptionPane.showInputDialog("Localização:", maquinaSelecionada.getLocalizacao());
                String novosDetalhes = JOptionPane.showInputDialog("Detalhes:", maquinaSelecionada.getDetalhes());
                String novoManual = JOptionPane.showInputDialog("Manual:", maquinaSelecionada.getManual());

                Maquina maquinaAtualizada = new Maquina(
                    maquinaSelecionada.getId(), // Mantém o ID da máquina
                    novoCodigo,
                    novoNome,
                    novoModelo,
                    novoFabricante,
                    maquinaSelecionada.getDataAquisicao(),
                    novoTempoVidaEstimado,
                    novaLocalizacao,
                    novosDetalhes,
                    novoManual
                );

                maquinaController.updateMaquina(linhaSelecionada, maquinaAtualizada);
                tableModel.setValueAt(novoCodigo, linhaSelecionada, 1);
                tableModel.setValueAt(novoNome, linhaSelecionada, 2);
                tableModel.setValueAt(novoModelo, linhaSelecionada, 3);
                tableModel.setValueAt(novoFabricante, linhaSelecionada, 4);
                tableModel.setValueAt(novoTempoVidaEstimado, linhaSelecionada, 6);
                tableModel.setValueAt(novaLocalizacao, linhaSelecionada, 7);
                tableModel.setValueAt(novosDetalhes, linhaSelecionada, 8);
                tableModel.setValueAt(novoManual, linhaSelecionada, 9);
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione uma máquina para alterar.");
            }
        });
    }
}
