package dao;
import model.Conta;
import java.sql.SQLException;

public interface ContaDAO {
    Conta criarConta(String titular, double saldoInicial) throws SQLException;
    Conta buscarPorId(int id) throws SQLException;
    void atualizarSaldo(int id, double novoSaldo) throws SQLException;
}