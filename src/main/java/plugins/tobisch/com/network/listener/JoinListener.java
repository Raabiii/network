package plugins.tobisch.com.network.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.scoreboard.MoneyScoreboard;

import java.util.Objects;

public class JoinListener implements Listener {

    private final CurrencyManager currencyManager;

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        this.createCustomItem(player);
        new MoneyScoreboard(player, player.getWorld().getName(), currencyManager.getPlayerCurrency(player));
    }

    public JoinListener(CurrencyManager currencyManager){
        this.currencyManager = currencyManager;
    }

    private void createCustomItem(Player player) {
        ItemStack customItem = new ItemStack(Material.NETHER_STAR);

        ItemMeta meta = customItem.getItemMeta();
        assert meta != null;
        meta.setDisplayName("§5Sword of Legends");
        customItem.setItemMeta(meta);

        player.getInventory().setItem(8, customItem);
    }
}
