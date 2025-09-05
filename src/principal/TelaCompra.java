package principal;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaCompra extends JFrame {
    private JList<Produto> listaProdutos;
    private DefaultListModel<Produto> carrinhoModel;
    private JList<Produto> listaCarrinho;
    private JLabel lblTotal;
    private JButton btnAdicionar, btnRemover, btnNotaFiscal, btnLogout;
    private ArrayList<Produto> carrinho;
    private String nomeUsuario;
    private String cpfUsuario;

    public TelaCompra(String nome, String cpf) {
        this.nomeUsuario = nome;
        this.cpfUsuario = cpf;
        carrinho = new ArrayList<>();

        setTitle("Tela de Compra (Cliente)");
        setSize(650, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Lista de produtos disponíveis
        listaProdutos = new JList<>(Supermercado.getProdutos().toArray(new Produto[0]));

        // Carrinho
        carrinhoModel = new DefaultListModel<>();
        listaCarrinho = new JList<>(carrinhoModel);

        // Painel central
        JPanel painelCentral = new JPanel(new GridLayout(1, 2, 10, 10));
        painelCentral.add(new JScrollPane(listaProdutos));
        painelCentral.add(new JScrollPane(listaCarrinho));
        add(painelCentral, BorderLayout.CENTER);

        // Botões
        JPanel painelBotoes = new JPanel(new GridLayout(1, 4, 10, 10));
        btnAdicionar = new JButton("Adicionar →");
        btnRemover = new JButton("← Remover");
        btnNotaFiscal = new JButton("Emitir Nota Fiscal");
        btnLogout = new JButton("Logout");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnNotaFiscal);
        painelBotoes.add(btnLogout);

        add(painelBotoes, BorderLayout.SOUTH);

        // Total
        lblTotal = new JLabel("Total: R$ 0.0");
        add(lblTotal, BorderLayout.NORTH);

        // Ações
        btnAdicionar.addActionListener(e -> adicionarAoCarrinho());
        btnRemover.addActionListener(e -> removerDoCarrinho());
        btnNotaFiscal.addActionListener(e -> emitirNotaFiscal());
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso!");
            new TelaDeInicio().setVisible(true);
            dispose();
        });
    }

    private void adicionarAoCarrinho() {
        Produto selecionado = listaProdutos.getSelectedValue();
        if (selecionado != null) {
            carrinho.add(selecionado);
            carrinhoModel.addElement(selecionado);
            atualizarTotal();
            JOptionPane.showMessageDialog(this, "Produto adicionado ao carrinho!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para adicionar!");
        }
    }

    private void removerDoCarrinho() {
        Produto selecionado = listaCarrinho.getSelectedValue();
        if (selecionado != null) {
            carrinho.remove(selecionado);
            carrinhoModel.removeElement(selecionado);
            atualizarTotal();
            JOptionPane.showMessageDialog(this, "Produto removido do carrinho!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover!");
        }
    }

    private void atualizarTotal() {
        double total = 0;
        for (Produto p : carrinho) {
            total += p.getPreco();
        }
        lblTotal.setText("Total: R$ " + total);
    }

    private void emitirNotaFiscal() {
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carrinho vazio!");
            return;
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

        JOptionPane.showMessageDialog(this, nota.toString());
    }
}
