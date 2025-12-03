package view;

import repository.EmprestimoRepository;
import repository.LivroRepository;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Sistema Biblioteca");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ✅ REPOSITÓRIOS ÚNICOS
        LivroRepository livroRepo = new LivroRepository();
        EmprestimoRepository emprestimoRepo = new EmprestimoRepository();

        // Abas principais
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Livros", new LivroView(livroRepo));
        tabs.add("Usuários", new UsuarioView());
        tabs.add("Empréstimos", new EmprestimoView(livroRepo, emprestimoRepo));

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }
}
