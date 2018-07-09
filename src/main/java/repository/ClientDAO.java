package repository;

import domain.Client;

import java.util.List;

public interface ClientDAO {

    int addClient(Client client);

    void deleteClient(Client client);

    void updateClient(Client client);

    List<Client> getAllTours();

}
