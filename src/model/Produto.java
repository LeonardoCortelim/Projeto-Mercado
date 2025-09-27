package model;

public class Produto {
    private int idProduto; // chave primária no banco
    private String nome;
    private double preco;

    // Construtor completo (usado quando já sei o ID do banco)
    public Produto(int idProduto, String nome, double preco) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.preco = preco;
    }

    // Construtor sem ID (usado antes de salvar no banco)
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return idProduto + " - " + nome + " - R$ " + preco;
    }
}
