package livraria;

import java.util.List;

public abstract class Livro {
    private String titulo;
    private List<String> autores;
    private String editora;
    private float preco;

    public String getTitulo() {
        return titulo;
    }

    public List<String> getAutores() {
        return autores;
    }

    public String getEditora() {
        return editora;
    }

    public float getPreco() {
        return preco;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutores(List<String> autores) {
        this.autores = autores;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autores='" + autores + '\'' +
                ", editora='" + editora + '\'' +
                ", preco=" + preco +
                '}';
    }
}
