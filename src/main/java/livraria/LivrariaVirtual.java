package livraria;

import Util.InputHandler;
import Util.Instance;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

public class LivrariaVirtual {
    private static final int MAX_IMPRESSOS = 10;
    private static final int MAX_ELETRONICOS = 20;
    private static final int MAX_VENDAS = 50;

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
                InputHandler.limparBuffer();
            } catch (InputMismatchException e) {
                opcao = InputHandler.getIntInput("Opção inválida! Por favor, insira um número.");
            }
            switch (opcao) {
                case 1 -> livrariaVirtual.cadastrarLivro();
                case 2 -> livrariaVirtual.realizarVenda();
                case 3 -> livrariaVirtual.listarLivrosImpressos();
                case 4 -> livrariaVirtual.listarLivrosEletronicos();
                case 5 -> livrariaVirtual.listarLivros();
                case 6 -> livrariaVirtual.listarVendas();
                default -> System.out.println("Opção inválida!");
            }

            cont = InputHandler.getIntInput("Deseja realizar mais uma operação? (1 - Sim | 2 - Não)\n");
        } while (cont == 1);
    }

    public void cadastrarLivro() {
        System.out.println("---- Cadastro de Livros ----");
        List<String> propriedadesLivroBase = getPropriedadesLivroBase();
        int tipo = InputHandler.getIntInput("Qual o tipo do livro a ser cadastrado? (1 - Impresso | 2 - Eletrônico | 3 - Ambos)\n");
        InputHandler.limparBuffer();

        if (tipo == 1) {
            setLivroImpresso(propriedadesLivroBase);
        } else if (tipo == 2) {
            setLivroEletronico(propriedadesLivroBase);
        } else if (tipo == 3) {
            setAmbosOsLivros(propriedadesLivroBase);
        } else {
            System.out.println("Código inválido!");
        }
    }

    private void inserirObjetoNoBancoDeDados(Object obj) {
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(obj);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    private List<String> getPropriedadesLivroBase() {
        String titulo = InputHandler.getStringInput("Qual o título do livro? ");
        int num;
        String autor;
        StringBuilder autores = new StringBuilder();

        do {
            autor = InputHandler.getStringInput("Qual o autor de " + titulo + ": ");
            autores.append(autor).append(", ");
            num = InputHandler.getIntInput("Deseja adicionar mais um autor? (1 - Sim | 2 - Não)\n");
            InputHandler.limparBuffer();
        } while (num == 1);

        if (autores.length() > 2) {
            autores.setLength(autores.length() - 2);
        }

        String editora = InputHandler.getStringInput("Qual a editora de publicação de " + titulo + "? ");
        float preco = InputHandler.getFloatInput("Qual o preço de " + titulo + "? ");

        List<String> propriedasLivroBase = new ArrayList<>();

        propriedasLivroBase.add(titulo);
        propriedasLivroBase.add(autores.toString());
        propriedasLivroBase.add(editora);
        propriedasLivroBase.add(String.valueOf(preco));

        return propriedasLivroBase;
    }

    private void setLivroImpresso(List<String> propriedadesLivroBase) {
        if (validarMaxImpressos()) {
            System.out.println("Número máximo de livros impressos alcançado!");
            return;
        }

        float frete = InputHandler.getFloatInput("Qual o valor do frete? ");
        int estoque = InputHandler.getIntInput("Quantos exemplares de " + propriedadesLivroBase.get(1) + " há em estoque? ");

        Impresso impresso = new Impresso(propriedadesLivroBase.get(0), propriedadesLivroBase.get(1), propriedadesLivroBase.get(2), Float.parseFloat(propriedadesLivroBase.get(3)), frete, estoque);

        inserirObjetoNoBancoDeDados(impresso);
    }

    private void setLivroEletronico(List<String> propriedadesLivroBase) {
        if (validarMaxEletronicos()) {
            System.out.println("Número máximo de livros eletrônicos alcançado!");
            return;
        }

        int tamanho = InputHandler.getIntInput("Qual o tamanho do arquivo em KB? ");

        Eletronico eletronico = new Eletronico(propriedadesLivroBase.get(0), propriedadesLivroBase.get(1), propriedadesLivroBase.get(2), Float.parseFloat(propriedadesLivroBase.get(3)), tamanho);

        inserirObjetoNoBancoDeDados(eletronico);
    }

    private void setAmbosOsLivros(List<String> propriedadesLivroBase) {
        setLivroImpresso(propriedadesLivroBase);
        setLivroEletronico(propriedadesLivroBase);
    }

    private boolean validarMaxImpressos() {
        return Instance.totalImpressos() >= MAX_IMPRESSOS;
    }

    private boolean validarMaxEletronicos() {
        return Instance.totalEletronico() >= MAX_ELETRONICOS;
    }

    public void realizarVenda(){
        if (Venda.getNumVendas() > MAX_VENDAS){
            System.out.println("Limite de vendas alcançado!");
            return;
        }

        System.out.println("---- Venda de Livro ----");
        String nome = InputHandler.getStringInput("Qual o nome do cliente? ");
        int quantidade = InputHandler.getIntInput("Quantos livros " + nome + " deseja comprar? ");
        int tipo, cod;
        List<Livro> livrosVendidos = new ArrayList<>();
        float totalVenda = 0;

        for (int i = 1; i <= quantidade; i++) {
            tipo = InputHandler.getIntInput("Qual o tipo do " + i + "º livro: (1 - Impresso | 2 - Eletrônico)\n");
            switch (tipo) {
                case 1 -> {
                    listarLivrosImpressos();
                    cod = InputHandler.getIntInput("Digite o código do livro desejado: ");
                    Impresso impresso = (Impresso) buscarLivroPorCodigo(cod);
                    if (impresso != null) {
                        if (impresso.getEstoque() <= 0) {
                            System.out.println("Não há mais exemplares de " + impresso.getTitulo() + " no estoque.");
                            return;
                        }
                        impresso.atualizarEstoque(cod);
                        livrosVendidos.add(impresso);
                        totalVenda += impresso.getPreco();
                    }
                }
                case 2 -> {
                    listarLivrosEletronicos();
                    cod = InputHandler.getIntInput("Digite o código do livro desejado: ");
                    Livro eletronico = buscarLivroPorCodigo(cod);
                    if (eletronico != null) {
                        livrosVendidos.add(eletronico);
                        totalVenda += eletronico.getPreco();
                    }
                }
                default -> {
                    System.out.println("Código inválido!");
                    i--;
                }
            }
        }

        if (livrosVendidos.isEmpty()){
            System.out.println("A venda não pôde ser concluída!\n");
            return;
        }

        Venda venda = new Venda(livrosVendidos, nome, totalVenda);

        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(venda);
        entityManager.getTransaction().commit();
        entityManager.close();

        System.out.println("Venda realizada com sucesso!\n");
    }

    public Livro buscarLivroPorCodigo(int cod){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        Livro livro = entityManager.find(Livro.class, cod);
        entityManager.close();

        if (livro != null){
            return livro;
        }

        System.out.println("Livro não encontrado!");
        return null;
    }

    public void listarLivrosImpressos(){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("\n---- Lista de Livros Impressos ----\n");
        List<Impresso> livrosImpressos = entityManager.createQuery("SELECT i FROM Impresso i", Impresso.class)
                .getResultList();

        for (Impresso livro : livrosImpressos) {
            if (livro.getEstoque() >= 1) {
                System.out.printf("Código: %d\nTítulo: %s\nAutores: %s\nEditora: %s\nPreço: R$%s\nEstoque: %d\nFrete: R$%.2f\n\n", livro.getCod(), livro.getTitulo(), livro.getAutores(), livro.getEditora(), livro.getPreco(), livro.getEstoque(), livro.getFrete());
            }
        }
        entityManager.close();
    }

    public void listarLivrosEletronicos(){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("\n---- Lista de Livros Eletrônicos ----\n");
        List<Eletronico> livrosEletronicos = entityManager.createQuery("SELECT i FROM Eletronico i", Eletronico.class)
                .getResultList();

        for (Eletronico livro : livrosEletronicos) {
            System.out.printf("Código: %d\nTítulo: %s\nAutores: %s\nEditora: %s\nPreço: R$%s\nTamanho: %dKB\n\n", livro.getCod(), livro.getTitulo(), livro.getAutores(), livro.getEditora(), livro.getPreco(), livro.getTamanho());
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
            System.out.printf("Título: %s\nAutores: %s\nEditora: %s\nPreço: R$%s\n", livro.getTitulo(), livro.getAutores(), livro.getEditora(), livro.getPreco());

            if (livro instanceof Impresso impresso) {
                System.out.printf("Tipo: Impresso\nEstoque: %s\nFrete: R$%.2f\n", impresso.getEstoque(), impresso.getFrete());
            } else if (livro instanceof Eletronico eletronico) {
                System.out.println("Tipo: Eletrônico\nTamanho: " + eletronico.getTamanho() + "KB");
            }

            System.out.println();
        }
        entityManager.close();
    }

    public void listarVendas(){
        EntityManager entityManager = Instance.getEntityManager();
        entityManager.getTransaction().begin();

        System.out.println("\n---- Lista de Vendas ----\n");
        List<Venda> vendas = entityManager.createQuery("SELECT i FROM Venda i", Venda.class)
                .getResultList();

        for (Venda venda : vendas) {

            System.out.printf("Código: %d\nCliente: %s\nValor: R$%.2f\nLista de livros comprados: \n", venda.getNumero(), venda.getCliente(), venda.getValor());
            for (Livro livro : venda.getLivros()) {
                System.out.println("- Código do livro: " + livro.getCod() + " | Título: " + livro.getTitulo() + " | Autor(es): " + livro.getAutores());
            }
            System.out.println();
        }
        entityManager.close();
    }
}
