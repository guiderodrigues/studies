package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Pet;

public class PetDAO {
	private Connection conexao;

	public PetDAO() {
		conexao = null;
	}

	public boolean conectar() {
		String driverName = "org.postgresql.Driver";                    
		String serverName = "localhost";
		String mydatabase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
		String username = "postgres";
		String password = "guilherme123";
		boolean status = false;

		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efetuada com o postgres!");
		} catch (ClassNotFoundException e) { 
			System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
		}

		return status;
	}

	public boolean close() {
		boolean status = false;
		try {
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return status;
	}

	public boolean inserirElemento(Pet elemento) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO PET (ID, nome, idade, peso) " +
					"VALUES (" + elemento.getID() + ", '" + elemento.getNome() + "', " +
					elemento.getIdade() + ", " + elemento.getPeso() + ");");
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean atualizarElemento(Pet elemento) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE PET SET nome = '" + elemento.getNome() + "', idade = " +
					elemento.getIdade() + ", peso = " + elemento.getPeso() +
					" WHERE ID = " + elemento.getID();
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public boolean excluirElemento(int ID) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM PET WHERE ID = " + ID);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public Pet[] getElementos() {
		Pet[] elementos = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM PET");
			if (rs.next()) {
				rs.last();
				elementos = new Pet[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					elementos[i] = new Pet(rs.getInt("ID"), rs.getString("nome"),
							rs.getInt("idade"), rs.getInt("peso"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return elementos;
	}
	public Pet getElemento(int ID) {
		Pet elemento = null;
		try{
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM PET WHERE ID = " + ID);
			if(rs.next()){
				elemento = new Pet(rs.getInt("ID"), rs.getString("nome"), rs.getInt("idade"), rs.getInt("peso"));
			}
		}catch(Exception e){
			System.err.println(e.getMessage());
		}
		return elemento;
	}
	private List<Pet> get(String orderBy) {
		List<Pet> pets = new ArrayList<Pet>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM pet" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Pet p = new Pet(rs.getInt("id"), rs.getString("nome"), rs.getInt("idade"), rs.getInt("peso"));
	            pets.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return pets;
	}

	public List<Pet> get() {
		return get("");
	}

	
	public List<Pet> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Pet> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<Pet> getOrderByIdade() {
		return get("idade");		
	}
}