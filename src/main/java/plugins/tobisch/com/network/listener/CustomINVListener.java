package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.guis.AccessoryBagGUI;
import plugins.tobisch.com.network.guis.CompacterGUI;
import plugins.tobisch.com.network.guis.QuiverGUI;
import plugins.tobisch.com.network.guis.UpgradeGUI;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.talisman.Talisman;
import plugins.tobisch.com.network.talisman.Utils;

import java.util.List;
import java.util.Objects;

public class CustomINVListener implements Listener {
    private final AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
    private final CurrencyManager currencyManager = new CurrencyManager();

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory topInventory = event.getView().getTopInventory();

        if (topInventory.getHolder() instanceof QuiverGUI || topInventory.getHolder() instanceof UpgradeGUI || topInventory.getHolder() instanceof AccessoryBagGUI) {
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
            event.setCancelled(false);
            if(item != null)
                if (!(item instanceof Talisman)){
                    event.setCancelled(true);
                }
        }

        if (topInventory.getHolder() instanceof CompacterGUI){
            int id = event.getRawSlot();
            Bukkit.getConsoleSender().sendMessage(id + "");
            if(id<9 || id>35) return;
            event.setCancelled(false);
            if(topInventory.getItem(id) == null)return;
            ItemMeta meta = Objects.requireNonNull(topInventory.getItem(id)).getItemMeta();
            if( Objects.requireNonNull(Objects.requireNonNull(topInventory.getItem(id)).getItemMeta()).hasEnchant(Enchantment.DURABILITY)){
                assert meta != null;
                meta.removeEnchant(Enchantment.DURABILITY);
                meta.setLore(List.of("§4Deactivate"));
            }else {
                assert meta != null;
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.setLore(List.of("§aActivate"));
            }
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            topInventory.getItem(id).setItemMeta(meta);

            if(item != null)
                if (!(item instanceof Talisman)){
                    event.setCancelled(true);
                }
        }

        if (topInventory.getHolder() instanceof UpgradeGUI){
            event.setCancelled(false);
            if (item != null)
                if(item.getType() == Material.ANVIL){
                    ItemStack oldItem = topInventory.getItem(21);
                    if (oldItem == null) return;
                    HumanEntity player = event.getWhoClicked();

                    if (oldItem.getItemMeta() == null) return;

                    if(oldItem.getItemMeta().getDisplayName().contains("Talisman")){
                        if(currencyManager.getPlayerCurrency(player.getUniqueId()) >= 1000000) {
                            topInventory.setItem(23, Utils.upgrade(oldItem, Utils.getStage(oldItem)));
                            topInventory.setItem(21, null);
                            currencyManager.removeCurrencyFromPlayer(player.getUniqueId(), 10000000);
                            player.sendMessage("§aYou have bought a " + topInventory.getItem(23).getItemMeta().getDisplayName() + " for §6" + plugins.tobisch.com.network.utils.Utils.formatMoney(1000000) + "€");
                        }else{
                            player.sendMessage("§4You have not enough money. You need §6" + 1000000 + "€");
                        }
                    }else{
                        player.sendMessage("§4You do not have the required item or maybe you need even more :)");
                    }

                    if(oldItem.getItemMeta().getDisplayName().contains("Ring") && Utils.hasEveryRing(event.getView().getBottomInventory(), accessoryBagManager.getAccessoryGUI(player.getUniqueId()), item)){
                        if(currencyManager.getPlayerCurrency(player.getUniqueId()) >= 10000000) {
                            topInventory.setItem(23, Utils.upgrade(oldItem, Utils.getStage(oldItem)));
                            topInventory.setItem(21, null);
                            currencyManager.removeCurrencyFromPlayer(player.getUniqueId(), 10000000);
                            player.sendMessage("§aYou have bought a " + topInventory.getItem(23).getItemMeta().getDisplayName() + " for §6" + plugins.tobisch.com.network.utils.Utils.formatMoney(10000000) + "€");
                        }else{
                            player.sendMessage("§4You have not enough money. You need §6" + 10000000 + "€");
                        }
                    }else  {
                        player.sendMessage("§4You do not have the required item or maybe you need even more :)");
                    }
                }

            if(item != null)
                if (Objects.requireNonNull(item.getItemMeta()).getDisplayName().equals("§aUpgrade!") || item.getType() == Material.BLACK_STAINED_GLASS_PANE || item.getType() == Material.DARK_OAK_SIGN ){
                    event.setCancelled(true);
                }


        }
    }
}