package dao;

import model.ConexaoDB;
import model.Compra;
import model.ItemCompra;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class CompraDAO {

    public void salvarCompra(Compra compra) {
        String sqlCompra = "INSERT INTO Compra (nome_cliente, cpf_cliente, data_compra, valor_total) VALUES (?, ?, ?, ?)";
        String sqlItem = "INSERT INTO ItemCompra (id_compra, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConnection()) {
            conn.setAutoCommit(false);

            // Salvar Compra
            try (PreparedStatement stmtCompra = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS)) {
                stmtCompra.setString(1, compra.getNomeCliente());
                stmtCompra.setString(2, compra.getCpfCliente());
                stmtCompra.setTimestamp(3, Timestamp.valueOf(compra.getDataCompra()));
                stmtCompra.setDouble(4, compra.getValorTotal());
                stmtCompra.executeUpdate();

                ResultSet rs = stmtCompra.getGeneratedKeys();
                if (rs.next()) {
                    int idCompra = rs.getInt(1);

                    // Salvar Itens da Compra
                    try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                        for (ItemCompra item : compra.getItens()) {
                            stmtItem.setInt(1, idCompra);
                            stmtItem.setInt(2, item.getIdProduto());
                            stmtItem.setInt(3, item.getQuantidade());
                            stmtItem.setDouble(4, item.getPrecoUnitario());
                            stmtItem.addBatch();
                        }
                        stmtItem.executeBatch();
                    }
                }
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
