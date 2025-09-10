package controller;

import model.Produto;
import java.util.List;

public class ProdutoController {

    // Verifica se os campos nome e preço não estão vazios
    public boolean validarCampos(String nome, String preco) {
        return !nome.isEmpty() && !preco.isEmpty();
    }

    // Cria um objeto Produto a partir do nome e do preço em String
    public Produto criarProduto(String nome, String preco) throws NumberFormatException {
        double precoDouble = Double.parseDouble(preco); // converte String para double
        return new Produto(nome, precoDouble); // cria novo Produto com nome e preço
    }

    // Adiciona o produto à lista de produtos (model)
    public void adicionarProduto(Produto produto) {
        Supermercado.adicionarProduto(produto); // chama a classe que mantém a lista de produtos
    }

    // Remove um produto da lista
    public void removerProduto(Produto produto) {
        Supermercado.removerProduto(produto);
    }

    // Retorna a lista atual de produtos
    public List<Produto> listarProdutos() {
        return Supermercado.getProdutos();
    }
}
