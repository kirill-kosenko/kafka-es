package teg.domain.commands;

import java.util.Locale;


public class AssignRequiredTime extends ItemCommand {

    private final int requiredTime;

    public AssignRequiredTime(final String itemId, final int requiredTime) {
        super(itemId);
        this.requiredTime = requiredTime;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "AssignRequiredTime{id=%s, requiredTime=%s}", getItemId(), getRequiredTime());
    }
}
