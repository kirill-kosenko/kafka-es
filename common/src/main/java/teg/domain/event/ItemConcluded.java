package teg.domain.event;

import java.util.Locale;


public class ItemConcluded extends ItemEvent {

    public ItemConcluded(final String itemId) {
        super(itemId);
    }

    public ItemConcluded(final String eventId, final long timestamp, final String itemId) {
        super(eventId, timestamp, itemId);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "ItemConcluded{eventId=%s, itemId=%s}", getEventId(), getItemId());
    }
}
