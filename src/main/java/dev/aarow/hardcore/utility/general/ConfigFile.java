package dev.aarow.hardcore.utility.general;

import dev.aarow.hardcore.HardcorePlugin;
import dev.aarow.hardcore.utility.chat.CC;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigFile extends YamlConfiguration {

    @Getter
    private File file;
    private JavaPlugin plugin = HardcorePlugin.INSTANCE;
    private String name;

    public ConfigFile(String name) {
        this.file = new File(plugin.getDataFolder(), name);
        this.name = name;

        loadConfig();
    }

    public void load() {
        loadConfig();
    }

    private void loadConfig() {
        if (!this.file.exists()) {
            // Only save the resource if the file doesn't exist
            plugin.saveResource(name, false);
        }

        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            this.save(this.file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload(){
        try {
            this.load(this.file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getInt(String path) {
        return super.getInt(path, 0);
    }

    @Override
    public double getDouble(String path) {
        return super.getDouble(path, 0.0);
    }

    @Override
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }

    @Override
    public List<String> getStringList(String path) {
        return super.getStringList(path).stream().map(CC::translate).collect(Collectors.toList());
    }

    public List<String> getStringList(String path, boolean ignored) {
        if (!super.contains(path)) return null;
        return super.getStringList(path).stream().map(CC::translate).collect(Collectors.toList());
    }
}
