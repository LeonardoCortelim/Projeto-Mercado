package view;

import javax.swing.*;
import controller.ProdutoController;
import model.Produto;
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
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Container principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Inicializa componentes antes do controller
        txtNomeProduto = new JTextField();
        txtPrecoProduto = new JTextField();
        txtNomeProduto.setPreferredSize(new Dimension(200, 30));
        txtPrecoProduto.setPreferredSize(new Dimension(200, 30));

        listaModel = new DefaultListModel<>();
        listaProdutos = new JList<>(listaModel);
        listaProdutos.setFont(new Font("SansSerif", Font.PLAIN, 14));

        // Painel de entrada com GridBagLayout para responsividade
        JPanel painelEntrada = new JPanel(new GridBagLayout());
        painelEntrada.setBorder(BorderFactory.createTitledBorder("Dados do Produto"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        painelEntrada.add(new JLabel("Nome do Produto:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        painelEntrada.add(txtNomeProduto, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        painelEntrada.add(new JLabel("Preço:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        painelEntrada.add(txtPrecoProduto, gbc);
        
        mainPanel.add(painelEntrada, BorderLayout.NORTH);

        // Botões
        btnCadastrar = new JButton("Cadastrar");
        btnRemover = new JButton("Remover");
        btnLogout = new JButton("Logout");
        
        btnCadastrar.setPreferredSize(new Dimension(120, 35));
        btnRemover.setPreferredSize(new Dimension(120, 35));
        btnLogout.setPreferredSize(new Dimension(120, 35));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnLogout);
        mainPanel.add(painelBotoes, BorderLayout.SOUTH);

        // Lista de produtos com scroll
        JScrollPane scrollPane = new JScrollPane(listaProdutos);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Produtos Cadastrados"));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);

        // Agora sim, cria o controller
        controller = new ProdutoController(this);

        // Ações dos botões
        btnCadastrar.addActionListener(e -> controller.cadastrarProduto(txtNomeProduto.getText(), txtPrecoProduto.getText()));
        btnRemover.addActionListener(e -> controller.removerProduto(listaProdutos.getSelectedValue()));
        btnLogout.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Logout realizado com sucesso!");
            new TelaDeInicio().setVisible(true);
            dispose();
        });
        
        // Define tamanho e tamanho mínimo
        setSize(600, 500);
        setMinimumSize(new Dimension(450, 400));
    }

    // Atualiza lista de produtos na tela
    public void atualizarListaProdutos(List<Produto> produtos) {
        listaModel.clear();
        for (Produto p : produtos) {
            listaModel.addElement(p);
        }
        txtNomeProduto.setText("");
        txtPrecoProduto.setText("");
    }
}