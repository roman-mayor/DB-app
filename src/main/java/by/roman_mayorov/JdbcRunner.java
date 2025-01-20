package by.roman_mayorov;

import by.roman_mayorov.dao.ClientDao;

public class JdbcRunner {
    public static void main(String[] args){
        var clientDao = ClientDao.getInstance();
        System.out.println(clientDao.findAll());
        System.out.println(clientDao.findById(6L));
    }
}
