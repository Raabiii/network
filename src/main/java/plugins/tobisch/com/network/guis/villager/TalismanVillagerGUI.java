package plugins.tobisch.com.network.guis.villager;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import plugins.tobisch.com.network.talisman.Talisman;
import plugins.tobisch.com.network.talisman.Utils;

public class TalismanVillagerGUI extends VillagerGUI {

    public TalismanVillagerGUI(String name){
        super(name);
        this.createGUI();
    }

    public VillagerGUI createGUI(){
        Inventory inv = this.createBasic("Talisman").getInventory();
        int i = 9;
        for(Talisman element: Utils.getTalismans()){
            if(i % 9 != 0){
                if((i+1)%9 != 0){
                    inv.setItem(i, VillagerGUI.disableItem(element.createTalisman(), 500000, true));}
            }
            i++;
        }

        this.setInventory(inv);
        return this;
    }

    public Villager makeVillager(Player player){
        Location location = player.getLocation();
        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

        // You can set any additional properties for the villager here
        villager.setCustomName(this.name);
        villager.setProfession(Villager.Profession.FARMER);
        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setRotation(180,0);

        return villager;
    }
}
