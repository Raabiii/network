package plugins.tobisch.com.network.listener.villager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.guis.villager.TalismanVillagerGUI;
import plugins.tobisch.com.network.guis.villager.VillagerGUI;
import plugins.tobisch.com.network.listener.talisman.*;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.skills.Skills;
import plugins.tobisch.com.network.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TalismanVillagerListener implements Listener {
    private CurrencyManager currencyManager = new CurrencyManager();
    private Skills skills = new Skills();

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Villager villager)) {
            return;
        }
        event.setCancelled(true);

        Player player = event.getPlayer();

        if (villager.getCustomName() != null && villager.getCustomName().equals("Talisman")) {
            // Open the custom GUI for the player
            TalismanVillagerGUI gui = new TalismanVillagerGUI("Talisman");
            player.openInventory(gui.getInventory());
        }
        Inventory topInventory = player.getInventory();

        for (int i = 0; i < topInventory.getSize(); i++) {
            ItemStack item = topInventory.getItem(i);
            if (item != null) {
                ItemMeta meta = item.getItemMeta();
                String name = "";

                if (meta == null || meta.getDisplayName().equals("")) {
                    name = item.getType().name();
                }else{
                    name = meta.getDisplayName();
                }
                int coins = 0;

                if(name.toLowerCase().contains("cobblestone")){
                    coins = 2;
                    if(name.contains("Enchanted")){
                        coins *= 128;
                    }
                } else if(name.toLowerCase().contains("wheat")){
                    coins = 12;
                    if(name.contains("Enchanted")){
                        coins *= 128;
                    }
                } else if(name.toLowerCase().contains("potato")) {
                    coins = 6;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                } else if(name.toLowerCase().contains("carrot")) {
                    coins = 6;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                } else if(name.toLowerCase().contains("melon_slice") || name.toLowerCase().contains("melon slice")) {
                    coins = 4;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("pumpkin")) {
                    coins = 20;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("log")) {
                    coins = 4;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                } else if(name.toLowerCase().contains("pufferfisch")) {
                    coins = 30;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("nether_wart") || name.toLowerCase().contains("nether wart")) {
                    coins = 8;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("beetroot")) {
                    coins = 6;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("sweet_berries") || name.toLowerCase().contains("sweet berries")) {
                    coins = 16;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("blaze_rod")) {
                    coins = 18;
                    if (name.contains("Enchanted")) {
                        coins *= 64;
                    }
                }  else if(name.toLowerCase().contains("blaze_powder") || name.toLowerCase().contains("blaze powder")) {
                    coins = 9;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("gold_ingot") || name.toLowerCase().contains("gold ingot")) {
                    coins = 8;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                } else if(name.toLowerCase().contains("iron_ingot") || name.toLowerCase().contains("iron ingot")) {
                    coins = 6;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("sugar_cane") || name.toLowerCase().contains("sugar cane")) {
                    coins = 8;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("rabbit_foot") ||name.toLowerCase().contains("rabbit foot")) {
                    coins = 10;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("feather")) {
                    coins = 6;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("ghast_tear") || name.toLowerCase().contains("ghast tear")) {
                    coins = 32;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }  else if(name.toLowerCase().contains("magma_cream") || name.toLowerCase().contains("magma cream")) {
                    coins = 16;
                    if (name.contains("Enchanted")) {
                        coins *= 128;
                    }
                }

                if(name.toLowerCase().contains("seeds")){
                    coins /=2;
                }

                Bukkit.getConsoleSender().sendMessage(name.toLowerCase());

                if(coins != 0)
                    this.addLore(meta, coins, item);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof VillagerGUI) {
            Player player = (Player) event.getPlayer();
            PlayerInventory inventory = player.getInventory();

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        List<String> lore = meta.getLore();
                        if (lore != null) {
                            lore.removeIf(element -> element.contains("coins"));
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Inventory topInventory = event.getView().getTopInventory();
        ItemStack selling = event.getCurrentItem();

        if (selling != null && selling.getItemMeta() != null && selling.getItemMeta().getLore() != null && event.getRawSlot() > 53 && topInventory.getHolder() instanceof VillagerGUI){
            for(String element: selling.getItemMeta().getLore()){
                if(element.contains("coins")){
                    int coins = Integer.parseInt(element.replace(" coins", "").replace("§6", ""));
                    if (selling.getItemMeta().getDisplayName().equals(""))
                        Bukkit.getPlayer(event.getWhoClicked().getUniqueId()).sendMessage("§aYou sold " + selling.getType().name() + " for §6" + coins + " €");
                    else
                        Bukkit.getPlayer(event.getWhoClicked().getUniqueId()).sendMessage("§aYou sold " + selling.getItemMeta().getDisplayName() + " for §6" + coins + " €");

                    currencyManager.addCurrencyToPlayer(Objects.requireNonNull(Bukkit.getPlayer(event.getWhoClicked().getUniqueId())), coins);
                    selling.setAmount(0);
                }
            }
        }

        if((topInventory.getHolder() instanceof TalismanVillagerGUI) && event.getRawSlot()<54){
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
            if(currencyManager.getPlayerCurrency(player)>=500000) {
                if (itemName.contains("Extra hearts")) {
                    this.extraHearts(player, clicked);
                } else if (itemName.contains("Fire")) {
                    this.fire(player,clicked);
                } else if (itemName.contains("Haste")){
                    this.haste(player, clicked);
                } else if (itemName.contains("Jump")) {
                    this.jump(player, clicked);
                } else if (itemName.contains("Life")) {
                    this.life(player, clicked);
                } else if (itemName.contains("Night")) {
                    this.night(player, clicked);
                } else if (itemName.contains("Fall")) {
                    this.nofall(player, clicked);
                } else if (itemName.contains("Regeneration")) {
                    this.regen(player, clicked);
                } else if (itemName.contains("Resistance")){
                    this.resis(player, clicked);
                } else if (itemName.contains("Saturation")) {
                    this.saturation(player, clicked);
                } else if (itemName.contains("Speed")) {
                    this.speed(player, clicked);
                } else if (itemName.contains("Strength")) {
                    this.strength(player, clicked);
                } else if (itemName.contains("Water")) {
                    this.water(player, clicked);
                }
            }else{
                player.sendMessage("§4You need more money");
            }
        }
    }

    private void addLore(ItemMeta meta, int coins, ItemStack item){
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.add(ChatColor.GOLD.toString() + coins*item.getAmount() + " coins");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    private boolean checkIfHasInInventory(Player player, String itemName){
        int amount = 0;
        Inventory inventory = player.getInventory();
        for(ItemStack element: inventory.getContents()){
            if(element != null && element.getItemMeta().getDisplayName().contains(itemName)){
                amount+= element.getAmount();
            }
        }

        if(amount >= 512){
            int removed = 0;

            for (ItemStack item : inventory.getContents()) {
                if (item != null && item.getItemMeta().getDisplayName().contains(itemName)) {
                    int count = item.getAmount();

                    if (count > 512 - removed) {
                        item.setAmount(count - (512 - removed));
                        removed = 512;
                        break;
                    } else {
                        removed += count;
                        item.setAmount(0);
                    }
                }
            }
            return true;
        }
        else {
            player.sendMessage("§4You need more stuff");
        }
        return false;
    }

    private void extraHearts(Player p, ItemStack item){
        if (skills.getFarming().getCurrentLevel(p) < 30){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Melon")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                ExtraHeartsListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void fire(Player p, ItemStack item){
        if (skills.getFarming().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Magma Cream")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                FireResistanceListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void haste(Player p, ItemStack item){
        if (skills.getMining().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Cobblestone")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                HasteListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void jump(Player p, ItemStack item){
        if (skills.getFarming().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Rabbit Foot")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                JumpBoostListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void life(Player p, ItemStack item){
        if (skills.getMining().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Gold")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void night(Player p, ItemStack item){
        if (skills.getFarming().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Golden Carrot")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                NightVisionListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void nofall(Player p, ItemStack item){
        if (skills.getCombat().getCurrentLevel(p) < 30){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Feather")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void regen(Player p, ItemStack item){
        if (skills.getCombat().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Ghast Tear")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                RegenerationListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void resis(Player p, ItemStack item){
        if (skills.getMining().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Iron")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                ResistanceListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void saturation(Player p, ItemStack item){
        if (skills.getFarming().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Beetroot")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                NightVisionListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void speed(Player p, ItemStack item){
        if (skills.getFarming().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Sugar")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                SpeedListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void strength(Player p, ItemStack item){
        if (skills.getCombat().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Blaze Powder")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                StrengthListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }

    private void water(Player p, ItemStack item){
        if (skills.getFishing().getCurrentLevel(p) < 30 || skills.getAlchemy().getCurrentLevel(p) < 10){
            p.sendMessage("§4You need to be stronger");
        } else{
            if (this.checkIfHasInInventory(p, "Enchanted Pufferfisch")){
                currencyManager.removeCurrencyFromPlayer(p, 500000);
                p.getInventory().addItem(item);
                WaterBreathingListener.event(p, item);
                p.sendMessage("§aYou bought a " + item.getItemMeta().getDisplayName() + " for§6 " + Utils.formatMoney(500000) + "€");
            }
        }
    }
}
