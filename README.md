# Banco Digital — Java + MySQL

Projeto de console simples com camadas model/dao/service e integração com MySQL usando DAO (interfaces + implementações JDBC).

## Funcionalidades
- Criar conta
- Depósito
- Saque
- Transferência (com **transação** no banco — commit/rollback)
- Saldo e extrato (últimas 20)

## Requisitos
- Java 17+ (pode usar 11+ também)
- MySQL 8+
- **MySQL Connector/J** 

## Banco de Dados
1. Execute o script em `db/BancoDigital.sql` no MySQL.
2. Ajuste as credenciais em `src/db/Conexao.java` (URL, usuário e senha).

## Estrutura
```
BancoDigital-Java-MySQL/
  db/BancoDigital.sql
  lib/                          # mysql-connector-j-<versão>.jar
  src/
    db/Conexao.java              # classe utilitária de conexão
    model/Conta.java
    model/Transacao.java
    dao/
      ContaDAO.java              # interface
      ContaDAOJdbc.java          # implementação JDBC
      TransacaoDAO.java          # interface
      TransacaoDAOJdbc.java      # implementação JDBC
    service/
      BancoService.java          # regra de negócio
    main/Main.java               # aplicação console
  README.md
```
