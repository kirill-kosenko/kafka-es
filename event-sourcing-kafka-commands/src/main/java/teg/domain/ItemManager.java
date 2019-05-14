package teg.domain;

import teg.domain.commands.AssignDueDate;
import teg.domain.commands.AssignRequiredTime;
import teg.domain.commands.AssignTags;
import teg.domain.commands.ConcludeItem;
import teg.domain.commands.CreateItem;
import teg.domain.commands.ItemCommand;
import teg.domain.commands.MoveItemToList;
import teg.domain.event.DueDateAssigned;
import teg.domain.event.ItemConcluded;
import teg.domain.event.ItemCreated;
import teg.domain.event.ItemEvent;
import teg.domain.event.ItemMovedToList;
import teg.domain.event.RequiredTimeAssigned;
import teg.domain.event.TagAssigned;
import teg.domain.event.TagRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class ItemManager implements CommandHandler, EventHandler {

    private static final Logger log = LoggerFactory.getLogger(ItemManager.class);

    private final Map<String, Item> items = new ConcurrentHashMap<>();

    private final ItemEventPublisher publisher;

    public ItemManager(final ItemEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public CompletableFuture<Void> onCommand(final ItemCommand command) {

        return CompletableFuture.runAsync(() -> validate(command).forEach(publisher::log));
    }

    @Override
    public CompletableFuture<Void> onCommand(final List<ItemCommand> commands) {

        return CompletableFuture.runAsync(() ->
                commands
                        .stream()
                        .flatMap(command -> validate(command).stream())
                        .forEach(publisher::log));
    }

    private List<ItemEvent> validate(final ItemCommand command) {

        if (command instanceof CreateItem) {
            return validate((CreateItem) command);
        }

        final Item item = items.get(command.getItemId());
        if (item == null) {
            log.warn("Item with ID {} does not exist but has received a command ({}). This is probably due to a " +
                            "out-of-order arrival of events, which should not happen due to Kafka's topic ordering guarantees. " +
                            "Please check your Kafka topic configuration. The command will be dropped.",
                    command.getItemId(), command);
            return emptyList();
        }

        if (command instanceof AssignDueDate) return validate(item, (AssignDueDate) command);
        else if (command instanceof AssignRequiredTime) return validate(item, (AssignRequiredTime) command);
        else if (command instanceof AssignTags) return validate(item, (AssignTags) command);
        else if (command instanceof ConcludeItem) return validate(item, (ConcludeItem) command);
        else if (command instanceof MoveItemToList) return validate(item, (MoveItemToList) command);
        else return emptyList();
    }

    private List<ItemEvent> validate(final CreateItem command) {
        return singletonList(new ItemCreated(command.getItemId(), command.getDescription()));
    }

    private List<ItemEvent> validate(final Item item, final AssignDueDate command) {
        final Date now = Date.from(Instant.now(Clock.systemUTC()));
        if (item.isDone() || command.getDueDate().before(now)) {
            logValidationFailed(item, command);
            return emptyList();
        } else {
            return singletonList(new DueDateAssigned(item.getId(), command.getDueDate()));
        }
    }

    private List<ItemEvent> validate(final Item item, final AssignRequiredTime command) {
        if (item.isDone() || command.getRequiredTime() < 0) {
            logValidationFailed(item, command);
            return emptyList();
        } else {
            return singletonList(new RequiredTimeAssigned(item.getId(), command.getRequiredTime()));
        }
    }

    private List<ItemEvent> validate(final Item item, final AssignTags command) {

        if (item.isDone()) {
            logValidationFailed(item, command);
            return emptyList();
        }

        final List<ItemEvent> events = new ArrayList<>();
        events.addAll(command.getTags()
                .stream()
                .filter(tag -> !item.getTags().contains(tag))
                .map(tag -> new TagAssigned(command.getItemId(), tag))
                .collect(Collectors.toList()));
        events.addAll(item.getTags()
                .stream()
                .filter(tag -> !command.getTags().contains(tag))
                .map(tag -> new TagRemoved(command.getItemId(), tag))
                .collect(Collectors.toList()));
        return events;
    }

    private List<ItemEvent> validate(final Item item, final ConcludeItem command) {
        if (item.isDone()) {
            logValidationFailed(item, command);
            return emptyList();
        } else {
            return singletonList(new ItemConcluded(item.getId()));
        }
    }

    private List<ItemEvent> validate(final Item item, final MoveItemToList command) {
        if (item.isDone() || command.getList().equals(item.getAssociatedList())) {
            logValidationFailed(item, command);
            return emptyList();
        } else {
            return singletonList(new ItemMovedToList(item.getId(), command.getList()));
        }
    }

    private void logValidationFailed(final Item currentState, final ItemCommand command) {
        log.warn("Received command {} which failed to validate against the current state of aggregate {}. " +
                "Skipping this command.", command, currentState);
    }

    @Override
    public CompletableFuture<Void> onEvent(final ItemEvent event) {

        return CompletableFuture.runAsync(() -> {

            if (event instanceof ItemCreated) {
                createNewItem((ItemCreated) event);
            } else {
                modifyExistingItem(event);
            }
        });
    }

    private void createNewItem(final ItemCreated event) {
        final Item newItem = new Item(event);
        items.put(newItem.getId(), newItem);
    }

    private void modifyExistingItem(final ItemEvent event) {
        final Item currentState = items.get(event.getItemId());

        if (currentState == null) {
            throw new IllegalStateException("Event " + event.toString() + " cannot be applied. There is no state for item with ID " + event.getItemId() + ".");
        }

        currentState.project(event);
        items.put(currentState.getId(), currentState);
    }
}
