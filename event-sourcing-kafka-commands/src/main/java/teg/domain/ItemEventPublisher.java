package teg.domain;

import teg.domain.event.ItemEvent;

public interface ItemEventPublisher {

    void log(ItemEvent itemEvent);
}
