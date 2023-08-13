package livraria;

import Util.Instance;
import Util.InputHandler;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

public class LivrariaVirtual {
    private static int MAX_IMPRESSOS = 10;
    private static int MAX_ELETRONICOS = 20;
    private static int MAX_VENDAS = 50;
    private List<Impresso> impressos = new ArrayList<>();
    private int numEletronicos;
    private int numVendas;

    public void cadastrarLivro(){
        System.out.println("---- Cadastro de Livros ----");
        int tipo = InputHandler.getIntInput("Qual o tipo do livro a ser cadastrado? (1 - Impresso | 2 - Eletrônico | 3 - Ambos)\n");
        InputHandler.getStringInput(""); // Limpa o buffer
        switch (tipo) {
            case 1:
                if (Instance.totalImpressos() >= MAX_IMPRESSOS){
                    System.out.println("Número máximo de livros impressos alcançado!");
                } else {
                    Livro livroBase = criarLivro();

                    float frete = InputHandler.getFloatInput("Qual o valor do frete? ");
                    int estoque = InputHandler.getIntInput("Quantos exemplares de " + livroBase.getTitulo() + " há em estoque? " );

                    Impresso impresso = new Impresso(livroBase.getTitulo(), livroBase.getAutores(), livroBase.getEditora(), livroBase.getPreco(), frete, estoque);

                    EntityManager entityManager = Instance.getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.merge(impresso);
                    entityManager.getTransaction().commit();
                    entityManager.close();
                }
                break;
            case 2:
                if (Instance.totalEletronico() >= MAX_ELETRONICOS){
                    System.out.println("Número máximo de livros eletrônicos alcançado!");
                } else {
                    Livro livroBase = criarLivro();

                    int tamanho = InputHandler.getIntInput("Qual o tamanho do arquivo em KB? ");

                    Eletronico eletronico = new Eletronico(livroBase.getTitulo(), livroBase.getAutores(), livroBase.getEditora(), livroBase.getPreco(), tamanho);

                    EntityManager entityManager = Instance.getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.merge(eletronico);
                    entityManager.getTransaction().commit();
                    entityManager.close();
                }
                break;
            case 3:
                Livro livroBase = criarLivro();

                if (Instance.totalImpressos() >= MAX_IMPRESSOS){
                    System.out.println("Número máximo de livros impressos alcançado!");
                } else {
                    float frete = InputHandler.getFloatInput("Qual o valor do frete? ");
                    int estoque = InputHandler.getIntInput("Quantos exemplares de " + livroBase.getTitulo() + " há em estoque? " );

                    Impresso impresso = new Impresso(livroBase.getTitulo(), livroBase.getAutores(), livroBase.getEditora(), livroBase.getPreco(), frete, estoque);

                    EntityManager entityManager = Instance.getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.merge(impresso);
                    entityManager.getTransaction().commit();
                    entityManager.close();
                }
                if (Instance.totalEletronico() >= MAX_ELETRONICOS){
                    System.out.println("Número máximo de livros eletrônicos alcançado!");
                } else {
                    int tamanho = InputHandler.getIntInput("Qual o tamanho do arquivo em KB? ");

                    Eletronico eletronico = new Eletronico(livroBase.getTitulo(), livroBase.getAutores(), livroBase.getEditora(), livroBase.getPreco(), tamanho);

                    EntityManager entityManager = Instance.getEntityManager();
                    entityManager.getTransaction().begin();
                    entityManager.merge(eletronico);
                    entityManager.getTransaction().commit();
                    entityManager.close();
                }

                break;
            default:
                System.out.println("Código inválido!");
                break;
        }
    }

    public Livro criarLivro(){
        String titulo = InputHandler.getStringInput("Qual o título do livro? ");
        int num; String autor; StringBuilder autores = new StringBuilder();
        do {
            autor = InputHandler.getStringInput("Qual o autor de " + titulo + ": ");
            autores.append(autor).append(", ");
            num = InputHandler.getIntInput("Deseja adicionar mais um autor? (1 - Sim | 2 - Não)\n");
            InputHandler.getStringInput("");
        } while (num == 1);

        if (autores.length() > 2) {
            autores.setLength(autores.length() - 2);
        }

        String editora = InputHandler.getStringInput("Qual a editora de publicação de " + titulo + "? ");
        float preco = InputHandler.getFloatInput("Qual o preço de " + titulo + "? ");

        return new Livro(titulo, autores.toString(), editora, preco);
    }

