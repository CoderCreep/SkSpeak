package de.codercreep.skspeak.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class AddChannelClientPermissionEffect extends Effect {

    private Expression<Integer> channelId;
    private Expression<Integer> clientDbId;
    private Expression<String> permission;
    private Expression<Integer> permissionValue;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult paramParseResult) {
        this.channelId = (Expression<Integer>) expressions[0];
        this.clientDbId = (Expression<Integer>) expressions[1];
        this.permission = (Expression<String>) expressions[2];
        this.permissionValue = (Expression<Integer>) expressions[3];
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

        SkSpeak.getInstance().getTs3Api().addChannelClientPermission(this.channelId.getSingle(event), this.clientDbId.getSingle(event), this.permission.getSingle(event),
                this.permissionValue.getSingle(event));
    }
}