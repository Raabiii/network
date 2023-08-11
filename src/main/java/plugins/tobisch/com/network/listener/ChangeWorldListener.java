package plugins.tobisch.com.network.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.scoreboard.MoneyScoreboard;

public class ChangeWorldListener implements Listener {

    private final CurrencyManager currencyManager;

    @EventHandler
    public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();
        String toWorld = event.getPlayer().getWorld().getName();

        MoneyScoreboard moneyScoreboard = new MoneyScoreboard(player, toWorld, currencyManager.getPlayerCurrency(player));
    }

    public ChangeWorldListener(CurrencyManager currencyManager){
        this.currencyManager = currencyManager;
    }
}
