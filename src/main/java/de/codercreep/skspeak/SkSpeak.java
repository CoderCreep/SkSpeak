package de.codercreep.skspeak;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.codercreep.skspeak.elements.EffConnect;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkSpeak extends JavaPlugin {

    public static final String PREFIX = "§7[§6SkSpeak§7] §r";
    private SkriptAddon skriptAddon;
    private TS3Api ts3Api;
    private TS3Query ts3Query;
    private static SkSpeak instance;

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

        this.skriptAddon = Skript.registerAddon(instance);

        try {
            this.skriptAddon.loadClasses("de.codercreep.skspeak", "elements");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Skript.registerEffect(EffConnect.class, "[skspeak] connect (to|with) %text% with user %text% and password %text% with name %text% at port %integer%");
    }

    public static SkSpeak getInstance() {
        return instance;
    }

    public SkriptAddon getSkriptAddon() {
        return skriptAddon;
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
