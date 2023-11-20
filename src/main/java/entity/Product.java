package entity;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "price", nullable = false)
    private Double price;


    @OneToMany(mappedBy = "productId", cascade = CascadeType.REMOVE)
    List<Buy> buys;

    public Product() {
    }


    public Product(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Buy> getBuys() {
        return buys;
    }

    public void getBuys(List<Buy> buys) {
        this.buys = buys;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
