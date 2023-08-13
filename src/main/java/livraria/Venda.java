package livraria;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private final List<Livro>livros = new ArrayList<>();
    private static int numVendas;
    private int numero = 1 + numVendas;
    private String cliente;
    private float valor;

    public void addLivro(Livro livro){
        livros.add(livro);
    }

    public void listarLivros(){
        for (Livro livro: livros) {
            System.out.println(livro.toString());
        }
    }

    public Long getId() {
        return id;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public static int getNumVendas() {
        return numVendas;
    }

    public int getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public float getValor() {
        return valor;
    }
}
