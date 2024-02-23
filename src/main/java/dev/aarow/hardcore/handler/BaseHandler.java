package dev.aarow.hardcore.handler;

import dev.aarow.hardcore.HardcorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BaseHandler implements Listener {

    public HardcorePlugin plugin = HardcorePlugin.INSTANCE;

    public BaseHandler(){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}
