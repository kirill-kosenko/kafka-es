package teg.api;

import teg.domain.CommandHandler;
import teg.domain.commands.CreateItem;
import teg.domain.commands.ItemCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import teg.kafka.TransactionalCommandPublisher;

import java.util.concurrent.CompletableFuture;

@RestController
public class ItemsCommandResource {

    private static final Logger log = LoggerFactory.getLogger(ItemsCommandResource.class);

    private final CommandHandler commandHandler;
    private final TransactionalCommandPublisher commandPublisher;

    @Autowired
    public ItemsCommandResource(final CommandHandler commandHandler, TransactionalCommandPublisher commandPublisher) {
        this.commandHandler = commandHandler;
        this.commandPublisher = commandPublisher;
    }

    private static ResponseEntity<Object> apply(Void dontCare) {
        return ResponseEntity.accepted().build();
    }

    @RequestMapping(path = "/items", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<Object>> createItem(@RequestBody CreateItemRequest createItem) {

        log.info("Received a create command request with data {}.", createItem);

        commandPublisher.log(commandsFor(createItem));

//        return commandHandler
//                .onCommand(commandsFor(createItem))
//                .thenApply(ItemsCommandResource::apply)
//                .exceptionally(e -> {
//                    log.warn("Caught an exception at the service boundary.", e);
//                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//                });
        return CompletableFuture.completedFuture(ResponseEntity.accepted().build());
    }

    private ItemCommand commandsFor(final CreateItemRequest createItem) {
        return new CreateItem(createItem.getDescription());
    }
}
