package model;

public class Conta {
    private int id;
    private String titular;
    private double saldo;

    public Conta() {}

    public Conta(int id, String titular, double saldo) {
        this.id = id;
        this.titular = titular;
        this.saldo = saldo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    @Override
    public String toString() {
        return "Conta{id=" + id + ", titular='" + titular + "', saldo=" + saldo + "}";
    }
}
