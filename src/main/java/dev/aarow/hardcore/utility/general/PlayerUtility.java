package dev.aarow.hardcore.utility.general;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtility {

    public static void addItem(Player player, ItemStack itemStack){
        if(player.getInventory().firstEmpty() == -1){
            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            return;
        }

        player.getInventory().addItem(itemStack);
    }
}
