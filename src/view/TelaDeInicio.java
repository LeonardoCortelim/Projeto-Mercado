package view;

import javax.swing.*;
import java.awt.*;
import controller.InicioController;

public class TelaDeInicio extends JFrame {
    private JTextField txtNome;
    private JTextField txtCPF;
    private JCheckBox chkAdmin;
    private JButton btnEntrar;

    private InicioController controller;

    public TelaDeInicio() {
        controller = new InicioController(this);

        setTitle("Identificação do Usuário");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Container principal com padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel lblNome = new JLabel("Nome:");
        mainPanel.add(lblNome, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtNome = new JTextField(20);
        txtNome.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(txtNome, gbc);
        
        // CPF
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel lblCPF = new JLabel("CPF:");
        mainPanel.add(lblCPF, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        txtCPF = new JTextField(20);
        txtCPF.setPreferredSize(new Dimension(200, 30));
        mainPanel.add(txtCPF, gbc);
        
        // Checkbox Admin
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        chkAdmin = new JCheckBox("Sou um administrador");
        mainPanel.add(chkAdmin, gbc);
        
        // Botão Entrar
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        btnEntrar = new JButton("Entrar");
        btnEntrar.setPreferredSize(new Dimension(150, 40));
        btnEntrar.addActionListener(e -> 
            controller.login(txtNome.getText(), txtCPF.getText(), chkAdmin.isSelected())
        );
        mainPanel.add(btnEntrar, gbc);
        
        add(mainPanel);
        pack();
        setMinimumSize(new Dimension(400, 280));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaDeInicio().setVisible(true));
    }
}