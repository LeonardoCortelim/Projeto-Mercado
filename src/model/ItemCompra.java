package model;

public class ItemCompra {
    private int idProduto;
    private int quantidade;
    private double precoUnitario;

    public ItemCompra(int idProduto, int quantidade, double precoUnitario) {
        this.idProduto = idProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }
}
