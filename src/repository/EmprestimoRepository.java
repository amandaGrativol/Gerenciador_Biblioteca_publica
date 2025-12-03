package repository;

import model.Emprestimo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {

    private static List<Emprestimo> lista = new ArrayList<>();
    private static int contador = 1;

    private final String ARQUIVO = "emprestimos.csv";

    public EmprestimoRepository() {
        carregar();
    }

    // Gera ID formatado com 3 dígitos
    private String gerarId() {
        return String.format("%03d", contador++);
    }

    public void adicionar(Emprestimo e) {
        lista.add(e);
        salvar();
    }

    public Emprestimo criarEmprestimo(int idLivro, int idUsuario, String dataEmp, String dataDev) {
        String novoId = gerarId();
        Emprestimo emp = new Emprestimo(novoId, idLivro, idUsuario, dataEmp, dataDev);
        adicionar(emp);
        return emp;
    }

    public void excluir(String id) {
        lista.removeIf(e -> e.getId().equals(id));
        salvar();
    }

    public List<Emprestimo> listar() {
        return lista;
    }

    // 🔹 Busca empréstimo ativo de um livro
    public Emprestimo buscarEmprestimoAtivoPorLivro(int idLivro) {
        for (Emprestimo e : lista) {
            if (e.getIdLivro() == idLivro && e.isAtivo()) {
                return e;
            }
        }
        return null;
    }

    // 🔹 Devolve o livro (encerra o empréstimo)
    public boolean devolverLivro(int idLivro, String dataDevolucao) {
        Emprestimo e = buscarEmprestimoAtivoPorLivro(idLivro);
        if (e != null) {
            e.setDataDevolucao(dataDevolucao);
            salvar();
            return true;
        }
        return false;
    }

    // ================= CSV =================

    // SALVAR
    private void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQUIVO))) {
            for (Emprestimo e : lista) {
                pw.println(
                        e.getId() + ";" +
                                e.getIdLivro() + ";" +
                                e.getIdUsuario() + ";" +
                                e.getDataEmprestimo() + ";" +
                                (e.getDataDevolucao() == null ? "" : e.getDataDevolucao())
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CARREGAR
    private void carregar() {
        try {
            File f = new File(ARQUIVO);
            if (!f.exists()) return;

            try (BufferedReader br = new BufferedReader(new FileReader(ARQUIVO))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] p = linha.split(";");

                    String id = p[0];
                    int idLivro = Integer.parseInt(p[1]);
                    int idUsuario = Integer.parseInt(p[2]);
                    String dataEmp = p[3];
                    String dataDev = p.length > 4 && !p[4].isEmpty() ? p[4] : null;

                    lista.add(new Emprestimo(id, idLivro, idUsuario, dataEmp, dataDev));

                    int numId = Integer.parseInt(id);
                    if (numId >= contador) {
                        contador = numId + 1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
