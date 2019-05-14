package teg.domain.commands;


abstract public class ItemCommand {

    private final String itemId;

    public ItemCommand(final String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
