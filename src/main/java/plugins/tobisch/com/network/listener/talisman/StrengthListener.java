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
import plugins.tobisch.com.network.talisman.Speed;
import plugins.tobisch.com.network.talisman.Strength;

public class StrengthListener implements Listener {

    private static PotionEffect potionEffect;

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        event(event.getPlayer(), event.getItem().getItemStack());
    }

    public static void event(Player p, ItemStack item){
        AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
        StrengthListener strengthListener = new StrengthListener();

        if (p.getInventory().getType() == InventoryType.PLAYER) {
            int strength = strengthListener.playerHasStrength(item);
            // Check if the player has blaze rods in their inventory
            if (strength > 0) {
                strengthListener.applyStrengthEffect((Player) p, strength);
            }
        }
    }

    private int playerHasStrength(ItemStack item) {
        Strength strength = new Strength();

        return strength.compare(item);
    }

    private void applyStrengthEffect(Player player, int strength) {
        if (player != null) {
            potionEffect = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, strength, false, false);
            player.addPotionEffect(potionEffect);
        }

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        if(new Strength().compare(event.getItemInHand())>0){
            stopEvent(event.getPlayer(), event.getItemInHand());
        }
    }

    public void stopEvent(Player p, ItemStack item){
        if(new Strength().compare(item)>0){
            p.removePotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, 0, false, false).getType());
        }
    }

    @EventHandler
    public void PlayDropItem(PlayerDropItemEvent event){
        Player p = event.getPlayer();
        ItemStack item = event.getItemDrop().getItemStack();

        if(new Strength().compare(item)>0){
            p.removePotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, 0, false, false).getType());
        }
    }
}
