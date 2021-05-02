package de.codercreep.skspeak.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EditPasswordEffect extends Effect {

    private Expression<Integer> id;
    private Expression<String> password;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult paramParseResult) {
        this.id = (Expression<Integer>) expressions[0];
        this.password = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ts3 editPassword";
    }

    @Override
    protected void execute(Event event) {
        if(!SkSpeak.getInstance().isConnected()) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4The Bot isn't connected.");
            return;
        }

        SkSpeak.getInstance().getTs3Api().editChannel(this.id.getSingle(event), ChannelProperty.CHANNEL_PASSWORD, this.password.getSingle(event));
    }
}