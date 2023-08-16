package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.manager.CompactorManager;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Objects;

public class CompacterListener implements Listener {
    private static CompactorManager compactorManager = new CompactorManager();

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event){
        Inventory inv = event.getPlayer().getInventory();
        ItemStack item = event.getItem().getItemStack();
        Material type = item.getType();
        Player p = event.getPlayer();
        String name = "";
        boolean active = false;

        switch (type) {
            case COBBLESTONE -> {
                name = "Cobblestone";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(9)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case ACACIA_LOG -> {
                name = "Acacia Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case BIRCH_LOG -> {
                name = "Birch Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case CHERRY_LOG -> {
                name = "Cherry Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case DARK_OAK_LOG -> {
                name = "Dark Oak Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case JUNGLE_LOG -> {
                name = "Jungle Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case MANGROVE_LOG -> {
                name = "Mangrove Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case OAK_LOG -> {
                name = "Oak Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case SPRUCE_LOG -> {
                name = "Spruce Log";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(10)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case WHEAT -> {
                name = "Wheat";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(11)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case CARROT -> {
                name = "Carrot";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(12)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case POTATO -> {
                name = "Potato";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(13)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case NETHER_WART -> {
                name = "Nether Wart";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(14)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case PUMPKIN -> {
                name = "Pumpkin";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(15)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case BEETROOT -> {
                name = "Beetroot";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(16)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            case SWEET_BERRIES -> {
                name = "Sweet Berries";
                if(Objects.requireNonNull(Objects.requireNonNull(Objects.requireNonNull(compactorManager.getAccessoryGUI(p).getInventory().getItem(17)).getItemMeta()).getLore()).contains("§aActivate")) active = true;
            }
            default -> {
                return;
            }
        }

        if(!active) return;

        if(checkIfPlayerHas2Stacks(item, inv)){
            removeItems(item, p);
            p.getInventory().addItem(Utils.createEnchanted(item.getType(), "§aEnchanted " + name));
        }

    }

    private boolean checkIfPlayerHas2Stacks(ItemStack item, Inventory inv){
        int number = 0;
        for(ItemStack element: inv.getContents()){
            if(element != null && element.getType() == item.getType() && !element.getItemMeta().getDisplayName().contains("§4Enchanted")){
                number+= element.getAmount();
            }
        }

        Bukkit.getConsoleSender().sendMessage(number + "");

        return number >= 128;
    }

    private void removeItems(ItemStack item, Player p){
        int itemsRemoved = 0;

        for (ItemStack element: p.getInventory().getContents()){
            if (element !=null && item.getType() == element.getType() && !(element.getItemMeta().getDisplayName().contains("Enchanted")) && itemsRemoved <=128){
                itemsRemoved+= element.getAmount();
                p.getInventory().remove(element);
            }
        }
    }
}
