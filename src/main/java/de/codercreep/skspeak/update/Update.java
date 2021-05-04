package de.codercreep.skspeak.update;

import de.codercreep.skspeak.SkSpeak;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class Update {

    private final int resourceId;

    public Update(int resourceId) {
        this.resourceId = resourceId;
    }

    public void getVersion(Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(SkSpeak.getInstance(), () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext())
                    consumer.accept(scanner.next());
            } catch (IOException exception) {
                Bukkit.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }
}
