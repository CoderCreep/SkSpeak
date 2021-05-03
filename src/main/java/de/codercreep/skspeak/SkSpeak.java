package de.codercreep.skspeak;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.lang.ExpressionType;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.codercreep.skspeak.elements.conditions.ConnectedCondition;
import de.codercreep.skspeak.elements.effects.*;
import de.codercreep.skspeak.elements.events.bukkit.*;
import de.codercreep.skspeak.elements.events.skript.*;
import de.codercreep.skspeak.elements.expressions.IdExpression;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkSpeak extends JavaPlugin {

    public static final String PREFIX = "§7[§6SkSpeak§7] §r";
    private static SkSpeak instance;
    private SkriptAddon addon;
    private TS3Api ts3Api;
    private TS3Query ts3Query;

    @Override
    public void onEnable() {
        if(Bukkit.getPluginManager().getPlugin("Skript") == null) {
            Bukkit.getConsoleSender().sendMessage(PREFIX + "§4You need Skript to Use SkSpeak!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        if(!getDataFolder().exists())
            getDataFolder().mkdirs();

        instance = this;

        this.addon = Skript.registerAddon(this);

        try {
            this.addon.loadClasses("de.codercreep.skspeak", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        Skript.registerExpression(IdExpression.class, Integer.class, ExpressionType.COMBINED, "[skspeak] client id of [user with ip] %string%");

        Skript.registerEffect(ConnectEffect.class, "[skspeak] connect (to|with) %string% with user %string% and name %string%, %string% (on|with) query port %integer%");
        Skript.registerEffect(DisconnectEffect.class, "[skspeak] disconnect [from] query bot");
        Skript.registerEffect(BroadcastEffect.class, "[skspeak] broadcast message %string% [with [query] bot]");
        Skript.registerEffect(EditDescriptionEffect.class, "[skspeak] (set|edit) description (from|of) channel [with id] %integer% to %string%");
        Skript.registerEffect(EditPasswordEffect.class, "[skspeak] (set|edit) password (from|of) channel [with id] %integer% to %string%");
        Skript.registerEffect(AddClientToServerGroupEffect.class, "[skspeak] add client [with id] %integer% to (servergroup|server group) %integer%");
        Skript.registerEffect(PokeClientEffect.class, "[skspeak] poke client [with id] %integer% (with reason|due to) %string%");
        Skript.registerEffect(SendChannelMessageEffect.class, "[skspeak] send [message] %string% to channel [with id] %integer%");
        Skript.registerEffect(SendPrivateMessageEffect.class, "[skspeak] send private message %string% to user [with id] %integer%");
        Skript.registerEffect(AddChannelClientPermissionEffect.class, "[skspeak] addChannelClientPermission %integer% %integer% %string% %integer%");

        Skript.registerCondition(ConnectedCondition.class, "connected");

        Skript.registerEvent("Client Join", ClientConnectSkriptEvent.class, ClientConnectEvent.class, "client join|connect");
        Skript.registerEvent("Client Leave", ClientDisconnectSkriptEvent.class, ClientDisconnectEvent.class, "client leave|disconnect");
        Skript.registerEvent("Channel Create", ChannelCreateSkriptEvent.class, ChannelCreateEvent.class, "channel create");
        Skript.registerEvent("Channel Deleted", ChannelDeletedSkriptEvent.class, ChannelDeletedEvent.class, "channel deleted");
        Skript.registerEvent("Channel Description Edited", ChannelCreateSkriptEvent.class, ChannelCreateEvent.class, "channel description edited");
        Skript.registerEvent("Channel Edited", ChannelEditedSkriptEvent.class, ChannelEditedEvent.class, "channel edited");
        Skript.registerEvent("Channel Moved", ChannelCreateSkriptEvent.class, ChannelCreateEvent.class, "channel moved");
        Skript.registerEvent("Channel Password Changed", ChannelPasswordChangedSkriptEvent.class, ChannelPasswordChangedEvent.class, "channel password changed");
        Skript.registerEvent("Client Moved", ClientMovedSkriptEvent.class, ClientMovedEvent.class, "client moved");
        Skript.registerEvent("Privilege Key Used", PrivilegeKeyUsedSkriptEvent.class, PrivilegeKeyUsedEvent.class, "privilege key used");
        Skript.registerEvent("Server Edited", ServerEditedSkriptEvent.class, ServerEditedEvent.class, "server edited");
        Skript.registerEvent("Text Message", TextMessageSkriptEvent.class, TextMessageEvent.class, "text message");
    }

    @Override
    public void onDisable() {
        if(isConnected()) {
            this.ts3Query.exit();
            setTs3Query(null);
            setTs3Api(null);

            Bukkit.getConsoleSender().sendMessage(PREFIX + "§4Disabling the TeamSpeak Bot");
        }
    }

    public boolean isConnected() {
        if(ts3Api == null) return false;
        if(ts3Query == null) return false;

        return ts3Query.isConnected();
    }

    public static SkSpeak getInstance() {
        return instance;
    }

    public SkriptAddon getAddon() {
        return addon;
    }

    public TS3Api getTs3Api() {
        return ts3Api;
    }

    public void setTs3Api(TS3Api ts3Api) {
        this.ts3Api = ts3Api;
    }

    public TS3Query getTs3Query() {
        return ts3Query;
    }

    public void setTs3Query(TS3Query ts3Query) {
        this.ts3Query = ts3Query;
    }
}
