package by.roman_mayorov.dao;

import by.roman_mayorov.dto.ClientFilter;
import by.roman_mayorov.entity.Client;

import java.util.List;
import java.util.Optional;

public interface Dao {

     Client save(Client client);
     List<Client> findAll(ClientFilter filter);
     List<Client> findAll();
     Optional<Client> findById(Long id);
     void update(Client client);
     boolean delete(Long id);
}
