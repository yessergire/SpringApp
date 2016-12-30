package app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Category extends AbstractPersistable<Long> {

    @NotBlank
    @Length(min=3, max=64)
    @Column(unique = true)
    private String name;

    @NotBlank
    @Length(min=3, max=1024)
    @Column(unique = true)
    private String description;

    @ManyToMany(mappedBy = "categories")
    private List<Item> items;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Item> getItems() {
	if (items == null) {
	    items = new ArrayList<>();
	}
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

}
