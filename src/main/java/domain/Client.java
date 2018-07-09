package domain;

import lombok.*;

@Builder
@AllArgsConstructor
public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private Sex sex;
    private int age;
    private String phone;
    private Tour tour;

}
