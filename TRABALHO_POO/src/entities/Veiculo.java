package entities;

public abstract class  Veiculo {
	private String placa;
	private String modelo;
	private String cor;
	private Double valorDiaria;

	public Veiculo (String placa, String modelo, String cor, Double valorDiaria){
		this.placa = placa;
		this.modelo = modelo;
		this.cor = cor;
		this.valorDiaria = valorDiaria;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}


	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public Double getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(Double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}
	
	public Double calcularValor(Integer dias) {
		return valorDiaria * dias;
	}
}