package view;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        setTitle("Sistema Biblioteca");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Abas principais
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Livros", new LivroView());
        tabs.add("Usuários", new UsuarioView());
        tabs.add("Empréstimos", new EmprestimoView());

        // Layout da janela
        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }
}
