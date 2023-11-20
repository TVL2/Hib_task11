package entity;



import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "shopper")
public class Shopper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "shopperId", cascade = CascadeType.REMOVE)
    List<Buy> buys;

    public Shopper() {
    }

    public Shopper(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Buy> getBuys() {
        return buys;
    }

    public void getBuys(List<Buy>buys) {
        this.buys = buys;
    }

    @Override
    public String toString() {
        return "Shopper{" +
                "name='" + name + '\'' +
                '}';
    }
}
