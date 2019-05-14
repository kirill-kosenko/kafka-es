package teg.api;

import teg.domain.Item;
import teg.domain.ItemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;


@RestController
public class ItemsQueryResource {

    private static final Logger log = LoggerFactory.getLogger(ItemsQueryResource.class);

    private final ItemView itemView;

    public ItemsQueryResource(final ItemView itemView) {
        this.itemView = itemView;
    }

    @RequestMapping(path = "/items", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<List<Item>>> listAllItems() {

        log.info("Received a list all managed items request.");

        return itemView.getItems()
                .thenApply(ResponseEntity::ok)
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
