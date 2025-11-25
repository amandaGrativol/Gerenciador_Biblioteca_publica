package repository;

import model.Usuario;
import java.util.*;
import java.io.*;

public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>();
    private String file = "usuarios.csv";
    private int nextId = 1;   // começa no 1, mas vira "001"

    public UsuarioRepository() {
        carregar();
    }

    // Gera ID no formato 001, 002, 003...
    private String gerarId() {
        String idFormatado = String.format("%03d", nextId);
        nextId++;
        return idFormatado;
    }

    // ADICIONAR usuário sem pedir ID
    public void adicionar(String nome, String email) {
        String idGerado = gerarId();
        usuarios.add(new Usuario(idGerado, nome, email));
        salvar();
    }

    // EXCLUIR por ID STRING
    public void excluir(String id) {
        usuarios.removeIf(x -> x.getId().equals(id));
        salvar();
    }

    // LISTAR
    public List<Usuario> listar() {
        return usuarios;
    }

    // BUSCAR
    public Usuario buscar(String id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // CARREGAR CSV
    private void carregar() {
        try {
            File f = new File(file);
            if (!f.exists()) return;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");

                String id = p[0];
                String nome = p[1];
                String email = p[2];

                usuarios.add(new Usuario(id, nome, email));

                // Atualiza o nextId sem quebrar o formato
                int numero = Integer.parseInt(id);
                if (numero >= nextId) {
                    nextId = numero + 1;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // SALVAR CSV
    private void salvar() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Usuario u : usuarios) {
                bw.write(u.getId() + ";" + u.getNome() + ";" + u.getEmail());
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
