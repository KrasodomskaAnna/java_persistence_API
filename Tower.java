package org.example;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "Tower.selectAll",
                query = "SELECT t FROM Tower t"),
        @NamedQuery(name = "Tower.selectWhereName",
                query = "SELECT t FROM Tower t WHERE t.name = :name"),
})

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Tower {
    @Id
    @Getter
    private String name;
    @Getter
    @Setter
    private int height;
    @Getter
    @Setter
    @ToString.Exclude
    @OneToMany(mappedBy = "tower", cascade = {CascadeType.ALL}, orphanRemoval=true, fetch = FetchType.LAZY)
    private List<Mage> mages;

    public Tower(String name, int height) {
        this.name = name;
        this.height = height;
    }
}
