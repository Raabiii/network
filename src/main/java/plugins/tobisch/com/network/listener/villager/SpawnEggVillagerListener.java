package plugins.tobisch.com.network.listener.villager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.villager.SpawnEggVillagerGUI;
import plugins.tobisch.com.network.guis.villager.TalismanVillagerGUI;
import plugins.tobisch.com.network.guis.villager.VillagerGUI;
import plugins.tobisch.com.network.listener.talisman.*;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.skills.Skills;
import plugins.tobisch.com.network.utils.Utils;

public class SpawnEggVillagerListener implements Listener {
    private CurrencyManager currencyManager = new CurrencyManager();
    private Skills skills = new Skills();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }
        event.setCancelled(true);

        Player player = event.getPlayer();

        if (villager.getCustomName() != null && villager.getCustomName().equals("Spawn Egg")) {
            // Open the custom GUI for the player
            SpawnEggVillagerGUI gui = new SpawnEggVillagerGUI("Spawn Egg");

            player.openInventory(gui.getInventory());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory topInventory = event.getView().getTopInventory();
        if((topInventory.getHolder() instanceof SpawnEggVillagerGUI)){
            if(event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getLore() == null){
                event.setCancelled(true);
                return;
            }
            ItemStack clicked = event.getCurrentItem().clone();
            String itemName = clicked.getItemMeta().getDisplayName();
            Player player = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());
            event.setCancelled(true);

            VillagerGUI.enableItem(clicked);

            assert player != null;
            if(currencyManager.getPlayerCurrency(player)>=1000) {
                currencyManager.removeCurrencyFromPlayer(player, 1000);
                player.getInventory().addItem(clicked);
            }else{
                player.sendMessage("§4You need more money");
            }

        }
    }
}
