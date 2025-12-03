package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Emprestimo;
import model.Livro;
import repository.EmprestimoRepository;
import repository.LivroRepository;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoView extends JPanel {

    private LivroRepository livroRepo;
    private EmprestimoRepository emprestimoRepo;
    private JTable table;

    public EmprestimoView(LivroRepository livroRepo,
                          EmprestimoRepository emprestimoRepo) {

        this.livroRepo = livroRepo;
        this.emprestimoRepo = emprestimoRepo;

        setLayout(new BorderLayout());

        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnEmprestar = new JButton("Adicionar");
        JButton btnDevolver = new JButton("Devolver");
        JButton btnSair = new JButton("Sair");

        painelBotoes.add(btnEmprestar);
        painelBotoes.add(btnDevolver);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);

        btnEmprestar.addActionListener(e -> emprestar());
        btnDevolver.addActionListener(e -> devolver());
        btnSair.addActionListener(e -> System.exit(0));

        loadTable();
    }

    private void emprestar() {
        try {
            int idLivro = Integer.parseInt(
                    JOptionPane.showInputDialog(this, "ID do Livro:")
            );

            int idUsuario = Integer.parseInt(
                    JOptionPane.showInputDialog(this, "ID do Usuário:")
            );

            Livro livro = livroRepo.buscar(idLivro);
            if (livro == null || !livro.isDisponivel()) {
                JOptionPane.showMessageDialog(this, "Livro indisponível!");
                return;
            }

            emprestimoRepo.criarEmprestimo(
                    idLivro,
                    idUsuario,
                    LocalDate.now().toString(),
                    null
            );

            livroRepo.marcarIndisponivel(idLivro);
            loadTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados!");
        }
    }

    private void devolver() {
        try {
            int idLivro = Integer.parseInt(
                    JOptionPane.showInputDialog(this, "ID do Livro:")
            );

            boolean ok = emprestimoRepo.devolverLivro(
                    idLivro,
                    LocalDate.now().toString()
            );

            if (!ok) {
                JOptionPane.showMessageDialog(this, "Empréstimo não encontrado!");
                return;
            }

            livroRepo.marcarDisponivel(idLivro);
            loadTable();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro!");
        }
    }

    private void loadTable() {
        List<Emprestimo> lista = emprestimoRepo.listar();

        String[] colunas = {"ID", "Livro", "Usuário", "Empréstimo", "Devolução"};
        String[][] dados = new String[lista.size()][5];

        for (int i = 0; i < lista.size(); i++) {
            Emprestimo e = lista.get(i);
            dados[i] = new String[]{
                    e.getId(),
                    String.valueOf(e.getIdLivro()),
                    String.valueOf(e.getIdUsuario()),
                    e.getDataEmprestimo(),
                    e.getDataDevolucao()
            };
        }

        table.setModel(new DefaultTableModel(dados, colunas));
    }
}
