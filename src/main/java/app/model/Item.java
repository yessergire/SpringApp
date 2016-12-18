package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Item extends AbstractPersistable<Long> implements Comparable<Item> {

    @NotBlank
    @Length(min = 3, max = 50)
    @Column(unique = true)
    private String name;

    @NotBlank
    @Length(min = 3, max = 120)
    private String imageUrl;

    @Min(0)
    private long count;

    @Min(0)
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Item o) {
        return name.compareTo(o.name);
    }

}
