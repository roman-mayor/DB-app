package by.roman_mayorov.service;

import by.roman_mayorov.dao.ClientDao;
import by.roman_mayorov.dto.ClientDto;
import by.roman_mayorov.entity.Client;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService {

    private static final ClientService INSTANCE = new ClientService();

    private final ClientDao clientDao = ClientDao.getInstance();

    public List<ClientDto> findAll() {
        return clientDao.findAll().stream().map(client ->
                ClientDto.getInstance(client.getId(), "%s + %s + %d"
                        .formatted(client.getFirstName(), client.getLastName(), client.getAge())))
                .collect(Collectors.toList());
    }

    public Optional<Client> findById(Long id){
        return clientDao.findById(id);
    }

    public static ClientService getINSTANCE() {
        return INSTANCE;
    }

    private ClientService(){}
}
