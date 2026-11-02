
package entidades;

// Representa o aluguel de um veículo por um cliente.
public class Locacao {

    private Cliente cliente;
    private Veiculo veiculo;
    private Integer dias;

    public Locacao(Cliente cliente, Veiculo veiculo, Integer dias) {
        this.cliente = cliente;
        this.veiculo = veiculo;
        this.dias = dias;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Integer getDias() {
        return dias;
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    // Calcula o total e aplica 10% de desconto para 7 dias ou mais.
    public double calcularTotal() {

        double valor = veiculo.calcularValor(dias);

        if (dias >= 7) {
            valor = valor * 0.90;
        }

        return valor;
    }
}
