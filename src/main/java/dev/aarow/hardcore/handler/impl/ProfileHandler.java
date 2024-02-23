package dev.aarow.hardcore.handler.impl;

import dev.aarow.hardcore.handler.BaseHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

public class ProfileHandler extends BaseHandler {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        plugin.getProfileManager().get(event.getPlayer());
    }
}
