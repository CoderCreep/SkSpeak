package de.codercreep.skspeak.elements.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.event.Event;

public class IdExpression extends SimpleExpression<Integer> {

    private Expression<String> ip;

    @Override
    protected Integer[] get(Event event) {
        for(Client client : SkSpeak.getInstance().getTs3Api().getClients()) {
            if(client.getIp().equalsIgnoreCase(this.ip.getSingle(event)))
                return new Integer[]{client.getId()};
            else
                return null;
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "Gets the Id of the Client with the Ip adress.";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.ip = (Expression<String>) expressions[0];
        return true;
    }
}
