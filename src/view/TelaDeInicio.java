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
        setSize(350, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();

        JLabel lblCPF = new JLabel("CPF:");
        txtCPF = new JTextField();

        chkAdmin = new JCheckBox("Sou administrador do sistema");
        btnEntrar = new JButton("Entrar");

       
        btnEntrar.addActionListener(e -> 
            controller.login(txtNome.getText(), txtCPF.getText(), chkAdmin.isSelected())
        );

        add(lblNome);
        add(txtNome);
        add(lblCPF);
        add(txtCPF);
        add(new JLabel());
        add(chkAdmin);
        add(new JLabel());
        add(btnEntrar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaDeInicio().setVisible(true));
    }
}
