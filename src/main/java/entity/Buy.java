package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "buy")
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product productId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopper_id")
    private Shopper shopperId;

    @Column(name = "price", nullable = false)
    private Double price;


    public Buy(){

    }

    public Buy(Product productId, Shopper shopperId) {
        this.productId = productId;
        this.shopperId = shopperId;
        this.price = productId.getPrice();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Shopper getShopperId() {
        return shopperId;
    }

    public void setShopperId(Shopper shopperId) {
        this.shopperId = shopperId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
