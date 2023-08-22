package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.CompacterGUI;
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
        int amount = 128;
        CompacterGUI gui = compactorManager.getAccessoryGUI(p);

        switch (type) {
            case COBBLESTONE -> {
                name = "Cobblestone";
            }
            case ACACIA_LOG -> {
                name = "Acacia Log";
            }
            case BIRCH_LOG -> {
                name = "Birch Log";
            }
            case CHERRY_LOG -> {
                name = "Cherry Log";
            }
            case DARK_OAK_LOG -> {
                name = "Dark Oak Log";
            }
            case JUNGLE_LOG -> {
                name = "Jungle Log";
            }
            case MANGROVE_LOG -> {
                name = "Mangrove Log";
            }
            case OAK_LOG -> {
                name = "Oak Log";
            }
            case SPRUCE_LOG -> {
                name = "Spruce Log";
            }
            case WHEAT -> {
                name = "Wheat";
            }
            case CARROT -> {
                name = "Carrot";
            }
            case POTATO -> {
                name = "Potato";
            }
            case NETHER_WART -> {
                name = "Nether Wart";
            }
            case PUMPKIN -> {
                name = "Pumpkin";
            }
            case BEETROOT -> {
                name = "Beetroot";
            }
            case SWEET_BERRIES -> {
                name = "Sweet Berries";
            }
            case BLAZE_POWDER -> {
                name = "Blaze Powder";
            }
            case BLAZE_ROD -> {
                amount = 64;
                name = "Blaze Powder";
            }
            case IRON_INGOT -> {
                name = "Iron Ingot";
            }
            case SUGAR_CANE -> {
                name = "Sugar Cane";
            }
            case GOLD_INGOT -> {
                name = "Gold Ingot";
            }
            case RABBIT_FOOT -> {
                name = "Rabbit Foot";
            }
            case FEATHER -> {
                name = "Feather";
            }
            case GHAST_TEAR -> {
                name = "Ghast Tear";
            }
            case MAGMA_CREAM -> {
                name = "Magma Cream";
            }
            case PUFFERFISH -> {
                name = "Pufferfisch";
            }
            case MELON_SLICE -> {
                name = "Melon Slice";
            }

            default -> {
                return;
            }
        }

        if(gui.isActivated(name.toLowerCase())) active = true;

        if(!active) return;

        if(checkIfPlayerHasItem(item, inv, amount)){
            removeItems(item, p, amount);
            if(item.getType() != Material.BLAZE_ROD)
                p.getInventory().addItem(Utils.createEnchanted(item.getType(), "§aEnchanted " + name));
            else
                p.getInventory().addItem(Utils.createEnchanted(Material.BLAZE_POWDER, "§aEnchanted " + name));

        }

    }

    private boolean checkIfPlayerHasItem(ItemStack item, Inventory inv, int amount){
        int number = 0;
        Bukkit.getConsoleSender().sendMessage(item.getType().name());
        for(ItemStack element: inv.getContents()){
            if(element ==null) continue;
            if(element != null && element.getType() == item.getType() && !(element.getItemMeta().getDisplayName().contains("Enchanted"))){
                number+= element.getAmount();
            }
        }


        return number >= amount;
    }

    private void removeItems(ItemStack item, Player p, int amount){
        int itemsRemoved = 0;

        for (ItemStack element :  p.getInventory().getContents()) {
            if (element !=null && item.getType() == element.getType() && !(element.getItemMeta().getDisplayName().contains("Enchanted"))) {
                int count = element.getAmount();
                if (count > (amount - itemsRemoved)) {
                    element.setAmount(count - (amount - itemsRemoved));
                    return;
                } else {
                    itemsRemoved += count;
                    element.setAmount(0);
                }
            }
        }
    }
}
