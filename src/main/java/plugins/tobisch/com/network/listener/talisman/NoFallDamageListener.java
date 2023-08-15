package plugins.tobisch.com.network.listener.talisman;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.talisman.NoFallDamage;

public class NoFallDamageListener implements Listener {
    private final AccessoryBagManager accessoryBagManager = new AccessoryBagManager();

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Player player;

        if(event.getEntity() instanceof Player){
            player = (Player) event.getEntity();
        }else{
            return;
        }

        NoFallDamage ls = new NoFallDamage();

        for(ItemStack element: player.getInventory().getContents()){
            if(element != null && ls.compare(element) > 0){
                int amount = ls.compare(element);
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    double originalDamage = event.getDamage();
                    double reducedDamage = switch (amount) {
                        case 1 -> originalDamage * (1.0 - 0.3);
                        case 2 -> originalDamage * (1.0 - 0.5);
                        default -> 0;
                    };

                    Bukkit.getConsoleSender().sendMessage(originalDamage + " " + reducedDamage);

                    event.setDamage(reducedDamage);
                    return;
                }
            }
        }

        for(ItemStack element: accessoryBagManager.getInventory(player).getContents()){
            if(element != null && ls.compare(element) > 0){
                int amount = ls.compare(element);
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    double originalDamage = event.getDamage();
                    double reducedDamage = 0;

                    Bukkit.getConsoleSender().sendMessage(amount + "");

                    switch (amount) {
                        case 1 -> reducedDamage = originalDamage * (1.0 - 0.3);
                        case 2 -> reducedDamage = originalDamage * (1.0 - 0.5);
                        case 3 -> reducedDamage = 0;
                    }

                    event.setDamage(reducedDamage);
                }
            }
        }
    }
}
