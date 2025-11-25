package repository;

import model.Livro;

import java.io.*;
import java.util.*;

public class LivroRepository {

    private List<Livro> lista = new ArrayList<>();
    private int nextId = 1; // começa no 1

    private final String ARQUIVO = "livros.csv";

    public LivroRepository() {
        carregar();
    }

    // gera 001, 002, 003...
    private int gerarId() {
        return nextId++;
    }

    // ADICIONAR livro (ID automático)
    public void adicionar(String titulo, String autor, int ano) {
        int idGerado = gerarId();
        lista.add(new Livro(idGerado, titulo, autor, ano, true));
        salvar();
    }

    // LISTAR livros
    public List<Livro> listar() {
        return lista;
    }

    // BUSCAR livro por ID
    public Livro buscar(int id) {
        return lista.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // MARCAR como indisponível (emprestado)
    public void marcarIndisponivel(int id) {
        Livro l = buscar(id);
        if (l != null) {
            l.setDisponivel(false);
            salvar();
        }
    }

    // MARCAR como disponível (devolvido)
    public void marcarDisponivel(int id) {
        Livro l = buscar(id);
        if (l != null) {
            l.setDisponivel(true);
            salvar();
        }
    }

    // EXCLUIR livro
    public void excluir(int id) {
        lista.removeIf(l -> l.getId() == id);
        salvar();
    }

    // SALVAR no CSV
    private void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Livro l : lista) {
                pw.println(l.getId() + ";" +
                        l.getTitulo() + ";" +
                        l.getAutor() + ";" +
                        l.getAno() + ";" +
                        l.isDisponivel());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CARREGAR do CSV
    private void carregar() {
        try {
            File f = new File(ARQUIVO);
            if (!f.exists()) return;

            try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] p = linha.split(";");

                    int id = Integer.parseInt(p[0]);
                    String titulo = p[1];
                    String autor = p[2];
                    int ano = Integer.parseInt(p[3]);
                    boolean disp = Boolean.parseBoolean(p[4]);

                    lista.add(new Livro(id, titulo, autor, ano, disp));

                    // atualiza o nextId para continuar a sequência
                    if (id >= nextId) {
                        nextId = id + 1;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
