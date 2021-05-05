package de.codercreep.skspeak.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.event.Event;

public class ClientIsOnlineExpression extends SimpleExpression<Boolean> {

    private Expression<Integer> id;

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{SkSpeak.getInstance().getTs3Api().isClientOnline(this.id.getSingle(event))};
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
        return "Returns the client is online.";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.id = (Expression<Integer>) expressions[0];
        return true;
    }
}
