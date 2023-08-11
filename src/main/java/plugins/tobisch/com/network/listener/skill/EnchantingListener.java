package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.skills.Enchanting;
import plugins.tobisch.com.network.skills.Mining;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class EnchantingListener implements Listener {
    private Enchanting enchanting;

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if( !(Utils.isPlayerInMultiverseWorld(event.getEnchanter(), "stoneblock"))){
            return;
        }
        int amount = 1;
        amount += enchanting.getCurrentLevel(event.getEnchanter())/10;
        enchanting.addAmount(event.getEnchanter(), amount);
    }

    public EnchantingListener(Enchanting enchanting){
        this.enchanting = enchanting;

    }
}
