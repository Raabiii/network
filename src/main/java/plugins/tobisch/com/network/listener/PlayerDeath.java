package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.checkerframework.checker.units.qual.A;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.talisman.LifeSaver;
import plugins.tobisch.com.network.utils.Utils;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PlayerDeath implements Listener {
    private CurrencyManager cm;
    private AccessoryBagManager accessoryBagManager = new AccessoryBagManager();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        LifeSaver ls = new LifeSaver();

        for(ItemStack element: player.getInventory().getContents()){
            if(element != null && ls.compare(element) > 0){
                int amount = ls.compare(element);
                switch (amount){
                    case 1: Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " legendary life saver talisman saved him §6" + Utils.formatMoney( cm.getPlayerCurrency(player) - cm.getPlayerCurrency(player)/2));
                            cm.removeCurrencyFromPlayer(player, cm.getPlayerCurrency(player)/2);
                            break;
                    case 2: Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " legendary life saver ring saved him §6" + Utils.formatMoney(cm.getPlayerCurrency(player) - cm.getPlayerCurrency(player)*0.25));
                            cm.removeCurrencyFromPlayer(player, cm.getPlayerCurrency(player)*0.25);
                            break;
                    case 3: Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " legendary life saver artifact saved his §6" + Utils.formatMoney(cm.getPlayerCurrency(player)) + "§7 !"); break;
                }
                return;
            }
        }

        for(ItemStack element: accessoryBagManager.getInventory(player).getContents()){
            if(element != null && ls.compare(element) > 0){
                int amount = ls.compare(element);
                switch (amount){
                    case 1: Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " legendary life saver talisman saved him §6" + Utils.formatMoney( cm.getPlayerCurrency(player) - cm.getPlayerCurrency(player)/2));
                        cm.removeCurrencyFromPlayer(player, cm.getPlayerCurrency(player)/2);
                        break;
                    case 2: Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " legendary life saver ring saved him §6" + Utils.formatMoney(cm.getPlayerCurrency(player) - cm.getPlayerCurrency(player)*0.25));
                        cm.removeCurrencyFromPlayer(player, cm.getPlayerCurrency(player)*0.25);
                        break;
                    case 3: Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " legendary life saver artifact saved his §6" + Utils.formatMoney(cm.getPlayerCurrency(player)) + "§7 !"); break;
                }
                return;
            }
        }

        double loss = cm.getPlayerCurrency(player)*0.8;
        cm.removeCurrencyFromPlayer(player, loss);
        Bukkit.broadcastMessage(ChatColor.GRAY +  player.getName() + " died and lost §6" + Utils.formatMoney(loss) + "§7 !");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();
        ItemStack customItem = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = customItem.getItemMeta();
        assert meta != null;
        meta.setDisplayName("§5Sword of Legends");
        customItem.setItemMeta(meta);

        inventory.setItem(8, customItem);
    }

    public PlayerDeath(CurrencyManager cm){
        this.cm = cm;
    }
}
