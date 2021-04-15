package de.codercreep.skspeak.elements.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class BanEffect extends Effect {

    private int args;
    private Expression<Integer> clientId;
    private Expression<Long> timeInSeconds;
    private Expression<String> reason;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, SkriptParser.ParseResult paramParseResult) {
        this.args = expressions.length;

        if(args == 2) {
            this.clientId = (Expression<Integer>) expressions[0];
            this.reason = (Expression<String>) expressions[1];
        } else if(args == 3) {
            this.clientId = (Expression<Integer>) expressions[0];
            this.timeInSeconds = (Expression<Long>) expressions[1];
            this.reason = (Expression<String>) expressions[2];
        }
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ts3 ban";
    }

    @Override
    protected void execute(Event event) {
        if(!SkSpeak.getInstance().isConnected()) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4The Bot isn't connected.");
            return;
        }

        if(args == 2)
            SkSpeak.getInstance().getTs3Api().banClient(this.clientId.getSingle(event), this.reason.getSingle(event));
        else if(args == 3)
            SkSpeak.getInstance().getTs3Api().banClient(this.clientId.getSingle(event), this.timeInSeconds.getSingle(event), this.reason.getSingle(event));
    }
}