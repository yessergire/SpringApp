package app.controller;

import app.model.Item;
import app.repository.ItemRepository;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @ModelAttribute
    public Item getItem() {
        return new Item();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("products", itemRepository.findAll());
        return "items";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(
            @Valid @ModelAttribute Item item,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "items";
        }
        itemRepository.save(item);
        return "redirect:/items";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        itemRepository.delete(id);
        return "redirect:/items";
    }
}