    public void realizarVenda(){
        System.out.println("---- Venda de Livro ----");
        String nome = InputHandler.getStringInput("Qual o nome do cliente? ");
        int quantidade = InputHandler.getIntInput("Quantos livros " + nome + " deseja comprar? ");
        int tipo;
        List<Impresso> livrosImpressos;
        List<Eletronico> livrosEletronicos;
        for (int i = 1; i <= quantidade; i++){
            tipo = InputHandler.getIntInput("Qual o tipo do " + i +"º livro: (1 - Impresso | 2 - Eletrônico)\n");
            //mostrar lista e esolher livro
        }
    }

    public void listarLivrosImpressos(){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("\n---- Lista de Livros Impressos ----\n");
        List<Impresso> livrosImpressos = entityManager.createQuery("SELECT i FROM Impresso i", Impresso.class)
                .getResultList();

        for (Impresso livro : livrosImpressos) {
            System.out.println("Código: " + livro.getCod() +
                    "\nTítulo: " + livro.getTitulo() +
                    "\nAutores: " + livro.getAutores() +
                    "\nEditora: " + livro.getEditora() +
                    "\nPreço: R$" + livro.getPreco() +
                    "\nEstoque: " + livro.getEstoque() + " unidades" +
                    "\nFrete: R$" + livro.getFrete() + "\n");
        }
        entityManager.close();
    }

    public void listarLivrosELetronicos(){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("\n---- Lista de Livros Eletrônicos ----\n");
        List<Eletronico> livrosEletronicos = entityManager.createQuery("SELECT i FROM Eletronico i", Eletronico.class)
                .getResultList();

        for (Eletronico livro : livrosEletronicos) {
            System.out.println("Código: " + livro.getCod() +
                    "\nTítulo: " + livro.getTitulo() +
                    "\nAutores: " + livro.getAutores() +
                    "\nEditora: " + livro.getEditora() +
                    "\nPreço: R$" + livro.getPreco() +
                    "\nTamanho: " + livro.getTamanho() + "KB\n");
        }
        entityManager.close();
    }

    public void listarLivros(){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("\n---- Lista de Livros ----\n");
        List<Livro> livros = entityManager.createQuery("SELECT l FROM Livro l", Livro.class)
                .getResultList();

        livros.sort(Comparator.comparing(Livro::getTitulo));
        for (Livro livro : livros) {
            System.out.println("Título: " + livro.getTitulo() +
                    "\nAutores: " + livro.getAutores() +
                    "\nEditora: " + livro.getEditora() +
                    "\nPreço: R$" + livro.getPreco());

            if (livro instanceof Impresso) {
                Impresso impresso = (Impresso) livro;
                System.out.println("Tipo: Impresso" +
                        "\nEstoque: " + impresso.getEstoque() +
                        "\nFrete: R$" + impresso.getFrete());
            } else if (livro instanceof Eletronico) {
                Eletronico eletronico = (Eletronico) livro;
                System.out.println("Tipo: Eletrônico" +
                        "\nTamanho: " + eletronico.getTamanho() + "KB");
            }

            System.out.println();
        }
        entityManager.close();
    }

    public void listarVendas(){}

    public static void main(String[] args) {
        LivrariaVirtual livrariaVirtual = new LivrariaVirtual();

        int cont;
        do {
            System.out.println("---- Menu ----");
            System.out.println("1. Cadastrar livro");
            System.out.println("2. Realizar venda");
            System.out.println("3. Listar livros impressos");
            System.out.println("4. Listar livros eletrônicos");
            System.out.println("5. Listar livros");
            System.out.println("6. Listar vendas");
            int opcao;
            try {
                opcao = InputHandler.getIntInput("");
            } catch (InputMismatchException e) {
                opcao = InputHandler.getIntInput("Opção inválida! Por favor, insira um número.");
            }
            switch (opcao) {
                case 1:
                    livrariaVirtual.cadastrarLivro();
                    break;
                case 2:
                    livrariaVirtual.realizarVenda();
                    break;
                case 3:
                    livrariaVirtual.listarLivrosImpressos();
                    break;
                case 4:
                    livrariaVirtual.listarLivrosELetronicos();
                    break;
                case 5:
                    livrariaVirtual.listarLivros();
                    break;
                case 6:
                    livrariaVirtual.listarVendas();
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            cont = InputHandler.getIntInput("Deseja realizar mais uma operação? (1 - Sim | 2 - Não)\n");
        } while (cont == 1);
    }
}
