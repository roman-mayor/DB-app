package by.roman_mayorov.servlets;

import by.roman_mayorov.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/clients_id")
public class IdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        ClientService clientService = ClientService.getINSTANCE();

        try(var pw = resp.getWriter()){
        pw.write("<html><body>");
        pw.write("<h1>Список id клиентов:</h1");
        pw.write("<ul>");
        clientService.findAll()
                .forEach(c -> pw.write("""
                                          <li> 
                                          <a href ='/clients_description?clientId=%d'> %d </a>
                                          </li>
                                          """.formatted(c.getId(), c.getId())));
    }catch (IOException e){
            e.printStackTrace();
        }
    }
}
