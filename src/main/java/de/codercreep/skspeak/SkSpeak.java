package de.codercreep.skspeak;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.codercreep.skspeak.elements.effects.BroadcastEffect;
import de.codercreep.skspeak.elements.effects.ConnectEffect;
import de.codercreep.skspeak.elements.effects.DisconnectEffect;
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
            addon.loadClasses("de.codercreep.skspeak", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Skript.registerEffect(ConnectEffect.class, "[skspeak] connect to %string% with user %string% and (login|name) %string%, %string% on query port %integer%");
        Skript.registerEffect(DisconnectEffect.class, "[skspeak] disconnect");
        Skript.registerEffect(BroadcastEffect.class, "[skspeak] broadcastMessage %string%");
    }

    @Override
    public void onDisable() {
        if(isConnected()) {
            ts3Query.exit();
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
