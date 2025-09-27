package controller;

import dao.CompraDAO;
import model.Compra;
import model.ItemCompra;
import model.Produto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CompraController {
    private ArrayList<Produto> carrinho;
    private String nomeUsuario;
    private String cpfUsuario;

    private CompraDAO compraDAO;

    public CompraController(String nome, String cpf) {
        this.nomeUsuario = nome;
        this.cpfUsuario = cpf;
        this.carrinho = new ArrayList<>();
        this.compraDAO = new CompraDAO();
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

    public void finalizarCompra() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Carrinho vazio!");
            return;
        }

        double valorTotal = calcularTotal();

        List<ItemCompra> itens = new ArrayList<>();
        for (Produto p : carrinho) {
            itens.add(new ItemCompra(p.getIdProduto(), 1, p.getPreco())); // quantidade=1 por produto
        }

        Compra compra = new Compra(nomeUsuario, cpfUsuario, LocalDateTime.now(), valorTotal, itens);

        compraDAO.salvarCompra(compra);

        JOptionPane.showMessageDialog(null, "Compra finalizada com sucesso!\n" + emitirNotaFiscal());

        carrinho.clear(); // Limpa carrinho após finalizar
    }
}
