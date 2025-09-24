package controller;

import model.Produto;
import model.Supermercado;
import view.TelaCadastroProdutos;

import javax.swing.*;
import java.util.List;

public class ProdutoController {
    private TelaCadastroProdutos view;

    // 🔹 Construtor que recebe a view
    public ProdutoController(TelaCadastroProdutos view) {
        this.view = view;
    }

    public void cadastrarProduto(String nome, String precoStr) {
        if (nome.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            Produto p = new Produto(nome, preco);
            Supermercado.adicionarProduto(p);

            // Atualiza a lista da View
            view.atualizarListaProdutos(Supermercado.getProdutos());
            JOptionPane.showMessageDialog(view, "Produto cadastrado com sucesso!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Preço inválido!");
        }
    }

    public void removerProduto(Produto selecionado) {
        if (selecionado != null) {
            Supermercado.removerProduto(selecionado);
            view.atualizarListaProdutos(Supermercado.getProdutos());
            JOptionPane.showMessageDialog(view, "Produto removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(view, "Selecione um produto para remover!");
        }
    }
}
