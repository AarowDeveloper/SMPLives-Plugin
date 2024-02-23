package dev.aarow.hardcore.data.player;

import dev.aarow.hardcore.HardcorePlugin;
import dev.aarow.hardcore.utility.chat.CC;
import dev.aarow.hardcore.utility.general.TimeUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Profile {

    private HardcorePlugin plugin = HardcorePlugin.INSTANCE;

    private UUID uuid;
    @Setter private int lives;
    private long deathBan;

    private Map<UUID, Long> cooldown = new HashMap<>();

    public Profile(UUID uuid){
        this.uuid = uuid;

        this.register();

        this.lives = plugin.getProfileDataConfig().getInt("DATA." + uuid.toString() + ".LIVES");
        this.deathBan = plugin.getProfileDataConfig().getLong("DATA." + uuid.toString() + ".DEATH-BAN");

        Bukkit.getConsoleSender().sendMessage("" + deathBan);
    }

    public void register(){
        if(plugin.getProfileDataConfig().getConfigurationSection("DATA." + uuid.toString()) != null) return;

        plugin.getProfileDataConfig().set("DATA." + uuid.toString() + ".LIVES", plugin.getDefaultConfig().getInt("LIVES.DEFAULT"));
        plugin.getProfileDataConfig().set("DATA." + uuid.toString() + ".DEATH-BAN", 0);

        plugin.getProfileDataConfig().save();
    }

    public void save(){
        plugin.getProfileDataConfig().set("DATA." + uuid.toString() + ".LIVES", lives);
        plugin.getProfileDataConfig().set("DATA." + uuid.toString() + ".DEATH-BAN", deathBan);

        plugin.getProfileDataConfig().save();
    }

    public void removeLives(int lives){
        this.lives-=lives;

        if(this.lives == 0){
            this.deathBan = System.currentTimeMillis() + TimeUtility.convertStringToMillis(plugin.getDefaultConfig().getString("DEATH-BAN.TIME"));
            this.lives = plugin.getDefaultConfig().getInt("LIVES.REVIVED");

            Bukkit.getPlayer(uuid).kickPlayer(CC.translate(plugin.getDefaultConfig().getString("DEATH-BAN.MESSAGE")
                    .replace("<time>", TimeUtility.convertToTimer(deathBan - System.currentTimeMillis()))));
        }

        this.save();
    }
}
