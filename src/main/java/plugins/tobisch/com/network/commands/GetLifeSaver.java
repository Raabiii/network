package plugins.tobisch.com.network.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.talisman.*;

public class GetLifeSaver implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        @SuppressWarnings("deprecation")
        OfflinePlayer p = Bukkit.getOfflinePlayer(sender.getName());
        ItemStack enchantedCobblestone = new ItemStack(Material.COBBLESTONE);

        // Apply the enchanted glint effect using BlockStateMeta
        ItemMeta itemMeta = enchantedCobblestone.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true); // Enchant with DURABILITY for the glint effect
        itemMeta.setDisplayName("Enchanted Cobblestone");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        enchantedCobblestone.setItemMeta(itemMeta);
        // Add lore or other attributes if desired
        enchantedCobblestone.setItemMeta(itemMeta);

        ((Player)p).getInventory().addItem(enchantedCobblestone);
        LifeSaver lifeSaver = new LifeSaver();
        Strength strength = new Strength();
        Resistance resistance = new Resistance();
        Speed speed  = new Speed();
        JumpBoost jumpBoost = new JumpBoost();
        Haste haste = new Haste();
        Regeneration regeneration = new Regeneration();
        FireResistance fireResistance = new FireResistance();
        WaterBreathing waterBreathing = new WaterBreathing();
        NightVision nightVision = new NightVision();
        Saturation saturation = new Saturation();
        ExtraHearts extraHearts = new ExtraHearts();
        Coin coin = new Coin();
        NoFallDamage noFallDamage = new NoFallDamage();

        ((Player)p).getInventory().addItem(lifeSaver.createRing());
        ((Player)p).getInventory().addItem(strength.createRing());
        ((Player)p).getInventory().addItem(resistance.createRing());
        ((Player)p).getInventory().addItem(speed.createRing());
        ((Player)p).getInventory().addItem(jumpBoost.createRing());
        ((Player)p).getInventory().addItem(haste.createRing());
        ((Player)p).getInventory().addItem(regeneration.createRing());
        ((Player)p).getInventory().addItem(fireResistance.createRing());
        ((Player)p).getInventory().addItem(nightVision.createRing());
        ((Player)p).getInventory().addItem(saturation.createRing());
        ((Player)p).getInventory().addItem(extraHearts.createRing());
        ((Player)p).getInventory().addItem(coin.createRing());
        ((Player)p).getInventory().addItem(noFallDamage.createRing());
        ((Player)p).getInventory().addItem(waterBreathing.createRing());

        return false;
    }
}
