package model;

import java.util.ArrayList;

public class Supermercado {
    private static ArrayList<Produto> produtos = new ArrayList<>();

    public static void adicionarProduto(Produto p) {
        produtos.add(p);
    }

    public static void removerProduto(Produto p) {
        produtos.remove(p);
    }

    public static ArrayList<Produto> getProdutos() {
        return produtos;
    }
}
