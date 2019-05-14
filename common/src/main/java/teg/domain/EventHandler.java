package teg.domain;

import teg.domain.event.ItemEvent;

import java.util.concurrent.CompletableFuture;


public interface EventHandler {
    CompletableFuture<Void> onEvent(ItemEvent event);
}
