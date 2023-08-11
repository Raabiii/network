package plugins.tobisch.com.network.listener.skill;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.skills.Combat;
import plugins.tobisch.com.network.utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class CombatListener implements Listener {
    private Combat combat;

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null){
            return;
        }

        Player p = event.getEntity().getKiller();
        if( !(Utils.isPlayerInMultiverseWorld(p, "stoneblock"))){
            return;
        }
        int amount = 1;
        List<ItemStack> items = new LinkedList<>();

        amount+= combat.getCurrentLevel(p)/10 + event.getEntity().getMaxHealth()/10;

        for(int i = 0;i< (combat.getCurrentLevel(p)/10) + event.getEntity().getMaxHealth()/10;i++) {
            items.add(new ItemStack(event.getDrops().get(i)));
        }

        double random = Math.random();

        event.getDrops().addAll(items);

        combat.addAmount(p, amount);
    }

    public CombatListener(Combat combat){
        this.combat = combat;

    }
}
