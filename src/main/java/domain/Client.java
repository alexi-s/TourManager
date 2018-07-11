package domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private ClientSex sex;
    private int age;
    private String phone;

    public Builder toBuilder() {
        return this.new Builder();
    }

    public class Builder {
        public Builder setId(int id) {
            Client.this.id = id;
            return this;
        }
        public Builder setFirstName(String firstName) {
            Client.this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName) {
            Client.this.lastName = lastName;
            return this;
        }
        public Builder setAge(int age) {
            Client.this.age = age;
            return this;
        }
        public Builder setPhone(String phone) {
            Client.this.phone = phone;
            return this;
        }

        public Client build() {
            return Client.this;
        }

    }
}
