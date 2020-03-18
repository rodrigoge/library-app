package br.com.library.models;

public enum TypeUser {
	ADMINISTRADOR("Administrador"), FUNCIONÁRIO("Funcionário"), CLIENTE("Cliente");
	
	private String type;
	
	private TypeUser(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
}
