package by.roman_mayorov.servlets;

import by.roman_mayorov.entity.Client;
import by.roman_mayorov.service.ClientService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/clients_description")
public class ClientsDescriptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        ClientService clientService = ClientService.getINSTANCE();

        Long clientId = Long.valueOf(req.getParameter("clientId"));

        Optional<Client> client = clientService.findById(clientId);

        var pw = resp.getWriter();

        pw.println("<html><body>");
        pw.println("<h1>ИНФОРМАЦИЯ О КЛИЕНТЕ:</h1>");
        pw.write("<h2>%s  %s  %d ЛЕТ</h2>"
                .formatted(client.get().getFirstName(), client.get().getLastName(), client.get().getAge()));
    }
}
