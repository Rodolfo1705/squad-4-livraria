package livraria;

public class Eletronico extends Livro{
    private int tamanho;

    @Override
    public String toString() {
        return "Virtual{" +
                "tamanho=" + tamanho +
                '}';
    }
}
