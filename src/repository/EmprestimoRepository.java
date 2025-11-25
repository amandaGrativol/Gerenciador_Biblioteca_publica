package repository;

import model.Emprestimo;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {

    private static List<Emprestimo> lista = new ArrayList<>();

    private static int contador = 1;

    // Gera ID formatado com 3 dígitos
    private String gerarId() {
        return String.format("%03d", contador++);
    }

    public void adicionar(Emprestimo e) {
        lista.add(e);
    }

    public Emprestimo criarEmprestimo(int idLivro, int idUsuario, String dataEmp, String dataDev) {
        String novoId = gerarId();
        Emprestimo emp = new Emprestimo(novoId, idLivro, idUsuario, dataEmp, dataDev);
        adicionar(emp);
        return emp;
    }

    public void excluir(String id) {
        lista.removeIf(e -> e.getId().equals(id));
    }

    public List<Emprestimo> listar() {
        return lista;
    }
}
