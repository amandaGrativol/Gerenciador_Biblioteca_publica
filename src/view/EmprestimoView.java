package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Emprestimo;
import repository.EmprestimoRepository;
import java.awt.*;
import java.util.List;

public class EmprestimoView extends JPanel {

    private EmprestimoRepository repo = new EmprestimoRepository();
    private JTable table;

    public EmprestimoView() {
        setLayout(new BorderLayout());

        // tabela
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnSair = new JButton("Sair");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> {
            try {
                int idLivro = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do Livro:"));
                int idUsuario = Integer.parseInt(JOptionPane.showInputDialog(this, "ID do Usuário:"));
                String dataEmp = JOptionPane.showInputDialog(this, "Data de Empréstimo:");
                String dataDev = JOptionPane.showInputDialog(this, "Data de Devolução:");

                repo.criarEmprestimo(idLivro, idUsuario, dataEmp, dataDev);
                loadTable();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados!");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = table.getSelectedRow();
            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione uma linha!");
                return;
            }

            String id = (String) table.getValueAt(linha, 0);
            repo.excluir(id);
            loadTable();
        });

        btnSair.addActionListener(e -> System.exit(0));

        loadTable();
    }

    private void loadTable() {
        List<Emprestimo> lista = repo.listar();

        String[] colunas = {"ID", "ID Livro", "ID Usuário", "Data Empréstimo", "Data Devolução"};
        String[][] dados = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            Emprestimo e = lista.get(i);
            dados[i] = new String[]{
                    e.getId(),                         // EX: "001"
                    String.valueOf(e.getIdLivro()),
                    String.valueOf(e.getIdUsuario()),
                    e.getDataEmprestimo(),
                    e.getDataDevolucao()
            };
        }

        table.setModel(new DefaultTableModel(dados, colunas));
    }
}
