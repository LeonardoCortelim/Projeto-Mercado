package controller;

import model.Produto;
import java.util.ArrayList;

public class CompraController {
    private ArrayList<Produto> carrinho;
    private String nomeUsuario;
    private String cpfUsuario;

    public CompraController(String nome, String cpf) {
        this.nomeUsuario = nome;
        this.cpfUsuario = cpf;
        this.carrinho = new ArrayList<>();
    }

    public void adicionarProduto(Produto p) {
        carrinho.add(p);
    }

    public void removerProduto(Produto p) {
        carrinho.remove(p);
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : carrinho) {
            total += p.getPreco();
        }
        return total;
    }

    public String emitirNotaFiscal() {
        if (carrinho.isEmpty()) {
            return "Carrinho vazio!";
        }

        StringBuilder nota = new StringBuilder();
        nota.append("Nota Fiscal\n");
        nota.append("Cliente: ").append(nomeUsuario).append("\n");
        nota.append("CPF: ").append(cpfUsuario).append("\n\n");
        nota.append("Produtos:\n");

        double total = 0;
        for (Produto p : carrinho) {
            nota.append("- ").append(p.getNome()).append(" (R$ ").append(p.getPreco()).append(")\n");
            total += p.getPreco();
        }

        nota.append("\nTotal: R$ ").append(total);

        return nota.toString();
    }

    public ArrayList<Produto> getCarrinho() {
        return carrinho;
    }
}
