package dev.aarow.hardcore;

import dev.aarow.hardcore.commands.impl.LivesCommand;
import dev.aarow.hardcore.commands.impl.WithdrawCommand;
import dev.aarow.hardcore.handler.impl.BanHandler;
import dev.aarow.hardcore.handler.impl.LivesHandler;
import dev.aarow.hardcore.handler.impl.ProfileHandler;
import dev.aarow.hardcore.managers.impl.ProfileManager;
import dev.aarow.hardcore.utility.general.ConfigFile;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class HardcorePlugin extends JavaPlugin {

    public static HardcorePlugin INSTANCE;

    private ConfigFile defaultConfig;
    private ConfigFile profileDataConfig;

    private ProfileManager profileManager;

    @Override
    public void onEnable(){
        INSTANCE = this;

        this.registerConfiguration();

        this.profileManager = new ProfileManager();

        this.registerCommands();
        this.registerHandlers();
    }

    protected void registerConfiguration(){
        this.defaultConfig = new ConfigFile("config.yml");
        this.profileDataConfig = new ConfigFile("profile_data.yml");
    }

    protected void registerCommands(){
        new LivesCommand();
        new WithdrawCommand();
    }

    protected void registerHandlers(){
        new ProfileHandler();
        new BanHandler();
        new LivesHandler();
    }
}
