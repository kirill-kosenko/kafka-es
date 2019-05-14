package teg.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Component
public class DefaultItemView implements ItemView {

    private final ItemRepository repository;

    @Autowired
    public DefaultItemView(final ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public CompletableFuture<List<Item>> getItems() {

        return CompletableFuture.supplyAsync(() -> {
            final List<Item> items = new ArrayList<>();
            items.addAll(repository.findAll());
            return Collections.unmodifiableList(items);
        });
    }

    @Override
    public CompletableFuture<Optional<Item>> getItem(final String itemId) {

        return CompletableFuture.supplyAsync(() -> repository.findById(itemId));
    }
}
