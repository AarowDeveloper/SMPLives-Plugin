package dev.aarow.hardcore.utility.other;

import dev.aarow.hardcore.HardcorePlugin;
import org.bukkit.Bukkit;

public class Task {

    public static void newThread(Caller caller) {
        Bukkit.getScheduler().runTaskLater(HardcorePlugin.INSTANCE, caller::call, 1L);
    }

    public interface Caller {
        void call();
    }
}