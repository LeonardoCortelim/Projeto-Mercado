package controller;

import dao.ProdutoDAO;
import model.Produto;
import view.TelaCadastroProdutos;

import javax.swing.*;
import java.util.List;

public class ProdutoController {
    private TelaCadastroProdutos view;
    private ProdutoDAO produtoDAO;

    public ProdutoController(TelaCadastroProdutos view) {
        this.view = view;
        this.produtoDAO = new ProdutoDAO();
        atualizarLista(); // Preenche a lista ao iniciar
    }

    public void cadastrarProduto(String nome, String precoStr) {
        if (nome.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }
        try {
            double preco = Double.parseDouble(precoStr);
            Produto p = new Produto(nome, preco);
            produtoDAO.salvarProduto(p);
            atualizarLista();
            JOptionPane.showMessageDialog(view, "Produto cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Preço inválido!");
        }
    }

    public void removerProduto(Produto selecionado) {
        if (selecionado != null) {
            produtoDAO.removerProduto(selecionado.getIdProduto());
            atualizarLista();
            JOptionPane.showMessageDialog(view, "Produto removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(view, "Selecione um produto para remover!");
        }
    }

    public void atualizarLista() {
        List<Produto> produtos = produtoDAO.listarProdutos();
        view.atualizarListaProdutos(produtos);
    }
}
