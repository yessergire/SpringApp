package app.controller;

import app.model.Item;
import app.repository.ItemRepository;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @Autowired
    ItemRepository itemRepository;
    
    @PostConstruct
    public void init() {
        Item item = new Item();
        item.setName("Sony TV");
        item.setPrice(999.99);
        item.setImageUrl("http://www.samsung.com/us/2012-smart-blu-ray-player/img/tv-front.png");
        itemRepository.save(item);

        item = new Item();
        item.setName("PS4");
        item.setPrice(300.00);
        item.setImageUrl("cdn.wccftech.com/wp-content/uploads/2015/02/playstation_4_console_controller_ps4_92842_3840x2160.jpg");
        itemRepository.save(item);

        item = new Item();
        item.setName("Tablet");
        item.setPrice(100.00);
        item.setImageUrl("http://www.bell.ca/Styles/wireless/all_languages/all_regions/catalog_images/large/mobile/front/iPadAir_SpaceGrey_en.png");
        itemRepository.save(item);
    }

    @RequestMapping("*")
    public String handleDefault() {
        return "home";
    }

}
