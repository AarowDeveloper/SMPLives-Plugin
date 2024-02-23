package dev.aarow.hardcore.utility.general;

import dev.aarow.hardcore.utility.chat.CC;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemBuilder {

    private ItemStack is;

    public ItemBuilder(Material m) {
        this(m, 1);
    }

    public ItemBuilder(ItemStack is) {
        this.is = is;
    }

    public ItemBuilder(Material m, int amount) {
        is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, byte durability) {
        is = new ItemStack(m, amount, durability);
    }

    public static ItemStack getFromConfig(ConfigFile configFile, String path) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.matchMaterial(configFile.getString(path + ".MATERIAL")));

        if (configFile.getInt(path + ".DATA") != 0) itemBuilder = new ItemBuilder(Material.matchMaterial(configFile.getString(path + ".MATERIAL")), 1, (short) configFile.getInt(path + ".DATA"));

        if (configFile.getString(path + ".NAME") != null) itemBuilder.setName(configFile.getString(path + ".NAME"));
        if (configFile.getString(path + ".LORE") != null) itemBuilder.setLore(configFile.getStringList(path + ".LORE"));
        if (configFile.isConfigurationSection(path + ".ENCHANTMENTS"))
            for (String key : configFile.getConfigurationSection(path + ".ENCHANTMENTS").getKeys(false))
                itemBuilder.addEnchantment(Enchantment.getByName(key), configFile.getInt(path + ".ENCHANTMENTS." + path));
        int amount = configFile.getInt(path + ".AMOUNT");
        if (amount > 0)
            itemBuilder.setAmount(amount);

        return itemBuilder.toItemStack();
    }

    public ItemBuilder(Material m, int amount, short data) {
        is = new ItemStack(m, amount, data);
    }

    public ItemBuilder clone() {
        return new ItemBuilder(is.clone());
    }

    public ItemBuilder setType(Material m) {
        is.setType(m);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        is.setAmount(amount);
        return this;
    }

    public ItemBuilder setDurability(short dur) {
        is.setDurability(dur);
        return this;
    }

    public ItemBuilder setDurability(int dur) {
        is.setDurability((short) dur);
        return this;
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(CC.translate(name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setUnColoredName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setUnTranslatedName(String name) {
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment ench, int level) {
        if (level < 1) {
            return this;
        }
        is.addUnsafeEnchantment(ench, level);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment ench) {
        is.removeEnchantment(ench);
        return this;
    }

    public ItemBuilder setPlayerHead(UUID uuid) {
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        is.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {
        if (level < 1) {
            return this;
        }
        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment ench, int level) {
        if (level < 1) {
            return this;
        }
        is.addEnchantment(ench, level);
        return this;
    }

    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        is.addEnchantments(enchantments);
        return this;
    }

    public ItemBuilder setInfinityDurability() {
        is.setDurability(Short.MAX_VALUE);
        return this;
    }


    public ItemBuilder setLore(List<String> lore) {
        ItemMeta im = is.getItemMeta();
        im.setLore(CC.translate(lore));
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (!lore.contains(line))
            return this;
        lore.remove(line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder removeLoreLine(int index) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        if (index < 0 || index > lore.size())
            return this;
        lore.remove(index);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore())
            lore = new ArrayList<>(im.getLore());
        lore.add(CC.translate(line));
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public String getName(){
        ItemMeta im = is.getItemMeta();
        return im.getDisplayName();
    }

    public List<String> getLore() {
        ItemMeta im = is.getItemMeta();
        return im.getLore();
    }

    public ItemBuilder addUnTranslatedLoreLine(String line) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (im.hasLore())
            lore = new ArrayList<>(im.getLore());
        lore.add(ChatColor.translateAlternateColorCodes('&', line));
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addLoreLine(String line, int pos) {
        ItemMeta im = is.getItemMeta();
        List<String> lore = new ArrayList<>(im.getLore());
        lore.set(pos, line);
        im.setLore(lore);
        is.setItemMeta(im);
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemBuilder setDyeColor(DyeColor color) {
        this.is.setDurability(color.getDyeData());
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
            im.setColor(color);
            is.setItemMeta(im);
        } catch (ClassCastException expected) {
        }
        return this;
    }

    public ItemStack toItemStack() {
        return is;
    }

}