package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.PetDAO;
import model.Pet;
import spark.Request;
import spark.Response;


public class PetService {

	private PetDAO petDAO = new PetDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_IDADE = 3;
	
	
	public PetService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Pet(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Pet(), orderBy);
	}

	
	public void makeForm(int tipo, Pet pet, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umPet = "";
		if(tipo != FORM_INSERT) {
			umPet += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/pet/list/1\">Novo Pet</a></b></font></td>";
			umPet += "\t\t</tr>";
			umPet += "\t</table>";
			umPet += "\t<br>";
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/pet/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Pet";
				nome = "digite seu nome";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + pet.getID();
				name = "Atualizar Pet (ID " + pet.getID() + ")";
				nome = pet.getNome();
				buttonLabel = "Atualizar";
			}
			umPet += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPet += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umPet += "\t\t</tr>";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPet += "\t\t</tr>";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umPet += "\t\t\t<td>Idade: <input class=\"input--register\" type=\"number\" name=\"idade\" value=\""+ pet.getIdade() +"\"></td>";
			umPet += "\t\t\t<td>Peso: <input class=\"input--register\" type=\"number\" name=\"peso\" value=\""+ pet.getPeso() +"\"></td>";
			umPet += "\t\t</tr>";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umPet += "\t\t</tr>";
			umPet += "\t</table>";
			umPet += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umPet += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Pet (ID " + pet.getID() + ")</b></font></td>";
			umPet += "\t\t</tr>";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPet += "\t\t</tr>";
			umPet += "\t\t<tr>";
			umPet += "\t\t\t<td>&nbsp;Nome: "+ pet.getNome() +"</td>";
			umPet += "\t\t\t<td>Idade: "+ pet.getIdade() +"</td>";
			umPet += "\t\t\t<td>Peso: "+ pet.getPeso() +"</td>";
			umPet += "\t\t</tr>";
			umPet += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Pet>", umPet);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Pets</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/pet/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/pet/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/pet/list/" + FORM_ORDERBY_IDADE + "\"><b>Idade</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Pet> pets;
		if (orderBy == FORM_ORDERBY_ID) {                 	pets = petDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		pets = petDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_IDADE) {			pets = petDAO.getOrderByIdade();
		} else {											pets = petDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Pet p : pets) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getIdade() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pet/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/pet/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePet('" + p.getID() + "', '" + p.getNome() + "', '" + p.getIdade() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-Pet>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		int idade = Integer.parseInt(request.queryParams("idade"));
		int peso = Integer.parseInt(request.queryParams("peso"));
		
		String resp = "";
		
		Pet pet = new Pet(-1, nome, idade, peso);
		
		if(petDAO.inserirElemento(pet) == true) {
            resp = "Pet (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Pet (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pet pet = (Pet) petDAO.getElemento(id);
		
		if (pet != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, pet, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pet " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Pet pet = (Pet) petDAO.getElemento(id);
		
		if (pet != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, pet, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Pet " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Pet pet = petDAO.getElemento(id);
        String resp = "";       

        if (pet != null) {
        	pet.setNome(request.queryParams("nome"));
        	pet.setIdade(Integer.parseInt(request.queryParams("idade")));
        	pet.setPeso(Integer.parseInt(request.queryParams("peso")));
        	petDAO.atualizarElemento(pet);
        	response.status(200); // success
            resp = "Pet (ID " + pet.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pet (ID \" + pet.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Pet pet = petDAO.getElemento(id);
        String resp = "";       

        if (pet != null) {
            petDAO.excluirElemento(id);
            response.status(200); // success
            resp = "Pet (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Pet (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}