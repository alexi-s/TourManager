package service;

import domain.Client;
import repository.ClientDAO;
import repository.impl.ClientDAOH2Impl;

import java.util.List;

public class ClientService {

    private ClientDAO clientDAO = new ClientDAOH2Impl();

    public int addClient(Client client) {
        return clientDAO.addClient(client);
    }

    public void deleteClient(Client client) {
        clientDAO.deleteClient(client);
    }

    public void updateClient(Client client) {
        clientDAO.updateClient(client);
    }

    public List<Client> getAllClients() {
        return clientDAO.getAllClients();
    }

    public List<Client> getSortedClients() {
        return clientDAO.getClientsSortedByLastName();
    }

    public Client getClientById(int id) {
        return clientDAO.getClientById(id);
    }

    public Client getClientByLastName(String lastName) {
        return clientDAO.getClientByLastName(lastName);
    }
}
