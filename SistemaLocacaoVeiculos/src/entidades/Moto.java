
package entidades;

// Representa uma motocicleta, herdando de Veiculo.
public class Moto extends Veiculo {

    private Integer cilindradas;

    public Moto(String placa, String modelo, String cor,
                Double valorDiaria, Integer cilindradas) {

        super(placa, modelo, cor, valorDiaria);
        this.cilindradas = cilindradas;
    }

    public Integer getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(Integer cilindradas) {
        this.cilindradas = cilindradas;
    }

    @Override
    public Double calcularValor(Integer dias) {
        return getValorDiaria() * dias;
    }
}
