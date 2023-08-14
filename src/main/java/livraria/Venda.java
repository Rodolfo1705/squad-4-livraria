package livraria;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendas")
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numero;
    @ManyToMany
    @JoinTable(name = "venda_livro",
            joinColumns = @JoinColumn(name = "venda_id"),
            inverseJoinColumns = @JoinColumn(name = "livro_id"))
    private final List<Livro>livros = new ArrayList<>();
    private static int numVendas;
    private String cliente;
    private float valor;

    public Venda(){}

    public Venda(List<Livro> livros, String cliente, float valor){
        this.livros.addAll(livros);
        this.cliente = cliente;
        this.valor = valor;
        numVendas++;
    }

    public void addLivro(Livro livro){
        livros.add(livro);
    }

    public void listarLivros(){
        for (Livro livro: livros) {
            System.out.println(livro.toString());
        }
    }

    public Long getNumero() {
        return numero;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public static int getNumVendas() {
        return numVendas;
    }

    public String getCliente() {
        return cliente;
    }

    public float getValor() {
        return valor;
    }
}
