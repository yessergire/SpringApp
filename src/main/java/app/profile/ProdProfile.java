package app.profile;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("production")
public class ProdProfile {

    @PostConstruct
    public void init() {
    }

}
