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
import plugins.tobisch.com.network.guis.CompacterGUI;
import plugins.tobisch.com.network.guis.villager.CompactorVillagerGUI;
import plugins.tobisch.com.network.manager.CompactorManager;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.skills.Skills;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Objects;

public class CompactorVillagerListener implements Listener {
    private final CurrencyManager currencyManager = new CurrencyManager();
    private final CompactorManager compactorManager = new CompactorManager();
    private final Skills skills = new Skills();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }
        event.setCancelled(true);

        Player player = event.getPlayer();

        if (villager.getCustomName() != null && villager.getCustomName().equals("Compactor")) {
            // Open the custom GUI for the player
            CompactorVillagerGUI gui = new CompactorVillagerGUI("Compactor");

            player.openInventory(gui.getInventory());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory topInventory = event.getView().getTopInventory();
        if((topInventory.getHolder() instanceof CompactorVillagerGUI)){
            if(event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getLore() == null){
                event.setCancelled(true);
                return;
            }
            ItemStack clicked = Objects.requireNonNull(event.getCurrentItem()).clone();
            Player player = Bukkit.getPlayer(event.getWhoClicked().getUniqueId());
            event.setCancelled(true);
            assert player != null;
            if(currencyManager.getPlayerCurrency(player)>=100000) {
                if(skills.everySkillHigherThan(player, 10)){
                    if(! (compactorManager.getAccessoryGUI(player).hasItem(CompacterGUI.createEnchanted(clicked.getType(), clicked.getItemMeta().getDisplayName())))) {
                        currencyManager.removeCurrencyFromPlayer(player, 100000);
                        compactorManager.getAccessoryGUI(player).addItem(CompacterGUI.createEnchanted(clicked.getType(), clicked.getItemMeta().getDisplayName()));
                        player.sendMessage("§aYou bought a " + clicked.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(100000) + "€");
                    }else{
                        player.sendMessage("§4You have already bought this compactor");
                    }
                }else{
                    player.sendMessage("§4You need to be stronger");
                }
            }else{
                player.sendMessage("§4You need more money");
            }
        }
    }
}
