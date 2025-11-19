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
        // Validação de campos vazios
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "O campo Nome é obrigatório!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "O campo CPF é obrigatório!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação do nome
        nome = nome.trim();
        if (nome.length() < 3) {
            JOptionPane.showMessageDialog(view, 
                "O nome deve ter no mínimo 3 caracteres!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nome.length() > 100) {
            JOptionPane.showMessageDialog(view, 
                "O nome deve ter no máximo 100 caracteres!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!nome.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            JOptionPane.showMessageDialog(view, 
                "O nome deve conter apenas letras e espaços!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação do CPF
        cpf = cpf.trim().replaceAll("[^0-9]", ""); // Remove formatação

        if (cpf.length() != 11) {
            JOptionPane.showMessageDialog(view, 
                "O CPF deve conter exatamente 11 dígitos!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarCPF(cpf)) {
            JOptionPane.showMessageDialog(view, 
                "CPF inválido! Por favor, verifique o número digitado.", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Login bem-sucedido
        if (isAdmin) {
            JOptionPane.showMessageDialog(view, 
                "Bem-vindo, Administrador " + nome + "!", 
                "Login Realizado", 
                JOptionPane.INFORMATION_MESSAGE);
            new TelaCadastroProdutos().setVisible(true);
            view.dispose();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Bem-vindo, " + nome + "!", 
                "Login Realizado", 
                JOptionPane.INFORMATION_MESSAGE);
            new TelaCompra(nome, cpf).setVisible(true);
            view.dispose();
        }
    }

    
    private boolean validarCPF(String cpf) {
        // Verifica se todos os dígitos são iguais (CPF inválido)
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            // Calcula o primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int primeiroDigito = 11 - (soma % 11);
            if (primeiroDigito >= 10) primeiroDigito = 0;

            // Verifica o primeiro dígito
            if (Character.getNumericValue(cpf.charAt(9)) != primeiroDigito) {
                return false;
            }

            // Calcula o segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int segundoDigito = 11 - (soma % 11);
            if (segundoDigito >= 10) segundoDigito = 0;

            return Character.getNumericValue(cpf.charAt(10)) == segundoDigito;

        } catch (Exception e) {
            return false;
        }
    }
}