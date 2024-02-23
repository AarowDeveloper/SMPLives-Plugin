package dev.aarow.hardcore.commands.impl;

import dev.aarow.hardcore.commands.BaseCommand;
import dev.aarow.hardcore.commands.CommandInfo;
import dev.aarow.hardcore.data.player.Profile;
import dev.aarow.hardcore.utility.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandInfo(name = "lives", playerOnly = true)
public class LivesCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        if(args.length == 1){
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

            Profile profile = plugin.getProfileManager().get(offlinePlayer);

            if(profile == null){
                player.sendMessage(CC.translate("&cThat player is not in our database."));
                return;
            }

            player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIVES-MESSAGE.TARGET")
                    .replace("<player>", offlinePlayer.getName())
                    .replace("<lives>", String.valueOf(profile.getLives()))));
            return;
        }

        Profile profile = plugin.getProfileManager().get(player);

        player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIVES-MESSAGE.PLAYER")
                .replace("<lives>", String.valueOf(profile.getLives()))));
    }
}
