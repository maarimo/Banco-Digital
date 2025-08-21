package service;

import dao.ContaDAO;
import dao.TransacaoDAO;
import model.Conta;
import model.Transacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BancoService {
    private final Connection conn;
    private final ContaDAO contaDAO;
    private final TransacaoDAO transacaoDAO;

    public BancoService(Connection conn, ContaDAO contaDAO, TransacaoDAO transacaoDAO) {
        this.conn = conn;
        this.contaDAO = contaDAO;
        this.transacaoDAO = transacaoDAO;
    }

    // Criar uma nova conta
    public Conta criarConta(String titular, double saldoInicial) throws SQLException {
        return contaDAO.criarConta(titular, saldoInicial);
    }

    // Consultar saldo
    public double consultarSaldo(int contaId) throws SQLException {
        Conta conta = contaDAO.buscarPorId(contaId);
        if (conta == null) {
            throw new SQLException("Conta não encontrada: " + contaId);
        }
        return conta.getSaldo();
    }

    // Depósito
    public void depositar(int contaId, double valor) throws SQLException {
        if (valor <= 0) throw new IllegalArgumentException("Valor inválido");

        Conta conta = contaDAO.buscarPorId(contaId);
        if (conta == null) throw new SQLException("Conta não encontrada");

        double novoSaldo = conta.getSaldo() + valor;
        contaDAO.atualizarSaldo(contaId, novoSaldo);
        transacaoDAO.registrar("DEPOSITO", contaId, valor, "Depósito em conta", null);
    }

    // Saque
    public void sacar(int contaId, double valor) throws SQLException {
        if (valor <= 0) throw new IllegalArgumentException("Valor inválido");

        Conta conta = contaDAO.buscarPorId(contaId);
        if (conta == null) throw new SQLException("Conta não encontrada");
        if (conta.getSaldo() < valor) throw new SQLException("Saldo insuficiente");

        double novoSaldo = conta.getSaldo() - valor;
        contaDAO.atualizarSaldo(contaId, novoSaldo);
        transacaoDAO.registrar("SAQUE", contaId, valor, "Saque em conta", null);
    }

    // Transferência entre contas
    public void transferir(int origemId, int destinoId, double valor) throws SQLException {
        if (valor <= 0) throw new IllegalArgumentException("Valor inválido");

        try {
            conn.setAutoCommit(false);

            Conta origem = contaDAO.buscarPorId(origemId);
            Conta destino = contaDAO.buscarPorId(destinoId);

            if (origem == null || destino == null) throw new SQLException("Conta inválida");
            if (origem.getSaldo() < valor) throw new SQLException("Saldo insuficiente");

            // Atualiza saldos
            contaDAO.atualizarSaldo(origemId, origem.getSaldo() - valor);
            contaDAO.atualizarSaldo(destinoId, destino.getSaldo() + valor);

            // Registra transações
            transacaoDAO.registrar("TRANSFERENCIA_ENVIO", origemId, valor, "Transferência para conta " + destinoId, destinoId);
            transacaoDAO.registrar("TRANSFERENCIA_RECEBIMENTO", destinoId, valor, "Transferência recebida da conta " + origemId, origemId);

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // Extrato
    public List<Transacao> extrato(int contaId, int limite) throws SQLException {
        return transacaoDAO.extrato(contaId, limite);
    }
}