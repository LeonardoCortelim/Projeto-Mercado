package controller;

import dao.ProdutoDAO;
import model.Produto;
import view.TelaCadastroProdutos;

import javax.swing.*;
import java.util.List;

public class ProdutoController {
    private TelaCadastroProdutos view;
    private ProdutoDAO produtoDAO;

    public ProdutoController(TelaCadastroProdutos view) {
        this.view = view;
        this.produtoDAO = new ProdutoDAO();
        atualizarLista(); // Preenche a lista ao iniciar
    }

    public void cadastrarProduto(String nome, String precoStr) {
        // Validação de campos vazios
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "O campo Nome do Produto é obrigatório!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (precoStr == null || precoStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "O campo Preço é obrigatório!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação do nome do produto
        nome = nome.trim();
        if (nome.length() < 3) {
            JOptionPane.showMessageDialog(view, 
                "O nome do produto deve ter no mínimo 3 caracteres!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (nome.length() > 100) {
            JOptionPane.showMessageDialog(view, 
                "O nome do produto deve ter no máximo 100 caracteres!", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação do preço
        precoStr = precoStr.trim().replace(",", ".");
        
        try {
            double preco = Double.parseDouble(precoStr);

            // Verifica se o preço é positivo
            if (preco <= 0) {
                JOptionPane.showMessageDialog(view, 
                    "O preço deve ser maior que zero!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verifica se o preço não é absurdamente alto
            if (preco > 1000000) {
                JOptionPane.showMessageDialog(view, 
                    "O preço não pode ser superior a R$ 1.000.000,00!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Arredonda para 2 casas decimais
            preco = Math.round(preco * 100.0) / 100.0;

            // Verifica se o produto já existe
            if (produtoJaExiste(nome)) {
                int opcao = JOptionPane.showConfirmDialog(view, 
                    "Já existe um produto com este nome. Deseja cadastrar mesmo assim?", 
                    "Produto Duplicado", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
                
                if (opcao != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Cadastra o produto
            Produto p = new Produto(nome, preco);
            produtoDAO.salvarProduto(p);
            atualizarLista();
            
            JOptionPane.showMessageDialog(view, 
                String.format("Produto '%s' cadastrado com sucesso!\nPreço: R$ %.2f", nome, preco), 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, 
                "Preço inválido! Use apenas números e ponto ou vírgula como separador decimal.\nExemplo: 19.90 ou 19,90", 
                "Erro de Validação", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao cadastrar produto: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void removerProduto(Produto selecionado) {
        if (selecionado == null) {
            JOptionPane.showMessageDialog(view, 
                "Selecione um produto na lista para remover!", 
                "Nenhum Produto Selecionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirmação de exclusão
        int opcao = JOptionPane.showConfirmDialog(view, 
            String.format("Deseja realmente remover o produto:\n\n%s\nPreço: R$ %.2f", 
                selecionado.getNome(), selecionado.getPreco()), 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (opcao == JOptionPane.YES_OPTION) {
            try {
                produtoDAO.removerProduto(selecionado.getIdProduto());
                atualizarLista();
                
                JOptionPane.showMessageDialog(view, 
                    "Produto removido com sucesso!", 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, 
                    "Erro ao remover produto: " + ex.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    public void atualizarLista() {
        try {
            List<Produto> produtos = produtoDAO.listarProdutos();
            view.atualizarListaProdutos(produtos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, 
                "Erro ao carregar lista de produtos: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Verifica se já existe um produto com o mesmo nome
     */
    private boolean produtoJaExiste(String nome) {
        List<Produto> produtos = produtoDAO.listarProdutos();
        return produtos.stream()
                .anyMatch(p -> p.getNome().equalsIgnoreCase(nome.trim()));
    }
}