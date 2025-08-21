package dao;

import model.Transacao;

import java.sql.SQLException;
import java.util.List;

public interface TransacaoDAO {
    void registrar(String tipo, int contaId, double valor, String descricao, Integer contaDestinoId) throws SQLException;
    List<Transacao> extrato(int contaId, int limite) throws SQLException;
}
