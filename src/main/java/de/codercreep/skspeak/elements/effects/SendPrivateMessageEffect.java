package de.codercreep.skspeak.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class SendPrivateMessageEffect extends Effect {

    private Expression<Integer> clientId;
    private Expression<String> message;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult paramParseResult) {
        this.message = (Expression<String>) expressions[0];
        this.clientId = (Expression<Integer>) expressions[1];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ts3 sendPrivateMessage";
    }

    @Override
    protected void execute(Event event) {
        if(!SkSpeak.getInstance().isConnected()) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4The Bot isn't connected.");
            return;
        }

        SkSpeak.getInstance().getTs3Api().sendPrivateMessage(this.clientId.getSingle(event), this.message.getSingle(event));
    }
}