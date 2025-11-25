package model;

public class Emprestimo {
    private String id;          // agora é String (ex: "001")
    private int idLivro;
    private int idUsuario;
    private String dataEmprestimo;
    private String dataDevolucao;

    public Emprestimo(String id, int idLivro, int idUsuario, String dataEmprestimo, String dataDevolucao) {
        this.id = id;
        this.idLivro = idLivro;
        this.idUsuario = idUsuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public String getId() { return id; }
    public int getIdLivro() { return idLivro; }
    public int getIdUsuario() { return idUsuario; }
    public String getDataEmprestimo() { return dataEmprestimo; }
    public String getDataDevolucao() { return dataDevolucao; }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
