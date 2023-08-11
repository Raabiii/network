package plugins.tobisch.com.network.listener;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.manager.CurrencyManager;

import java.util.Objects;

public class PickUpCoins implements Listener {

    private CurrencyManager cm;

    @EventHandler
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            ItemStack item = event.getItem().getItemStack();

            // Check if the item is an enchanted coin
            if (item.getType() == Material.DIAMOND_BLOCK && item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).hasDisplayName() &&
                    item.getItemMeta().getDisplayName().equals("§bEnchanted Coin")) {
                // Cancel the event to prevent picking up the item
                player.getInventory().removeItem(item);
                cm.addCurrencyToPlayer(player, event.getItem().getItemStack().getAmount());
                event.getItem().remove();
                event.setCancelled(true);
            }
        }
    }

    public PickUpCoins(CurrencyManager cm){
        this.cm = cm;
    }
}
