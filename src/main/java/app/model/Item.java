package app.model;

import javax.persistence.Entity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Item extends AbstractPersistable<Long> {
    @NotBlank
    @Length(min = 3, max = 50)
    private String name;

    @NotBlank
    @Length(min = 3, max = 120)
    private String imageUrl;

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
}
