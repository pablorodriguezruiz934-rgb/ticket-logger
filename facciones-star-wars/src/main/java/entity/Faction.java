package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Faction {
private int id;
private String code;
private String name;

    public Faction(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
