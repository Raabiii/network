package plugins.tobisch.com.network.listener.talisman;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.talisman.Haste;
import plugins.tobisch.com.network.talisman.Speed;
import plugins.tobisch.com.network.talisman.Strength;

public class HasteListener implements Listener {

    private static PotionEffect potionEffect;

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        event(event.getPlayer(), event.getItem().getItemStack());
    }

    public static void event(Player p, ItemStack item){
        AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
        HasteListener speedListener = new HasteListener();

        if (p.getInventory().getType() == InventoryType.PLAYER) {
            int speed = speedListener.playerHasspeed(item);
            // Check if the player has blaze rods in their inventory
            if (speed > 0) {
                speedListener.applySpeedEffect((Player) p, speed);
            }
        }
    }

    private int playerHasspeed(ItemStack item) {
        Haste speed = new Haste();

        return speed.compare(item);
    }

    private void applySpeedEffect(Player player, int speed) {
        if (player != null) {
            potionEffect = new PotionEffect(PotionEffectType.FAST_DIGGING, PotionEffect.INFINITE_DURATION, speed, false, false);
            player.addPotionEffect(potionEffect);
        }

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        if(new Haste().compare(event.getItemInHand())>0){
            stopEvent(event.getPlayer(), event.getItemInHand());
        }
    }

    public void stopEvent(Player p, ItemStack item){
        if(new Haste().compare(item)>0){
            p.removePotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, PotionEffect.INFINITE_DURATION, 0, false, false).getType());
        }
    }

    @EventHandler
    public void PlayDropItem(PlayerDropItemEvent event){
        stopEvent(event.getPlayer(), event.getItemDrop().getItemStack());
    }
}
