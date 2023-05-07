package org.example;

import lombok.*;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Mage.selectAll",
                query = "SELECT m FROM Mage m"),
        @NamedQuery(name = "Mage.selectWhereName",
                query = "SELECT m FROM Mage m WHERE m.name = :name"),
})

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Mage {
    @Id
    @Getter
    private String name;
    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    @ManyToOne
    private Tower tower;

    public Mage(String name, int level, Tower tower) {
        this.name = name;
        this.level = level;
        this.tower = tower;
    }
}
