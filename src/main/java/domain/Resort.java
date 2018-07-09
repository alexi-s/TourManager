package domain;

import lombok.Data;

@Data
public class Resort {

    private int id;
    private String name;

    public Resort(String name) {
        this.name = name;
    }
}
