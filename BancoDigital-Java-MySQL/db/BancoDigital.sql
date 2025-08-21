-- Criação do schema para Banco Digital
CREATE DATABASE IF NOT EXISTS BancoDigital
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_0900_ai_ci;

USE BancoDigital;

CREATE TABLE IF NOT EXISTS Conta (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titular VARCHAR(100) NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 0
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS Transacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    conta_id INT NOT NULL,
    tipo ENUM('DEPOSITO','SAQUE','TRANSFERENCIA') NOT NULL,
    valor DECIMAL(15,2) NOT NULL,
    data TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao VARCHAR(255),
    conta_destino_id INT NULL,
    CONSTRAINT fk_trans_conta FOREIGN KEY (conta_id) REFERENCES Conta(id),
    CONSTRAINT fk_trans_conta_dest FOREIGN KEY (conta_destino_id) REFERENCES Conta(id)
) ENGINE=InnoDB;
