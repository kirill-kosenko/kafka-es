package teg.api;

import teg.domain.CommandHandler;
import teg.domain.commands.AssignDueDate;
import teg.domain.commands.AssignRequiredTime;
import teg.domain.commands.AssignTags;
import teg.domain.commands.ConcludeItem;
import teg.domain.commands.ItemCommand;
import teg.domain.commands.MoveItemToList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Slf4j
@RestController
public class ItemCommandResource {

    private final CommandHandler commandHandler;

    public ItemCommandResource(final CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @RequestMapping(path = "/items/{itemId}", method = RequestMethod.DELETE, produces = "application/json")
    public CompletableFuture<ResponseEntity<Object>> deleteItem(@PathVariable("itemId") String itemId) {

        log.info("Received a delete item request for item with ID {}.", itemId);

        return commandHandler
                .onCommand(new ConcludeItem(itemId))
                .thenApply(dontCare -> ResponseEntity.accepted().build())
                .exceptionally(e -> {
                    log.warn("Caught an exception at the service boundary.", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @RequestMapping(path = "/items/{itemId}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public CompletableFuture<ResponseEntity<Object>> updateItem(@PathVariable("itemId") String itemId,
                                                                @RequestBody UpdateItemRequest updateItem) {

        log.info("Received an update item request for item with ID {} and updated data {}.", itemId, updateItem);

        return commandHandler
                .onCommand(commandsFor(itemId, updateItem).orElse(Collections.emptyList()))
                .thenApply(dontCare -> ResponseEntity.accepted().build())
                .exceptionally((e -> {
                    log.warn("Caught an exception at the service boundary.", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }));
    }

    private Optional<List<ItemCommand>> commandsFor(final String itemId, final UpdateItemRequest updateItem) {
        final List<ItemCommand> commands = new ArrayList<>();
        if (updateItem.getAssociatedList() != null) {
            commands.add(new MoveItemToList(itemId, updateItem.getAssociatedList()));
        }
        if (updateItem.getDueDate() != null) {
            final Date dueDate = Date.from(Instant.ofEpochMilli(updateItem.getDueDate()));
            commands.add(new AssignDueDate(itemId, dueDate));
        }
        if (updateItem.getRequiredTime() != null) {
            commands.add(new AssignRequiredTime(itemId, updateItem.getRequiredTime()));
        }
        if (updateItem.getTags() != null) {
            commands.add(new AssignTags(itemId, updateItem.getTags()));
        }
        return Optional.of(Collections.unmodifiableList(commands));
    }
}
