package de.codercreep.skspeak.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EditClientChannelGroup extends Effect {

    private Expression<Integer> groupId;
    private Expression<Integer> channelId;
    private Expression<Integer> clientDbId;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult paramParseResult) {
        this.clientDbId = (Expression<Integer>) expressions[0];
        this.groupId = (Expression<Integer>) expressions[1];
        this.channelId = (Expression<Integer>) expressions[2];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ts3 editChannelGroup";
    }

    @Override
    protected void execute(Event event) {
        if(!SkSpeak.getInstance().isConnected()) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4The Bot isn't connected.");
            return;
        }

        SkSpeak.getInstance().getTs3Api().setClientChannelGroup(this.groupId.getSingle(event), this.channelId.getSingle(event), this.clientDbId.getSingle(event));
    }
}