package app.profile;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import app.model.Customer;
import app.model.Item;
import app.repository.CustomerRepository;
import app.repository.ItemRepository;

@Configuration
@Profile("test")
public class TestProfile {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void init() {
    	Customer customer = new Customer();
    	customer.setName("Default user");
    	customer.setUsername("username");
    	customer.setPassword("password");
    	customerRepository.save(customer);

    	for (int i = 1; i <= 1000; i++) {
            Item item = new Item();
            item.setName("TV " + (1000 * i));
            item.setPrice(350.15 * i);
            item.setImageUrl("http://www.samsung.com/us/2012-smart-blu-ray-player/img/tv-front.png");
            itemRepository.save(item);

    	    item = new Item();
    	    item.setName("Game station " + i);
    	    item.setPrice(99.99 * i);
    	    item.setImageUrl("http://static.trustedreviews.com/94/00003ab9b/a4b6_orh500w750/ps4-pro-and-slim-1.jpg");
    	    itemRepository.save(item);

    	    item = new Item();
    	    item.setName("Smart device " + i);
    	    item.setPrice(100.00);
    	    item.setImageUrl("http://www.bell.ca/Styles/wireless/all_languages/all_regions/catalog_images/large/mobile/front/iPadAir_SpaceGrey_en.png");
    	    itemRepository.save(item);
    	}
    }

}
