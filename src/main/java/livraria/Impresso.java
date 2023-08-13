package livraria;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("impresso")
public class Impresso extends Livro{
    private float frete;
    private int estoque;

    public Impresso(){}

    public Impresso(Livro livro, float frete, int estoque) {
        this.frete = frete;
        this.estoque = estoque;
    }

    public Impresso(String titulo, String autores, String editora, float preco, float frete, int estoque) {
        super(titulo, autores, editora, preco);
        this.frete = frete;
        this.estoque = estoque;
    }

    public void atualizarEstoque(){
        this.estoque -= 1;
    }

    @Override
    public String toString() {
        return "Impresso{" +
                "frete=" + frete +
                ", estoque=" + estoque +
                '}';
    }
}
