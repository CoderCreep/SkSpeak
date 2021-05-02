package de.codercreep.skspeak.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class AddClientToServerGroupEffect extends Effect {

    private Expression<Integer> groupId;
    private Expression<Integer> clientId;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult paramParseResult) {
        this.groupId = (Expression<Integer>) expressions[0];
        this.clientId = (Expression<Integer>) expressions[1];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ts3 addClientToServerGroup";
    }

    @Override
    protected void execute(Event event) {
        if(!SkSpeak.getInstance().isConnected()) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4The Bot isn't connected.");
            return;
        }

        SkSpeak.getInstance().getTs3Api().addClientToServerGroup(this.groupId.getSingle(event), this.clientId.getSingle(event));
    }
}