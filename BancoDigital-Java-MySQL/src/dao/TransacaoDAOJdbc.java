package dao;

import model.Transacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAOJdbc implements TransacaoDAO {
    private final Connection conn;

    public TransacaoDAOJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void registrar(String tipo, int contaId, double valor, String descricao, Integer contaDestinoId) throws SQLException {
        String sql = "INSERT INTO Transacao (conta_id, tipo, valor, descricao, conta_destino_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contaId);
            ps.setString(2, tipo);
            ps.setDouble(3, valor);
            ps.setString(4, descricao);
            if (contaDestinoId == null) ps.setNull(5, Types.INTEGER);
            else ps.setInt(5, contaDestinoId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Transacao> extrato(int contaId, int limite) throws SQLException {
        String sql = "SELECT id, conta_id, tipo, valor, data, descricao, conta_destino_id " +
                "FROM Transacao WHERE conta_id = ? ORDER BY data DESC, id DESC LIMIT ?";
        List<Transacao> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contaId);
            ps.setInt(2, limite);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Transacao t = new Transacao();
                    t.setId(rs.getInt("id"));
                    t.setContaId(rs.getInt("conta_id"));
                    t.setTipo(rs.getString("tipo"));
                    t.setValor(rs.getDouble("valor"));
                    Timestamp ts = rs.getTimestamp("data");
                    if (ts != null) t.setData(ts.toLocalDateTime());
                    t.setDescricao(rs.getString("descricao"));
                    int dest = rs.getInt("conta_destino_id");
                    if (!rs.wasNull()) t.setContaDestinoId(dest);
                    lista.add(t);
                }
            }
        }
        return lista;
    }
}