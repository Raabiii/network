package plugins.tobisch.com.network.listener.talisman;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.talisman.Strength;

public class StrengthListener implements Listener {

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        event(event.getPlayer(), event.getItem().getItemStack());
    }

    public static void event(Player p, ItemStack item){
        AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
        StrengthListener strengthListener = new StrengthListener();

        if (p.getInventory().getType() == InventoryType.PLAYER) {
            Bukkit.getConsoleSender().sendMessage("yes");
            int strength = strengthListener.playerHasStrength(item);
            // Check if the player has blaze rods in their inventory
            Bukkit.getConsoleSender().sendMessage("strength " + strength);
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
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, PotionEffect.INFINITE_DURATION, strength, false, false));
        }
    }
}
