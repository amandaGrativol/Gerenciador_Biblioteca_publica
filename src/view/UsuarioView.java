package view;

import javax.swing.*;
import repository.UsuarioRepository;
import model.Usuario;
import java.awt.*;
import java.util.List;

public class UsuarioView extends JPanel {
    private UsuarioRepository repo = new UsuarioRepository();
    private JTable table;

    public UsuarioView() {
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

        // Ação adicionar (SEM ID)
        add.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog("Nome:");
                if (nome == null || nome.isBlank()) throw new Exception();

                String email = JOptionPane.showInputDialog("Email:");
                if (email == null || email.isBlank()) throw new Exception();

                // chama o método correto → ID automático 001, 002...
                repo.adicionar(nome, email);

                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Entrada inválida!");
            }
        });

        // Ação excluir
        del.addActionListener(e -> {
            try {
                String id = JOptionPane.showInputDialog("ID para excluir:");
                if (id == null || id.isBlank()) throw new Exception();

                repo.excluir(id);
                loadTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "ID inválido!");
            }
        });

        // Botão sair
        sair.addActionListener(e -> {
            int opcao = JOptionPane.showConfirmDialog(
                    null,
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

    // Atualiza tabela
    private void loadTable() {
        List<Usuario> lista = repo.listar();
        String[] cols = {"ID", "Nome", "Email"};
        String[][] data = new String[lista.size()][3];

        for (int i = 0; i < lista.size(); i++) {
            Usuario u = lista.get(i);
            data[i] = new String[]{
                    u.getId(),
                    u.getNome(),
                    u.getEmail()
            };
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, cols));
    }
}
