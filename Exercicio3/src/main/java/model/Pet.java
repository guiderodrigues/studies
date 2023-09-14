package model;

public class Pet {
	private int ID;
	private String nome;
	private int idade;
	private int peso;
	
	public Pet() {
		this.ID = -1;
		this.nome = "";
		this.idade = 0;
		this.peso = 0;
	}
	
	public Pet(int ID, String nome, int idade, int peso) {
		this.ID = ID;
		this.nome = nome;
		this.idade = idade;
		this.peso = peso;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "PET [ID=" + ID + ", nome=" + nome + ", idade=" + idade + ", peso=" + peso + "]";
	}	
}