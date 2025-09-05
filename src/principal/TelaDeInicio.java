package principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TelaDeInicio extends JFrame {
    private JTextField txtNome;
    private JTextField txtCPF;
    private JCheckBox chkAdmin;
    private JButton btnEntrar;

    public TelaDeInicio() {
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

        btnEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText();
                String cpf = txtCPF.getText();
                boolean isAdmin = chkAdmin.isSelected();

                if (nome.isEmpty() || cpf.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!");
                    return;
                }

                if (isAdmin) {
                    JOptionPane.showMessageDialog(null, "Bem-vindo administrador: " + nome);
                    new TelaCadastroProdutos().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Bem-vindo cliente: " + nome);
                    new TelaCompra(nome, cpf).setVisible(true);
                    dispose();
                }
            }
        });

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
        SwingUtilities.invokeLater(() -> {
            new TelaDeInicio().setVisible(true);
        });
    }
}
