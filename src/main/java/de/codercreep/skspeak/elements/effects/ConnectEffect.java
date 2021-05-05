package de.codercreep.skspeak.elements.effects;

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
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import de.codercreep.skspeak.SkSpeak;
import de.codercreep.skspeak.elements.events.bukkit.ClientConnectEvent;
import de.codercreep.skspeak.elements.events.bukkit.ClientDisconnectEvent;
import de.codercreep.skspeak.elements.events.bukkit.TextMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class ConnectEffect extends Effect {

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
        if(SkSpeak.getInstance().isConnected()) {
            Bukkit.getConsoleSender().sendMessage(SkSpeak.PREFIX + "§4The Bot is already connected.");
            return;
        }

        try {
            TS3Config config = new TS3Config();

            config.setHost(this.host.getSingle(event));

            config.setQueryPort(this.port.getSingle(event));

            TS3Query query = new TS3Query(config);

            query.connect();

            TS3Api api = query.getApi();

            api.login(this.login.getSingle(event), this.password.getSingle(event));

            api.selectVirtualServerById(1);

            api.setNickname(this.user.getSingle(event));

            api.registerAllEvents();

            api.addTS3Listeners(new TS3Listener() {

                @Override
                public void onTextMessage(com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new TextMessageEvent());
                    });
                }

                @Override
                public void onServerEdit(ServerEditedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ServerEditedEvent());
                    });
                }

                @Override
                public void onClientMoved(ClientMovedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ClientMovedEvent());
                    });
                }

                @Override
                public void onClientLeave(ClientLeaveEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new ClientDisconnectEvent());
                    });
                }

                @Override
                public void onClientJoin(ClientJoinEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new ClientConnectEvent());
                    });
                }

                @Override
                public void onChannelEdit(ChannelEditedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ChannelEditedEvent());
                    });
                }

                @Override
                public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ChannelDescriptionEditedEvent());
                    });
                }

                @Override
                public void onChannelCreate(ChannelCreateEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ChannelCreateEvent());
                    });
                }

                @Override
                public void onChannelDeleted(ChannelDeletedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ChannelDeletedEvent());
                    });
                }

                @Override
                public void onChannelMoved(ChannelMovedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ChannelMovedEvent());
                    });
                }

                @Override
                public void onChannelPasswordChanged(ChannelPasswordChangedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.ChannelPasswordChangedEvent());
                    });
                }

                @Override
                public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent event) {
                    Bukkit.getScheduler().runTask(SkSpeak.getInstance(), () -> {
                        Bukkit.getPluginManager().callEvent(new de.codercreep.skspeak.elements.events.bukkit.PrivilegeKeyUsedEvent());
                    });
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