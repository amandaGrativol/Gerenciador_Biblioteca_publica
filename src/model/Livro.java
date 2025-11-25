package model;

public class Livro {
    private int id;
    private String titulo;
    private String autor;
    private int ano;
    private boolean disponivel;

    public Livro(int id, String titulo, String autor, int ano, boolean disponivel){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.disponivel = disponivel;
    }

    public int getId(){ return id; }
    public String getTitulo(){ return titulo; }
    public String getAutor(){ return autor; }
    public int getAno(){ return ano; }
    public boolean isDisponivel(){ return disponivel; }

    public void setDisponivel(boolean d){ this.disponivel = d; }
}
