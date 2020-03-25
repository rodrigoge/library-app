package br.com.library.models;

public enum TypeProduct {
	
	ADESIVO("Adesivo"), LIVROS("Livros"), PULSEIRAS("Pulseiras"),
	CAMISA("Camisa"), VELA("Vela"), TERÇO("Terço"), IMAGEM("Imagem"),
	BÍBLIA("Bíblia"), CORRENTE("Corrente");
	
	private String type;
	
	private TypeProduct(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
