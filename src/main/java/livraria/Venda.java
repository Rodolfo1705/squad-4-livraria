package livraria;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

public class Venda {
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
}
