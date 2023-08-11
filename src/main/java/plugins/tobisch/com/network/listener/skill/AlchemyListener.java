package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.skills.Alchemy;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Objects;

public class AlchemyListener implements Listener {
    private Alchemy alchemy;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof org.bukkit.block.BrewingStand) {
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                if( !(Utils.isPlayerInMultiverseWorld(player, "netherblock"))){
                    return;
                }
                int clickedSlot = event.getRawSlot();

                if (clickedSlot >= 0 && clickedSlot <= 2) {
                    if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.POTION) {
                        PotionMeta potionMeta = (PotionMeta) event.getCurrentItem().getItemMeta();
                        PotionType p = potionMeta.getBasePotionData().getType();
                        if(p == PotionType.AWKWARD)
                            return;
                        int amount = 1;
                        amount += alchemy.getCurrentLevel(player)/10;
                        alchemy.addAmount(player, amount);

                        ItemStack potionToAdd = new ItemStack(Material.POTION);
                        PotionMeta addedPotionMeta = (PotionMeta) potionToAdd.getItemMeta();
                        addedPotionMeta.setBasePotionData(potionMeta.getBasePotionData());
                        potionToAdd.setItemMeta(addedPotionMeta);

                        if (event.getWhoClicked().getInventory().firstEmpty() == -1) {
                            // Drop the potion if inventory is full
                            event.getWhoClicked().getWorld().dropItemNaturally(event.getWhoClicked().getLocation(), potionToAdd);
                        } else {
                            // Add the potion to the player's inventory
                            event.getWhoClicked().getInventory().addItem(potionToAdd);
                        }
                        event.setCurrentItem(null);
                    }
                }

                if(clickedSlot >= 5 && clickedSlot <= 40){
                    if(event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.POTION){
                        PotionMeta potionMeta = (PotionMeta) event.getCurrentItem().getItemMeta();
                        PotionType p = potionMeta.getBasePotionData().getType();
                        if(p != PotionType.AWKWARD){
                            event.setCancelled(true);
                        }
                    }
                }

            }
        }
    }


    public AlchemyListener(Alchemy alchemy){
        this.alchemy = alchemy;

    }
}
