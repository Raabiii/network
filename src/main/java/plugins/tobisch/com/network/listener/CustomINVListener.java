package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.AccessoryBagGUI;
import plugins.tobisch.com.network.guis.QuiverGUI;
import plugins.tobisch.com.network.talisman.LifeSaver;
import plugins.tobisch.com.network.talisman.Utils;

public class CustomINVListener implements Listener {

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory topInventory = event.getView().getTopInventory();

        if (topInventory.getHolder() instanceof QuiverGUI || topInventory.getHolder() instanceof AccessoryBagGUI) {
            // Allow dragging within the custom GUI
            event.setCancelled(false);
    }}

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory topInventory = event.getView().getTopInventory();
        ItemStack item = event.getCurrentItem();
        if (topInventory.getHolder() instanceof QuiverGUI) {
            event.setCancelled(false);
            if(item != null)
                if (item.getType() != Material.ARROW){
                    event.setCancelled(true);
                }
        }

        if (topInventory.getHolder() instanceof AccessoryBagGUI){
            LifeSaver lf = new LifeSaver();
            event.setCancelled(false);
            if(item != null)
                if ((lf.compare(item)) < 0){
                    event.setCancelled(true);
                }
        }
    }
}