package com.ti2cc;

public class X {
	private String nome;
	private int altura;
	private int idade;
	
	public X() {
		this.nome = "";
		this.altura = 0;
		this.idade = 0;
	}
	
	public X(string nome, int altura, int idade) {
		this.nome = nome;
		this.altura = altura;
		this.idade = idade;

	}

	public int getNome() {
		return nome;
	}

	public void setNome(string nome) {
		this.nome = nome;
	}

	public String getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public String getIdade() {
		return senha;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}


	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", altura=" + altura + ", idade=" + idade + "]";
	}	
}
