package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.Main;
import plugins.tobisch.com.network.skills.Foraging;
import plugins.tobisch.com.network.utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class ForagingListener implements Listener {
    private Foraging foraging;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if( !(Utils.isPlayerInMultiverseWorld(p, "skyblock"))){
            return;
        }
        int amount = 1;
        List<ItemStack> items = new LinkedList<>();
        Material[] WOOD_BLOCK_TYPES = {
                Material.OAK_LOG, Material.SPRUCE_LOG, Material.BIRCH_LOG,
                Material.JUNGLE_LOG, Material.ACACIA_LOG, Material.DARK_OAK_LOG
        };

        for (Material mElement : WOOD_BLOCK_TYPES) {
            if (event.getBlock().getType() == mElement) {
                amount += foraging.getCurrentLevel(p) / 10;

                for (int i = 0; i < foraging.getCurrentLevel(p) / 10; i++) {
                    items.add(new ItemStack(event.getBlock().getType()));
                }

                double random = Math.random();

                for (ItemStack element : items) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), element);
                }

                foraging.addAmount(event.getPlayer(), amount);
            }
        }
    }

    public ForagingListener(Foraging foraging){
        this.foraging = foraging;

    }
}
