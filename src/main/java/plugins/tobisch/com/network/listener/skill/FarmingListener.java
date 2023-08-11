package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.skills.Farming;
import org.bukkit.block.data.Ageable;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FarmingListener implements Listener {
    private Farming farming;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();
        if( !(Utils.isPlayerInMultiverseWorld(p, "skyblock"))){
            return;
        }
        int amount = 1;
        List<ItemStack> items = new LinkedList<>();
        Material[] CROP_TYPES = {
                Material.WHEAT, Material.CARROTS, Material.POTATOES,
                Material.BEETROOTS, Material.NETHER_WART,
                Material.MELON, Material.PUMPKIN, Material.CACTUS,
                Material.POPPY, Material.DANDELION, Material.ALLIUM, Material.AZURE_BLUET,
                Material.BLUE_ORCHID, Material.CORNFLOWER, Material.LILY_OF_THE_VALLEY,
                Material.OXEYE_DAISY, Material.PINK_TULIP, Material.RED_TULIP, Material.ORANGE_TULIP,
                Material.WHITE_TULIP, Material.SWEET_BERRY_BUSH
        };

        Material[] NON_AGEABLE_CROPS = {
                Material.MELON, Material.PUMPKIN, Material.CACTUS,
                Material.POPPY, Material.DANDELION, Material.ALLIUM,
                Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.CORNFLOWER,
                Material.LILY_OF_THE_VALLEY, Material.OXEYE_DAISY,
                Material.PINK_TULIP, Material.RED_TULIP, Material.ORANGE_TULIP,
                Material.WHITE_TULIP
        };


        for (Material mElement : CROP_TYPES) {
            if (event.getBlock().getType() == mElement) {
                if (event.getBlock().getBlockData() instanceof Ageable) {
                    Ageable ageable = (Ageable) event.getBlock().getBlockData();
                    if (ageable.getAge() >= ageable.getMaximumAge()) {
                        amount += farming.getCurrentLevel(p) / 10;

                        for (int i = 0; i < farming.getCurrentLevel(p) / 10; i++) {
                            items.add(new ItemStack(event.getBlock().getType()));
                        }

                        double random = Math.random();

                        for (ItemStack element : items) {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), element);
                        }

                        farming.addAmount(event.getPlayer(), amount);
                    }
                }

                if(Arrays.asList(NON_AGEABLE_CROPS).contains(mElement)){
                    amount += farming.getCurrentLevel(p) / 10;

                    for (int i = 0; i < farming.getCurrentLevel(p) / 10; i++) {
                        items.add(new ItemStack(event.getBlock().getType()));
                    }

                    double random = Math.random();

                    for (ItemStack element : items) {
                        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), element);
                    }

                    farming.addAmount(event.getPlayer(), amount);
                }
            }
        }
    }

    public FarmingListener(Farming farming){
        this.farming = farming;

    }
}
