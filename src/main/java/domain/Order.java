package domain;

import lombok.Data;

@Data
public class Order {

    private int id;
    private Tour tour;
    private Client client;
    private boolean isPaid;

}
