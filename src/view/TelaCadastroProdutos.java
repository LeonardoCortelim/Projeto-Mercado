// view/TelaCadastroProdutos.java
package view;

import javax.swing.*;
import controller.ProdutoController;
import model.Produto;
import model.Supermercado;
import java.awt.*;
import java.util.List;

public class TelaCadastroProdutos extends JFrame {
    private JTextField txtNomeProduto;
    private JTextField txtPrecoProduto;
    private JButton btnCadastrar, btnRemover, btnLogout;
    private DefaultListModel<Produto> listaModel;
    private JList<Produto> listaProdutos;

    private ProdutoController controller;

    public TelaCadastroProdutos() {
        setTitle("Cadastro de Produtos (Administrador)");
        setSize(500, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        controller = new ProdutoController(this);

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
        btnCadastrar.addActionListener(e -> controller.cadastrarProduto(txtNomeProduto.getText(), txtPrecoProduto.getText()));
        btnRemover.addActionListener(e -> controller.removerProduto(listaProdutos.getSelectedValue()));
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso!");
            new TelaDeInicio().setVisible(true);
            dispose();
        });
    }

    // Método para atualizar lista na tela
    public void atualizarListaProdutos(List<Produto> produtos) {
        listaModel.clear();
        for (Produto p : produtos) {
            listaModel.addElement(p);
        }
        txtNomeProduto.setText("");
        txtPrecoProduto.setText("");
    }
}
