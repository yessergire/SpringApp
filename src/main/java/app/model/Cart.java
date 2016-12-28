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

    private Map<Item, Long> items = new TreeMap<>();;

    public Map<Item, Long> getItems() {
        return items;
    }

    public void setItems(Map<Item, Long> items) {
        this.items = items;
    }
    
    public void add(Item item) {
        if (items.containsKey(item)) {
            items.put(item, 1L + items.get(item));
        } else {
            items.put(item, 1L);
        }
    }

    public void remove(Item item) {
        if (items.containsKey(item)) {
            if (items.get(item) > 1) {
        	items.put(item, items.get(item) - 1);
            }
            else {
        	items.remove(item);
            }
        }
    }
}
