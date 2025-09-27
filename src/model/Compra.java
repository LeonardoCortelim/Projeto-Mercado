package model;

import java.time.LocalDateTime;
import java.util.List;

public class Compra {
    private String nomeCliente;
    private String cpfCliente;
    private LocalDateTime dataCompra;
    private double valorTotal;
    private List<ItemCompra> itens;

    public Compra(String nomeCliente, String cpfCliente, LocalDateTime dataCompra, double valorTotal, List<ItemCompra> itens) {
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
        this.dataCompra = dataCompra;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public LocalDateTime getDataCompra() {
        return dataCompra;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public List<ItemCompra> getItens() {
        return itens;
    }
}
