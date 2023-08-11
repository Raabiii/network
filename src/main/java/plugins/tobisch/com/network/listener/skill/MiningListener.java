package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.skills.Mining;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MiningListener implements Listener {
    private Mining mining;

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player p = event.getPlayer();
        if( !(Utils.isPlayerInMultiverseWorld(p, "stoneblock"))){
            return;
        }

        int amount = 1;
        List<ItemStack> items = new LinkedList<>();

        if(event.getBlock().getType() == Material.STONE || event.getBlock().getType() == Material.COBBLESTONE || event.getBlock().getType() == Material.BASALT){
            amount+= mining.getCurrentLevel(p)/10;

            for(int i = 0;i< mining.getCurrentLevel(p)/10;i++){
                switch (event.getBlock().getType()){
                    case STONE:
                        if(p.getItemInHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0){
                            items.add(new ItemStack(event.getBlock().getType()));
                        }else{
                            items.add(new ItemStack(Material.COBBLESTONE));
                        }
                        break;
                    default:
                        items.add(new ItemStack(event.getBlock().getType()));
                }
            }

            for(ItemStack element: items){
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), element);
            }


            mining.addAmount(event.getPlayer(), amount);
        }
    }

    public MiningListener(Mining mining){
        this.mining = mining;

    }
}
