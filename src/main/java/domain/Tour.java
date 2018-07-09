package domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tour {

    private int id;
    private String name;
    private TourType type;
    private List<Country> countries;
    private List<Resort> resorts;
    private int duration;
    private float price;

}
