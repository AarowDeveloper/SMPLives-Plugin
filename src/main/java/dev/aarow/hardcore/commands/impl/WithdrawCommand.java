package dev.aarow.hardcore.commands.impl;

import dev.aarow.hardcore.commands.BaseCommand;
import dev.aarow.hardcore.commands.CommandInfo;
import dev.aarow.hardcore.data.player.Profile;
import dev.aarow.hardcore.utility.chat.CC;
import dev.aarow.hardcore.utility.general.GeneralUtility;
import dev.aarow.hardcore.utility.general.LivesUtility;
import dev.aarow.hardcore.utility.general.PlayerUtility;
import org.bukkit.entity.Player;

@CommandInfo(name = "withdraw", playerOnly = true)
public class WithdrawCommand extends BaseCommand {

    @Override
    public void execute(Player player, String[] args) {
        Profile profile = plugin.getProfileManager().get(player);

        if(args.length != 1){
            player.sendMessage(CC.translate("&c[Correct Usage] /withdraw <livesAmount>"));
            return;
        }
        if(!GeneralUtility.isNumber(args[0])){
            player.sendMessage(CC.translate("&c[Correct Usage] /withdraw <livesAmount>"));
            return;
        }

        int amount = Integer.parseInt(args[0]);

        if(profile.getLives() - amount <= 0){
            player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIFE-ITEM.MESSAGES.WITHDRAW-FAIL")));
            return;
        }

        profile.removeLives(amount);

        PlayerUtility.addItem(player, LivesUtility.getLivesItem(amount));

        player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIFE-ITEM.MESSAGES.WITHDRAW-SUCCESS")
                .replace("<lives>", String.valueOf(amount))));
    }
}
