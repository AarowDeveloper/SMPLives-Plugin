package dev.aarow.hardcore.utility.general;

import de.tr7zw.nbtapi.NBTItem;
import dev.aarow.hardcore.HardcorePlugin;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class LivesUtility {

    public static ItemStack getLivesItem(int amount){
        ItemBuilder itemBuilder = new ItemBuilder(ItemBuilder.getFromConfig(HardcorePlugin.INSTANCE.getDefaultConfig(), "LIFE-ITEM"));

        itemBuilder.setName(itemBuilder.getName().replace("<lives>", String.valueOf(amount)));

        for(int i = 0; i < itemBuilder.getLore().size(); i++){
            itemBuilder.addLoreLine(itemBuilder.getLore().get(i).replace("<lives>", String.valueOf(amount)), i);
        }

        NBTItem nbtItem = new NBTItem(itemBuilder.toItemStack());

        nbtItem.setInteger("livesAmount", amount);
        nbtItem.setString("identifierUUID", UUID.randomUUID().toString());

        return nbtItem.getItem();
    }
}
