package dev.aarow.hardcore.managers.impl;

import dev.aarow.hardcore.data.player.Profile;
import dev.aarow.hardcore.managers.Manager;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.Map;

public class ProfileManager extends Manager {

    private Map<UUID, Profile> profiles = new HashMap<>();

    @Override
    public void setup() {
        // LOAD USERS
        if(plugin.getProfileDataConfig().getConfigurationSection("DATA") == null) return;

        plugin.getProfileDataConfig().getConfigurationSection("DATA").getKeys(false).forEach(stringUUID -> {
            profiles.put(UUID.fromString(stringUUID), new Profile(UUID.fromString(stringUUID)));
        });
    }

    public Profile get(Player player){
        this.profiles.putIfAbsent(player.getUniqueId(), new Profile(player.getUniqueId()));

        return this.profiles.get(player.getUniqueId());
    }

    public Profile get(OfflinePlayer offlinePlayer){
        return this.profiles.get(offlinePlayer.getUniqueId());
    }

    public Profile get(UUID uuid){
        this.profiles.putIfAbsent(uuid, new Profile(uuid));

        return this.profiles.get(uuid);
    }
}
