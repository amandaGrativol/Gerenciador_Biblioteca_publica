package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import repository.LivroRepository;
import model.Livro;

import java.awt.*;
import java.util.List;

public class LivroView extends JPanel {

    // ✅ REPOSITÓRIO COMPARTILHADO
    private LivroRepository repo;
    private JTable table;

    public LivroView(LivroRepository repo) {
        this.repo = repo;

        setLayout(new BorderLayout());

        // Tabela
        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));

        JButton add = new JButton("Adicionar");
        JButton del = new JButton("Excluir");
        JButton sair = new JButton("Sair");

        painelBotoes.add(add);
        painelBotoes.add(Box.createHorizontalStrut(10));
        painelBotoes.add(del);
        painelBotoes.add(Box.createHorizontalStrut(10));
        painelBotoes.add(sair);

        add(painelBotoes, BorderLayout.SOUTH);

        // AÇÃO ADICIONAR
        add.addActionListener(e -> adicionarLivro());

        // AÇÃO EXCLUIR
        del.addActionListener(e -> excluirLivro());

        // AÇÃO SAIR
        sair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja sair?",
                    "Sair",
                    JOptionPane.YES_NO_OPTION
            );
            if (opcao == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        loadTable();
    }

    private void adicionarLivro() {
        try {
            String titulo = JOptionPane.showInputDialog(this, "Título:");
            if (titulo == null || titulo.isBlank()) return;

            String autor = JOptionPane.showInputDialog(this, "Autor:");
            if (autor == null || autor.isBlank()) return;

            String anoStr = JOptionPane.showInputDialog(this, "Ano:");
            if (anoStr == null) return;

            int ano = Integer.parseInt(anoStr);

            repo.adicionar(titulo, autor, ano);
            loadTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Entrada inválida!");
        }
    }

    private void excluirLivro() {
        try {
            String idStr = JOptionPane.showInputDialog(this, "ID para excluir:");
            if (idStr == null) return;

            int id = Integer.parseInt(idStr);
            repo.excluir(id);
            loadTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ID inválido!");
        }
    }

    private void loadTable() {
        List<Livro> lista = repo.listar();
        String[] cols = {"ID", "Título", "Autor", "Ano", "Disponível"};
        String[][] data = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            Livro l = lista.get(i);
            data[i] = new String[]{
                    String.valueOf(l.getId()),
                    l.getTitulo(),
                    l.getAutor(),
                    String.valueOf(l.getAno()),
                    l.isDisponivel() ? "Sim" : "Não"
            };
        }

        table.setModel(new DefaultTableModel(data, cols));
    }
}
