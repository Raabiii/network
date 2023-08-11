package plugins.tobisch.com.network.listener;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.Main;

import java.util.*;

public class CoinDropListener implements Listener {
    private double dropChance;
    private Main plugin;

    @EventHandler
    public void mobDeath(EntityDeathEvent event) {
        if (event.getEntityType() == EntityType.PLAYER || event.getEntity().getKiller() == null) {
            return;
        }

            Mob mob = (Mob) event.getEntity();
            double maxMobHealth = Objects.requireNonNull(mob.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue();

            List<ItemStack> items = new LinkedList<>();

            for(int i = 0;i< dropChance+maxMobHealth/100;i++){
                ItemStack diamondBlock = new ItemStack(Material.DIAMOND_BLOCK);
                ItemMeta itemMeta = diamondBlock.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName("§bEnchanted Coin"); // The §b adds a cyan color to the name
                diamondBlock.setItemMeta(itemMeta);

                items.add(diamondBlock);
            }

            if (Math.random() < dropChance) {
                event.getDrops().addAll(items);
            }
        ;
    }

    public void setDropChance(double dropChance) {
        this.dropChance = dropChance;
    }

    public CoinDropListener(Main plugin, double dropChance){
        this.dropChance = dropChance;
    }
}
