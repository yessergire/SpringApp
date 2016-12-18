package app.model;

import javax.persistence.Entity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
public class Customer extends AbstractPersistable<Long>{

    @Length(min=3, max=40)
    @NotBlank
    private String name;

    @Length(min=5, max=40)
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private boolean adminAccess;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }
}
