package by.roman_mayorov;

import by.roman_mayorov.dao.ClientDao;
import by.roman_mayorov.dto.ClientDto;
import by.roman_mayorov.dto.ClientFilter;

public class JdbcRunner {
    public static void main(String[] args){
        var clientDao = ClientDao.getInstance();
        ClientFilter filter = new ClientFilter(26, "РОМАН", 2);
        System.out.println(clientDao.findAll(filter));
//        Client client = new Client();
//        client = clientDao.findById(6L).get();
//        System.out.println(client);
//        client.setFirstName("John");
//        client.setLastName("Travolta");
//        client.setAge(25);
//        clientDao.update(client);
    }
}
