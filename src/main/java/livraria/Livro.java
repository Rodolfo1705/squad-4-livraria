package livraria;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "livro")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_livro", discriminatorType = DiscriminatorType.STRING)
public class Livro {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int cod;
    private String titulo;
    private String autores;
    private String editora;
    private float preco;

    public Livro(){}
    public Livro(String titulo, String autores, String editora, float preco) {
        this.titulo = titulo;
        this.autores = autores;
        this.editora = editora;
        this.preco = preco;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutores() {
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

    public void setAutores(String autores) {
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
        return "Livro: \nTítulo: " + titulo + "\nAutores: " + autores + "\nEditora: " + editora + "\nPreço=" + preco;
    }
}
