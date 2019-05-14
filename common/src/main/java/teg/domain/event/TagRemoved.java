package teg.domain.event;

import java.util.Locale;


public class TagRemoved extends ItemEvent {

    private final String tag;

    public TagRemoved(final String itemId, final String tag) {
        super(itemId);
        this.tag = tag;
    }

    public TagRemoved(final String eventId, final long timestamp, final String itemId, final String tag) {
        super(eventId, timestamp, itemId);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "TagRemoved{eventId=%s, itemId=%s, tag=%s}", getEventId(), getItemId(), getTag());
    }
}
