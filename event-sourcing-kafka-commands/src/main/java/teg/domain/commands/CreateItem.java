package teg.domain.commands;

import java.util.Locale;
import java.util.UUID;


public class CreateItem extends ItemCommand {

    private final String description;

    public CreateItem(final String description) {
        super(UUID.randomUUID().toString().substring(0, 7));
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "CreateItem{id=%s, description=%s}", getItemId(), getDescription());
    }
}
