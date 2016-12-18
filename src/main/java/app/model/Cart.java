package app.model;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Cart implements Serializable {

    private Map<Item, Long> items;

    public Map<Item, Long> getItems() {
        if (items == null) {
            items = new TreeMap<>();
        }
        return items;
    }

    public void setItems(Map<Item, Long> items) {
        this.items = items;
    }
    
    public void add(Item item) {
        getItems().put(item, 1L + getItems().getOrDefault(item, 0L));
    }

    public void remove(Item item) {
        if (getItems().containsKey(item)) {
            if (getItems().get(item) > 1) {
                getItems().put(item, getItems().get(item) - 1);
            }
            else {
                getItems().remove(item);
            }
        }
    }
}
