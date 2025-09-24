package controller;

import javax.swing.*;
import view.TelaCadastroProdutos;
import view.TelaCompra;
import view.TelaDeInicio;

public class InicioController {
    private TelaDeInicio view;

    public InicioController(TelaDeInicio view) {
        this.view = view;
    }

    public void login(String nome, String cpf, boolean isAdmin) {
        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Preencha todos os campos!");
            return;
        }

        if (isAdmin) {
            JOptionPane.showMessageDialog(view, "Bem-vindo administrador: " + nome);
            new TelaCadastroProdutos().setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, "Bem-vindo cliente: " + nome);
            new TelaCompra(nome, cpf).setVisible(true);
            view.dispose();
        }
    }
}
