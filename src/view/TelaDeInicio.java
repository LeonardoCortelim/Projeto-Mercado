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
        getContentPane().setLayout(new GridLayout(4, 2, 10, 10));

        JLabel lblNome = new JLabel("Nome:");
        txtNome = new JTextField();

        JLabel lblCPF = new JLabel("CPF:");
        txtCPF = new JTextField();

        chkAdmin = new JCheckBox("Sou administrador");
        btnEntrar = new JButton("Entrar");

       
        btnEntrar.addActionListener(e -> 
            controller.login(txtNome.getText(), txtCPF.getText(), chkAdmin.isSelected())
        );

        getContentPane().add(lblNome);
        getContentPane().add(txtNome);
        getContentPane().add(lblCPF);
        getContentPane().add(txtCPF);
        getContentPane().add(new JLabel());
        getContentPane().add(chkAdmin);
        getContentPane().add(new JLabel());
        getContentPane().add(btnEntrar);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaDeInicio().setVisible(true));
    }
}
