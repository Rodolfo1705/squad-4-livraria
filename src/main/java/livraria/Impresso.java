package livraria;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("Impresso")
public class Impresso extends Livro{
    private float frete;
    private int estoque;

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

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

    public void atualizarEstoque(int cod){
        EntityManager entityManager = Util.Instance.getEntityManager();
        entityManager.getTransaction().begin();

        Impresso impresso = entityManager.find(Impresso.class, cod);

        if (impresso != null){
            int novoEstoque = impresso.getEstoque() - 1;

            impresso.setEstoque(novoEstoque);
            entityManager.merge(impresso);
            entityManager.getTransaction().commit();
            entityManager.close();


            if (novoEstoque == 1){
                System.out.println("\nHá apenas mais um " + impresso.getTitulo() + " no estoque.");
            }
        }
    }

    public float getFrete() {
        return frete;
    }

    public int getEstoque() {
        return estoque;
    }

    @Override
    public String toString() {
        return "Impresso{" +
                "frete=" + frete +
                ", estoque=" + estoque +
                '}';
    }
}
