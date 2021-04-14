package de.codercreep.skspeak.elements;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.*;
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

    @Override
    protected void execute(Event event) {
        try {
            TS3Config config = new TS3Config();
            config.setHost(host.getSingle(event));
            config.setQueryPort(port.getSingle(event));

            TS3Query query = new TS3Query(config);
            SkSpeak.getInstance().setTs3Query(query);

            TS3Api api = query.getApi();
            SkSpeak.getInstance().setTs3Api(api);
            api.login(login.getSingle(event), password.getSingle(event));
            api.selectVirtualServerById(1);
            api.setNickname(user.getSingle(event));
            api.registerAllEvents();

            api.addTS3Listeners(new TS3Listener() {
                @Override
                public void onTextMessage(TextMessageEvent textMessageEvent) {

                }

                @Override
                public void onClientJoin(ClientJoinEvent clientJoinEvent) {

                }

                @Override
                public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {

                }

                @Override
                public void onServerEdit(ServerEditedEvent serverEditedEvent) {

                }

                @Override
                public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {

                }

                @Override
                public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

                }

                @Override
                public void onClientMoved(ClientMovedEvent clientMovedEvent) {

                }

                @Override
                public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

                }

                @Override
                public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

                }

                @Override
                public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

                }

                @Override
                public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

                }

                @Override
                public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

                }
            });
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§aConnected successfuly to the TeamSpeak Server.");
        } catch(TS3ConnectionFailedException exception) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§cCan't connect to the TeamSpeak Server.");
        }
    }

    @Override
    public String toString(Event event, boolean b) {
        return "skspeak connect";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        if(expressions.length == 5)  {
            this.host = (Expression<String>) expressions[0];
            this.user = (Expression<String>) expressions[1];
            this.login = (Expression<String>) expressions[2];
            this.password = (Expression<String>) expressions[3];
            this.port = (Expression<Integer>) expressions[4];
        }
        return true;
    }
}
