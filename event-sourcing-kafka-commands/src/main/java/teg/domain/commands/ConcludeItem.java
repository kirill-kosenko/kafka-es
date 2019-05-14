package teg.domain.commands;

import java.util.Locale;


public class ConcludeItem extends ItemCommand {

    public ConcludeItem(final String itemId) {
        super(itemId);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "ConcludeItem{id=%s}", getItemId());
    }
}
