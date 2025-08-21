package main;

import dao.ContaDAOJdbc;
import dao.TransacaoDAOJdbc;
import db.Conexao;
import model.Conta;
import model.Transacao;
import service.BancoService;


import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = Conexao.getConnection();
             Scanner sc = new Scanner(System.in)) {

            BancoService bancoService = new BancoService(
                    conn,
                    new ContaDAOJdbc(conn),
                    new TransacaoDAOJdbc(conn)
            );

            while (true) {
                System.out.println("\n=== Banco Digital ===");
                System.out.println("1 - Criar Conta");
                System.out.println("2 - Consultar Saldo");
                System.out.println("3 - Depositar");
                System.out.println("4 - Sacar");
                System.out.println("5 - Transferir");
                System.out.println("6 - Extrato");
                System.out.println("0 - Sair");
                System.out.print("Escolha: ");

                int opcao = sc.nextInt();
                sc.nextLine(); // limpar buffer

                try {
                    switch (opcao) {
                        case 1 -> {
                            System.out.print("Titular: ");
                            String titular = sc.nextLine();
                            System.out.print("Saldo inicial: ");
                            double saldoInicial = sc.nextDouble();
                            Conta conta = bancoService.criarConta(titular, saldoInicial);
                            System.out.println("Conta criada! ID: " + conta.getId());
                        }
                        case 2 -> {
                            System.out.print("ID da conta: ");
                            int contaId = sc.nextInt();
                            double saldo = bancoService.consultarSaldo(contaId);
                            System.out.println("Saldo da conta " + contaId + ": R$ " + saldo);
                        }
                        case 3 -> {
                            System.out.print("ID da conta: ");
                            int contaId = sc.nextInt();
                            System.out.print("Valor do depósito: ");
                            double valor = sc.nextDouble();
                            bancoService.depositar(contaId, valor);
                            System.out.println("Depósito realizado!");
                        }
                        case 4 -> {
                            System.out.print("ID da conta: ");
                            int contaId = sc.nextInt();
                            System.out.print("Valor do saque: ");
                            double valor = sc.nextDouble();
                            bancoService.sacar(contaId, valor);
                            System.out.println("Saque realizado!");
                        }
                        case 5 -> {
                            System.out.print("Conta de origem: ");
                            int origemId = sc.nextInt();
                            System.out.print("Conta de destino: ");
                            int destinoId = sc.nextInt();
                            System.out.print("Valor da transferência: ");
                            double valor = sc.nextDouble();
                            bancoService.transferir(origemId, destinoId, valor);
                            System.out.println("Transferência realizada!");
                        }
                        case 6 -> {
                            System.out.print("ID da conta: ");
                            int contaId = sc.nextInt();
                            System.out.print("Quantas últimas transações deseja ver? ");
                            int limite = sc.nextInt();
                            List<Transacao> extrato = bancoService.extrato(contaId, limite);
                            System.out.println("\n--- Extrato da conta " + contaId + " ---");
                            for (Transacao t : extrato) {
                                System.out.printf("[%s] %s - R$ %.2f (%s)\n",
                                        t.getData(), t.getTipo(), t.getValor(), t.getDescricao());
                            }
                        }
                        case 0 -> {
                            System.out.println("Saindo...");
                            return;
                        }
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.out.println("Falha ao conectar: " + e.getMessage());
        }
    }
}