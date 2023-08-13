package livraria;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Eletronico")
public class Eletronico extends Livro{
    private int tamanho;

    public Eletronico(){}

    public Eletronico(String titulo, String autores, String editora, float preco, int tamanho) {
        super(titulo, autores, editora, preco);
        this.tamanho = tamanho;
    }

    public int getTamanho() {
        return tamanho;
    }

    @Override
    public String toString() {
        return "Virtual{" +
                "tamanho=" + tamanho +
                '}';
    }
}
