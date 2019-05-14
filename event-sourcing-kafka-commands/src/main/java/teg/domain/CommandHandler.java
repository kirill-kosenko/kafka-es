package teg.domain;

import teg.domain.commands.ItemCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CommandHandler {

    CompletableFuture<Void> onCommand(ItemCommand command);
    CompletableFuture<Void> onCommand(List<ItemCommand> command);
}
