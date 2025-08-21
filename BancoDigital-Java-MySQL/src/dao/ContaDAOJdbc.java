package dao;

import model.Conta;

import java.sql.*;

public class ContaDAOJdbc implements ContaDAO {
    private final Connection conn;

    public ContaDAOJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Conta criarConta(String titular, double saldoInicial) throws SQLException {
        String sql = "INSERT INTO Conta (titular, saldo) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, titular);
            ps.setDouble(2, saldoInicial);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return new Conta(id, titular, saldoInicial);
                }
            }
        }
        throw new SQLException("Erro ao criar conta");
    }

    @Override
    public Conta buscarPorId(int id) throws SQLException {
        String sql = "SELECT id, titular, saldo FROM Conta WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Conta(
                            rs.getInt("id"),
                            rs.getString("titular"),
                            rs.getDouble("saldo")
                    );
                }
            }
        }
        return null; // se não encontrar
    }

    @Override
    public void atualizarSaldo(int id, double novoSaldo) throws SQLException {
        String sql = "UPDATE Conta SET saldo = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, novoSaldo);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}