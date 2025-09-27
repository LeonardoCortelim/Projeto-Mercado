package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import controller.CompraController;
import dao.ProdutoDAO;
import model.Produto;

public class TelaCompra extends JFrame {
    private JList<Produto> listaProdutos;
    private DefaultListModel<Produto> carrinhoModel;
    private JList<Produto> listaCarrinho;
    private JLabel lblTotal;
    private JButton btnAdicionar, btnRemover, btnNotaFiscal, btnLogout;
    private CompraController controller;

    public TelaCompra(String nome, String cpf) {
        controller = new CompraController(nome, cpf);

        setTitle("Tela de Compra (Cliente)");
        setSize(650, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Buscar produtos diretamente do banco
        ProdutoDAO produtoDAO = new ProdutoDAO();
        List<Produto> produtosDisponiveis = produtoDAO.listarProdutos();
        listaProdutos = new JList<>(produtosDisponiveis.toArray(new Produto[0]));

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

        // Ações → agora chamam o controller
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
            controller.adicionarProduto(selecionado);
            carrinhoModel.addElement(selecionado);
            atualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para adicionar!");
        }
    }

    private void removerDoCarrinho() {
        Produto selecionado = listaCarrinho.getSelectedValue();
        if (selecionado != null) {
            controller.removerProduto(selecionado);
            carrinhoModel.removeElement(selecionado);
            atualizarTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover!");
        }
    }

    private void atualizarTotal() {
        double total = controller.calcularTotal();
        lblTotal.setText("Total: R$ " + total);
    }

    private void emitirNotaFiscal() {
        String nota = controller.emitirNotaFiscal();
        JOptionPane.showMessageDialog(this, nota);
    }
}
