package controller;

import dao.CompraDAO;
import model.Compra;
import model.ItemCompra;
import model.Produto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class CompraController {
    private ArrayList<Produto> carrinho;
    private String nomeUsuario;
    private String cpfUsuario;
    private CompraDAO compraDAO;

    private static final int LIMITE_ITENS_CARRINHO = 100;
    private static final double VALOR_MAXIMO_COMPRA = 50000.00;

    public CompraController(String nome, String cpf) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser vazio!");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF do usuário não pode ser vazio!");
        }

        this.nomeUsuario = nome.trim();
        this.cpfUsuario = cpf.trim();
        this.carrinho = new ArrayList<>();
        this.compraDAO = new CompraDAO();
    }

    public void adicionarProduto(Produto p) {
        // Validação do produto
        if (p == null) {
            JOptionPane.showMessageDialog(null, 
                "Produto inválido!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica limite de itens no carrinho
        if (carrinho.size() >= LIMITE_ITENS_CARRINHO) {
            JOptionPane.showMessageDialog(null, 
                String.format("Limite máximo de %d itens no carrinho atingido!", LIMITE_ITENS_CARRINHO), 
                "Limite Atingido", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verifica se adicionar o produto não ultrapassa o valor máximo
        double novoTotal = calcularTotal() + p.getPreco();
        if (novoTotal > VALOR_MAXIMO_COMPRA) {
            JOptionPane.showMessageDialog(null, 
                String.format("Adicionar este produto ultrapassaria o valor máximo de compra (R$ %.2f)!", VALOR_MAXIMO_COMPRA), 
                "Valor Máximo Excedido", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        carrinho.add(p);
    }

    public void removerProduto(Produto p) {
        if (p == null) {
            JOptionPane.showMessageDialog(null, 
                "Selecione um produto para remover!", 
                "Nenhum Produto Selecionado", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "O carrinho está vazio!", 
                "Carrinho Vazio", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean removido = carrinho.remove(p);
        if (!removido) {
            JOptionPane.showMessageDialog(null, 
                "Produto não encontrado no carrinho!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (Produto p : carrinho) {
            if (p != null && p.getPreco() > 0) {
                total += p.getPreco();
            }
        }
        return Math.round(total * 100.0) / 100.0; // Arredonda para 2 casas decimais
    }

    public String emitirNotaFiscal() {
        if (carrinho.isEmpty()) {
            return "Carrinho vazio! Adicione produtos antes de emitir a nota fiscal.";
        }

        StringBuilder nota = new StringBuilder();
        nota.append("═══════════════════════════════════════\n");
        nota.append("           NOTA FISCAL\n");
        nota.append("═══════════════════════════════════════\n\n");
        
        nota.append("Cliente: ").append(nomeUsuario).append("\n");
        nota.append("CPF: ").append(formatarCPF(cpfUsuario)).append("\n");
        nota.append("Data: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n\n");
        
        nota.append("───────────────────────────────────────\n");
        nota.append("PRODUTOS:\n");
        nota.append("───────────────────────────────────────\n");

        // Agrupa produtos por nome e conta quantidades
        Map<String, Integer> quantidades = new HashMap<>();
        Map<String, Double> precos = new HashMap<>();
        
        for (Produto p : carrinho) {
            String nome = p.getNome();
            quantidades.put(nome, quantidades.getOrDefault(nome, 0) + 1);
            precos.put(nome, p.getPreco());
        }

        double total = 0;
        for (Map.Entry<String, Integer> entry : quantidades.entrySet()) {
            String nome = entry.getKey();
            int qtd = entry.getValue();
            double precoUnit = precos.get(nome);
            double subtotal = precoUnit * qtd;
            
            nota.append(String.format("%-30s x%d\n", nome, qtd));
            nota.append(String.format("  R$ %.2f cada = R$ %.2f\n\n", precoUnit, subtotal));
            
            total += subtotal;
        }

        nota.append("───────────────────────────────────────\n");
        nota.append(String.format("TOTAL: R$ %.2f\n", total));
        nota.append("═══════════════════════════════════════\n");
        nota.append("\nObrigado pela preferência!\n");

        return nota.toString();
    }

    public void finalizarCompra() {
        // Validação do carrinho
        if (carrinho.isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "O carrinho está vazio! Adicione produtos antes de finalizar a compra.", 
                "Carrinho Vazio", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validação dos produtos no carrinho
        for (Produto p : carrinho) {
            if (p == null || p.getPreco() <= 0) {
                JOptionPane.showMessageDialog(null, 
                    "Existe um produto inválido no carrinho!", 
                    "Erro de Validação", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        double valorTotal = calcularTotal();

        // Confirmação da compra
        int opcao = JOptionPane.showConfirmDialog(null, 
            String.format("Deseja finalizar a compra?\n\nTotal de itens: %d\nValor total: R$ %.2f", 
                carrinho.size(), valorTotal), 
            "Confirmar Compra", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Agrupa produtos e calcula quantidades
            Map<Integer, ItemCompra> itensMap = new HashMap<>();
            
            for (Produto p : carrinho) {
                int idProduto = p.getIdProduto();
                if (itensMap.containsKey(idProduto)) {
                    ItemCompra item = itensMap.get(idProduto);
                    item.setQuantidade(item.getQuantidade() + 1);
                } else {
                    itensMap.put(idProduto, new ItemCompra(idProduto, 1, p.getPreco()));
                }
            }

            List<ItemCompra> itens = new ArrayList<>(itensMap.values());
            Compra compra = new Compra(nomeUsuario, cpfUsuario, LocalDateTime.now(), valorTotal, itens);

            // Salva a compra no banco
            compraDAO.salvarCompra(compra);

            // Exibe nota fiscal
            JOptionPane.showMessageDialog(null, 
                "✓ Compra finalizada com sucesso!\n\n" + emitirNotaFiscal(), 
                "Compra Finalizada", 
                JOptionPane.INFORMATION_MESSAGE);

            // Limpa carrinho após finalizar
            carrinho.clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao finalizar compra: " + ex.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public ArrayList<Produto> getCarrinho() {
        return new ArrayList<>(carrinho); // Retorna cópia para evitar modificações externas
    }

    public int getQuantidadeItens() {
        return carrinho.size();
    }

    /**
     * Formata CPF para exibição (xxx.xxx.xxx-xx)
     */
    private String formatarCPF(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return String.format("%s.%s.%s-%s", 
            cpf.substring(0, 3), 
            cpf.substring(3, 6), 
            cpf.substring(6, 9), 
            cpf.substring(9, 11));
    }
}