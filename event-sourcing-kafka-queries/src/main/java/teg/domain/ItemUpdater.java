package teg.domain;

import teg.domain.event.ItemCreated;
import teg.domain.event.ItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;


@Service
public class ItemUpdater implements EventHandler {

    private static final Logger log = LoggerFactory.getLogger(ItemUpdater.class);

    private final ItemRepository repository;

    public ItemUpdater(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<Void> onEvent(ItemEvent event) {

        return CompletableFuture.runAsync(() -> {
            if (event instanceof ItemCreated) {
                createNewItem((ItemCreated) event);
            } else {
                modifyExistingItem(event);
            }
        });
    }

    private void createNewItem(ItemCreated itemCreated) {
        final Item newItem = new Item(itemCreated);
        repository.save(newItem);
        log.info("Applied event {} and created new item with current state {}.", itemCreated, newItem);
    }

    private void modifyExistingItem(ItemEvent event) {
        final Item item = repository.findById(event.getItemId()).orElseThrow(RuntimeException::new);
        item.project(event);
        final Item updatedItem = repository.save(item);
        log.info("Applied event {} to the aggregate with ID {} and current state {}.", event, event.getItemId(), updatedItem);
    }
}
