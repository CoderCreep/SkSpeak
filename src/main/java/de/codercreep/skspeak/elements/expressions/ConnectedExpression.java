package de.codercreep.skspeak.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.event.Event;

public class ConnectedExpression extends SimpleExpression<Boolean> {

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{SkSpeak.getInstance().isConnected()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "Returns the connection to the client.";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
