package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.skills.Fishing;
import plugins.tobisch.com.network.skills.Mining;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class FishingListener implements Listener {
    private Fishing fishing;

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player p = event.getPlayer();
        if( !(Utils.isPlayerInMultiverseWorld(p, "skyblock"))){
            return;
        }
        int amount = 1;
        List<ItemStack> items = new LinkedList<>();

        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            amount+= fishing.getCurrentLevel(p)/10;
            fishing.addAmount(p, amount);
        }
    }

    public FishingListener(Fishing fishing){
        this.fishing = fishing;

    }
}
