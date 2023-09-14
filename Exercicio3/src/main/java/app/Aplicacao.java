package app;

import static spark.Spark.*;
import service.PetService;


public class Aplicacao {
	
	private static PetService petService = new PetService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/pet/insert", (request, response) -> petService.insert(request, response));

        get("/pet/:id", (request, response) -> petService.get(request, response));
        
        get("/pet/list/:orderby", (request, response) -> petService.getAll(request, response));

        get("/pet/update/:id", (request, response) -> petService.getToUpdate(request, response));
        
        post("/pet/update/:id", (request, response) -> petService.update(request, response));
           
        get("/pet/delete/:id", (request, response) -> petService.delete(request, response));

             
    }
}