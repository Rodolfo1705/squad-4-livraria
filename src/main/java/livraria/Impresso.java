package livraria;

public class Impresso extends Livro{
    private float frete;
    private int estoque;

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
