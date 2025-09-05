package principal;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroProdutos extends JFrame {
    private JTextField txtNomeProduto;
    private JTextField txtPrecoProduto;
    private JButton btnCadastrar, btnRemover, btnLogout;
    private DefaultListModel<Produto> listaModel;
    private JList<Produto> listaProdutos;

    public TelaCadastroProdutos() {
        setTitle("Cadastro de Produtos (Administrador)");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        listaModel = new DefaultListModel<>();
        for (Produto p : Supermercado.getProdutos()) {
            listaModel.addElement(p);
        }
        listaProdutos = new JList<>(listaModel);

        // Painel de entrada
        JPanel painelEntrada = new JPanel(new GridLayout(2, 2, 10, 10));
        painelEntrada.add(new JLabel("Nome do Produto:"));
        txtNomeProduto = new JTextField();
        painelEntrada.add(txtNomeProduto);

        painelEntrada.add(new JLabel("Preço:"));
        txtPrecoProduto = new JTextField();
        painelEntrada.add(txtPrecoProduto);

        add(painelEntrada, BorderLayout.NORTH);

        // Botões
        JPanel painelBotoes = new JPanel(new GridLayout(1, 3, 10, 10));
        btnCadastrar = new JButton("Cadastrar");
        btnRemover = new JButton("Remover");
        btnLogout = new JButton("Logout");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLogout);

        add(painelBotoes, BorderLayout.SOUTH);

        // Lista de produtos
        add(new JScrollPane(listaProdutos), BorderLayout.CENTER);

        // Ações
        btnCadastrar.addActionListener(e -> cadastrarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso!");
            new TelaDeInicio().setVisible(true);
            dispose();
        });
    }

    private void cadastrarProduto() {
        String nome = txtNomeProduto.getText();
        String precoStr = txtPrecoProduto.getText();

        if (nome.isEmpty() || precoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            Produto p = new Produto(nome, preco);
            Supermercado.adicionarProduto(p);
            listaModel.addElement(p);

            JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
            txtNomeProduto.setText("");
            txtPrecoProduto.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido!");
        }
    }

    private void removerProduto() {
        Produto selecionado = listaProdutos.getSelectedValue();
        if (selecionado != null) {
            Supermercado.removerProduto(selecionado);
            listaModel.removeElement(selecionado);
            JOptionPane.showMessageDialog(this, "Produto removido com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto para remover!");
        }
    }
}
