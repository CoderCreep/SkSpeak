package de.codercreep.skspeak.elements;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EffConnect extends Effect {

    private Expression<String> host;
    private Expression<String> user;
    private Expression<String> login;
    private Expression<String> password;
    private Expression<Integer> port;

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean kleenean, ParseResult paramParseResult) {
        this.host = (Expression<String>) expressions[0];
        this.user = (Expression<String>) expressions[1];
        this.login = (Expression<String>) expressions[2];
        this.password = (Expression<String>) expressions[3];
        this.port = (Expression<Integer>) expressions[4];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "ts3 connect";
    }

    @Override
    protected void execute(Event event) {
        try {
            TS3Config config = new TS3Config();

            config.setHost(host.getSingle(event));

            config.setQueryPort(port.getSingle(event));

            TS3Query query = new TS3Query(config);

            query.connect();

            TS3Api api = query.getApi();

            api.login(login.getSingle(event), password.getSingle(event));

            api.selectVirtualServerById(1);

            api.setNickname(user.getSingle(event));

            api.registerAllEvents();

            api.addTS3Listeners(new TS3Listener() {

                @Override
                public void onTextMessage(TextMessageEvent event) {

                }

                @Override
                public void onServerEdit(ServerEditedEvent event) {

                }

                @Override
                public void onClientMoved(ClientMovedEvent event) {

                }

                @Override
                public void onClientLeave(ClientLeaveEvent event) {

                }

                @Override
                public void onClientJoin(ClientJoinEvent event) {

                }

                @Override
                public void onChannelEdit(ChannelEditedEvent event) {

                }

                @Override
                public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent event) {

                }

                @Override
                public void onChannelCreate(ChannelCreateEvent event) {

                }

                @Override
                public void onChannelDeleted(ChannelDeletedEvent event) {

                }

                @Override
                public void onChannelMoved(ChannelMovedEvent event) {

                }

                @Override
                public void onChannelPasswordChanged(ChannelPasswordChangedEvent event) {

                }

                @Override
                public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent event) {

                }
            });
            SkSpeak.getInstance().setTs3Query(query);
            SkSpeak.getInstance().setTs3Api(api);
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§aSuccessfully connected to the TeamSpeak Server.");
        } catch (TS3ConnectionFailedException exception) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4Couldn't connect to TeamSpeak Server.");
        }
    }
}