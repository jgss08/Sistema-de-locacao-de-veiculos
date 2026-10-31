package entities;

public class Caminhao extends Veiculo{
	private Double capacidadeKg;

	public Caminhao(String placa, String modelo, String cor, Double valorDiaria, Double capacidadeKg) {
		super(placa, modelo, cor, valorDiaria);
		this.capacidadeKg = capacidadeKg;
	}

	public Double getCapacidadeKg() {
		return capacidadeKg;
	}

	public void setCapacidadeKg(Double capacidadeKg) {
		this.capacidadeKg = capacidadeKg;
	}
	
	@Override
	public Double calcularValor(Integer dias) {
	    return getValorDiaria() * dias;
	}
	
}
