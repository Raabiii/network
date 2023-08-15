package plugins.tobisch.com.network.listener.talisman;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import plugins.tobisch.com.network.Main;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.talisman.Coin;

import java.util.Objects;

public class CoinListener implements Listener {
    private static CurrencyManager currencyManager = new CurrencyManager();
    private int taskID;
    public Main main;

    public CoinListener(Main main){
        this.main = main;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        event(event.getPlayer(), event.getItem().getItemStack(), main);
    }

    public static void event(Player p, ItemStack item, Main main){
        AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
        CoinListener speedListener = new CoinListener(main);

        if (p.getInventory().getType() == InventoryType.PLAYER) {
            int speed = speedListener.playerHasspeed(item);
            // Check if the player has blaze rods in their inventory
            if (speed > 0) {
                switch (speed){
                    case 1: speed = 45; break;
                    case 2: speed = 100; break;
                    case 3: speed = 200; break;
                }

                int finalSpeed = speed;
                Runnable xpRunnable = new Runnable() {
                    @Override
                    public void run() {
                        currencyManager.addCurrencyToPlayer(p, finalSpeed);
                    }
                };

                speedListener.taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(speedListener.main, xpRunnable, 0, 200L);
            }
        }
    }

    private int playerHasspeed(ItemStack item) {
        Coin speed = new Coin();

        return speed.compare(item);
    }

    private void applySpeedEffect(Player player, int speed) {
        if (player != null) {
            Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_FOLLOW_RANGE)).setBaseValue(speed);
        }

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        if(new Coin().compare(event.getItemInHand())>0){
            stopEvent(event.getPlayer(), event.getItemInHand());
        }
    }

    public void stopEvent(Player p, ItemStack item){
        if(new Coin().compare(item)>0){
            Bukkit.getScheduler().cancelTask(taskID);
        }
    }

    @EventHandler
    public void PlayDropItem(PlayerDropItemEvent event){
        stopEvent(event.getPlayer(), event.getItemDrop().getItemStack());
    }
}
