package dev.aarow.hardcore.handler.impl;

import de.tr7zw.nbtapi.NBTItem;
import dev.aarow.hardcore.data.player.Profile;
import dev.aarow.hardcore.handler.BaseHandler;
import dev.aarow.hardcore.utility.chat.CC;
import dev.aarow.hardcore.utility.general.LivesUtility;
import dev.aarow.hardcore.utility.general.PlayerUtility;
import dev.aarow.hardcore.utility.general.TimeUtility;
import dev.aarow.hardcore.utility.other.Task;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LivesHandler extends BaseHandler {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(!event.getAction().name().contains("RIGHT")) return;
        if(event.getItem() == null) return;

        Player player = event.getPlayer();
        Profile profile = plugin.getProfileManager().get(player);
        NBTItem nbtItem = new NBTItem(event.getItem());

        if(!nbtItem.hasKey("livesAmount")) return;

        int beforeCurrentLives = profile.getLives();
        int max = plugin.getDefaultConfig().getInt("LIVES.MAX");
        int amount = nbtItem.getInteger("livesAmount");

        if(profile.getLives() == max){
            player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIFE-ITEM.MESSAGES.MAX-LIVES")));
            return;
        }


        if(profile.getLives() + amount > max){
            int left = (profile.getLives() + amount) - max;

            profile.setLives(max);

            Task.newThread(() -> {
                player.getInventory().setItem(player.getInventory().getHeldItemSlot(), LivesUtility.getLivesItem(left));
                player.updateInventory();
            });
            player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIFE-ITEM.MESSAGES.SUCCESS")
                    .replace("<lives>", String.valueOf(max - beforeCurrentLives))));

            profile.save();
            return;
        }

        Task.newThread(() -> {
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
            player.updateInventory();
        });

        profile.setLives(beforeCurrentLives+amount);

        player.sendMessage(CC.translate(plugin.getDefaultConfig().getString("LIFE-ITEM.MESSAGES.SUCCESS")
                .replace("<lives>", String.valueOf(amount))));

        profile.save();
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event){
        // ADD PLAYER CHECK
        if(event.getEntity().getType() != EntityType.PLAYER) return;

        Player victim = (Player) event.getEntity();
        Profile victimProfile = plugin.getProfileManager().get(victim);

        victimProfile.removeLives(1);

        victim.sendMessage(CC.translate(plugin.getDefaultConfig().getString("DEATH-MESSAGE")
                .replace("<lives>", String.valueOf(victimProfile.getLives()))));

        if(event.getEntity().getKiller() == null) return;

        Player killer = event.getEntity().getKiller();

        if(killer.getAddress().getAddress().equals(victim.getAddress().getAddress())) return;

        Profile killerProfile = plugin.getProfileManager().get(killer);

        if(killerProfile.getCooldown().get(victim.getUniqueId()) != null){
            if(System.currentTimeMillis() < killerProfile.getCooldown().get(victim.getUniqueId())) return;
        }

        killerProfile.getCooldown().put(victim.getUniqueId(), System.currentTimeMillis() + TimeUtility.convertStringToMillis(plugin.getDefaultConfig().getString("KILL-LIFE-COOLDOWN")));

        PlayerUtility.addItem(killer, LivesUtility.getLivesItem(1));
    }
}
