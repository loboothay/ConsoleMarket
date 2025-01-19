package main;

import model.Produto;
import utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Mercado {
    private static Scanner input = new Scanner(System.in);
    private static ArrayList<Produto> produtos;
    private static Map<Produto, Integer> carrinho;

    //region Cores
    public static final String RESET = "\u001B[0m";
    public static final String VERDE = "\u001B[32m";
    public static final String AZUL = "\u001B[34m";
    public static final String AMARELO = "\u001B[33m";
    public static final String ROXO = "\u001B[35m";
    public static final String VERMELHO = "\u001B[31m";
    //endregion

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        produtos = new ArrayList<>();
        carrinho = new HashMap<>();
        menu();
    }

    private static void menu() {
        limparTela();

        System.out.println(VERDE + "+------------------------------------------+" + RESET);
        System.out.println(VERDE + "|" + AZUL + "              MERCADO VIRTUAL             " + VERDE + "|" + RESET);
        System.out.println(VERDE + "+------------------------------------------+" + RESET);
        System.out.println(VERDE + "|" + AMARELO + "              MENU PRINCIPAL              " + VERDE + "|" + RESET);
        System.out.println(VERDE + "|                                          |" + RESET);
        System.out.println(VERDE + "|  " + ROXO + "[1]" + AZUL + " Cadastrar Produtos                  " + VERDE + "|" + RESET);
        System.out.println(VERDE + "|  " + ROXO + "[2]" + AZUL + " Listar Produtos                     " + VERDE + "|" + RESET);
        System.out.println(VERDE + "|  " + ROXO + "[3]" + AZUL + " Realizar Compra                     " + VERDE + "|" + RESET);
        System.out.println(VERDE + "|  " + ROXO + "[4]" + AZUL + " Visualizar Carrinho                 " + VERDE + "|" + RESET);
        System.out.println(VERDE + "|  " + ROXO + "[5]" + VERMELHO + " Sair                                " + VERDE + "|" + RESET);
        System.out.println(VERDE + "|                                          |" + RESET);
        System.out.println(VERDE + "+------------------------------------------+" + RESET);

        int opcao = input.nextInt();

        switch (opcao){
            case 1:
                cadastrarProdutos();
                break;
            case 2:
                listarProdutos();
                break;
            case 3:
                comprarProdutos();
                break;
            case 4:
                verCarrinho();
                break;
            case 5:
                System.out.println("Volte sempre!");
                System.exit(0);
                break;
            default:
                System.out.println("Opção Inválida!");
                menu();
                break;
        }
    }

    private static void cadastrarProdutos() {
        System.out.println("Digite o nome do produto: ");
        String nome = input.next();
        System.out.println("Digite o preço do produto: ");
        double preco = input.nextDouble();

        Produto produto = new Produto(nome, preco);
        produtos.add(produto);

        System.out.println(produto.getNome() + " cadastrado com sucesso!");
        menu();
    }

    private static void listarProdutos() {
        if (produtos.size() > 0) {
            System.out.println("Lista de produtos: \n");
            for (Produto produto : produtos) {
                System.out.println(produto.getNome() + " - " + produto.getPreco());
            }
            menu();
        }else {
            System.out.println("Lista de produtos está vazia! \n");
            menu();
        }
    }

    private static void comprarProdutos() {
        if (produtos.size() > 0) {
            System.out.println("Código do produto: ");
            System.out.println("-------------- Produtos Disponíveis --------------");
            for (Produto produto : produtos) {
                System.out.println(produto.getId() + " - " + produto.getNome() + " - " + produto.getPreco());
            }
            int id = Integer.parseInt(input.next());
            boolean isPresent = false;
            for (Produto produto : produtos) {
                if (produto.getId() == id) {
                    int qtd = 0;
                    try {
                        qtd = carrinho.get(produto);
                        carrinho.put(produto, qtd + 1);
                    }catch (NullPointerException e) {
                        carrinho.put(produto, 1);
                    }

                    System.out.println(produto.getNome() + " adicionado ao carrinho!!");
                    isPresent = true;

                    if (isPresent){
                        System.out.println("Você deseja adicionar outro produto ao carrinho?");
                        System.out.println("Digite  1 para sim, ou 0 para finalizar a compra. \n");

                        int opcao = Integer.parseInt(input.next());
                        if (opcao == 1) {
                            comprarProdutos();
                        }else {
                            finalizarCompra();
                        }
                    }
                }else{
                    System.out.println("Produto não encontrado!");
                    menu();
                }
            }
        }else{
            System.out.println("Não existem produtos cadastrados!");
            menu();
        }
    }

    private static void verCarrinho() {
        System.out.println("--------------- Produtos no Carrinho --------------");
        if (produtos.size() > 0) {
            for (Produto produto : carrinho.keySet()) {
                System.out.println("Produto: " + produto + "\nQuantidade: " + carrinho.get(produto));
            }
        }else {
            System.out.println("O carrinho está vazio! \n");
            menu();
        }
    }

    private static void finalizarCompra() {
        Double valorCompra = 0.0;
        System.out.println("--------------- Finalizar Compra --------------");
        for (Produto produto : carrinho.keySet()) {
            int qtd = carrinho.get(produto);
            valorCompra += produto.getPreco() * qtd;
            System.out.println("Produto: " + produto + "\nQuantidade: " + qtd);
        }

        System.out.println("Valor da Compra: " + Utils.doubleToString(valorCompra));
        carrinho.clear();
        System.out.println("--------------- Obrigada pela preferência --------------");
        menu();
    }
}