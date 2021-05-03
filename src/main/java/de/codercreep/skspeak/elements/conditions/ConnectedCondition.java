package de.codercreep.skspeak.elements.conditions;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.event.Event;

public class ConnectedCondition extends Condition {

    @Override
    public boolean check(Event event) {
        return SkSpeak.getInstance().isConnected();
    }

    @Override
    public String toString(Event event, boolean b) {
        return "connected";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
