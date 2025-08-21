package model;

import java.time.LocalDateTime;

public class Transacao {
    private int id;
    private int contaId;
    private String tipo; // DEPOSITO, SAQUE, TRANSFERENCIA
    private double valor;
    private LocalDateTime data;
    private String descricao;
    private Integer contaDestinoId; // pode ser null

    public Transacao() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getContaId() { return contaId; }
    public void setContaId(int contaId) { this.contaId = contaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getContaDestinoId() { return contaDestinoId; }
    public void setContaDestinoId(Integer contaDestinoId) { this.contaDestinoId = contaDestinoId; }
}
