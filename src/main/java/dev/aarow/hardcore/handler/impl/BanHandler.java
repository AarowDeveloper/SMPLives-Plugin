package dev.aarow.hardcore.handler.impl;

import dev.aarow.hardcore.data.player.Profile;
import dev.aarow.hardcore.handler.BaseHandler;
import dev.aarow.hardcore.utility.chat.CC;
import dev.aarow.hardcore.utility.general.TimeUtility;
import dev.aarow.hardcore.utility.other.Task;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanHandler extends BaseHandler {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);

        if(profile.getDeathBan() > System.currentTimeMillis()){
            event.setKickMessage(CC.translate(plugin.getDefaultConfig().getString("DEATH-BAN.MESSAGE")
                    .replace("<time>", TimeUtility.convertToTimer(profile.getDeathBan() - System.currentTimeMillis()))));
            event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }
}
