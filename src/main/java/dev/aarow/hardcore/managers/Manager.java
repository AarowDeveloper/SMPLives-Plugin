package dev.aarow.hardcore.managers;

import dev.aarow.hardcore.HardcorePlugin;
import dev.aarow.hardcore.utility.other.Task;

public abstract class Manager {

    public HardcorePlugin plugin = HardcorePlugin.INSTANCE;

    public abstract void setup();

    public Manager(){
        Task.newThread(this::setup);
    }
}
