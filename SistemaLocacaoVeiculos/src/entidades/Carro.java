
package entidades;

// Representa um carro, herdando de Veiculo.
public class Carro extends Veiculo {

    private Integer portas;

    public Carro(String placa, String modelo, String cor,
                 Double valorDiaria, Integer portas) {

        super(placa, modelo, cor, valorDiaria);
        this.portas = portas;
    }

    public Integer getPortas() {
        return portas;
    }

    public void setPortas(Integer portas) {
        this.portas = portas;
    }

    @Override
    public Double calcularValor(Integer dias) {
        return getValorDiaria() * dias;
    }
}
