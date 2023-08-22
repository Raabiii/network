package plugins.tobisch.com.network.guis.villager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.Buffer;
import java.util.Objects;

public class SpawnEggVillagerGUI extends VillagerGUI {
    private ItemStack[] spawnEggs = {
            new ItemStack(Material.BEE_SPAWN_EGG),
            new ItemStack(Material.BLAZE_SPAWN_EGG),
            new ItemStack(Material.CAVE_SPIDER_SPAWN_EGG),
            new ItemStack(Material.CREEPER_SPAWN_EGG),
            new ItemStack(Material.DOLPHIN_SPAWN_EGG),
            new ItemStack(Material.DROWNED_SPAWN_EGG),
            new ItemStack(Material.ENDERMAN_SPAWN_EGG),
            new ItemStack(Material.ENDERMITE_SPAWN_EGG),
            new ItemStack(Material.GHAST_SPAWN_EGG),
            new ItemStack(Material.HOGLIN_SPAWN_EGG),
            new ItemStack(Material.HUSK_SPAWN_EGG),
            new ItemStack(Material.IRON_GOLEM_SPAWN_EGG),
            new ItemStack(Material.MAGMA_CUBE_SPAWN_EGG),
            new ItemStack(Material.PHANTOM_SPAWN_EGG),
            new ItemStack(Material.SKELETON_SPAWN_EGG),
            new ItemStack(Material.SLIME_SPAWN_EGG),
            new ItemStack(Material.SPIDER_SPAWN_EGG),
            new ItemStack(Material.VEX_SPAWN_EGG),
            new ItemStack(Material.ZOMBIE_SPAWN_EGG),
            new ItemStack(Material.CHICKEN_SPAWN_EGG),
            new ItemStack(Material.PUFFERFISH_SPAWN_EGG),
            new ItemStack(Material.RABBIT_SPAWN_EGG),
            new ItemStack(Material.ZOMBIFIED_PIGLIN_SPAWN_EGG),
            new ItemStack(Material.SPAWNER)

    };

    public SpawnEggVillagerGUI(String name){
        super(name);
        this.createGUI();
    }

    public VillagerGUI createGUI(){
        Inventory inv = this.createBasic("Spawn Egg").getInventory();
        int i = 10;
        int index = 0;
        for(index = 0;index<this.spawnEggs.length;i++){
            if(i % 9 != 0){
                if((i+1)%9 != 0){
                    ItemStack element = this.spawnEggs[index++];
                    ItemMeta meta = element.getItemMeta();
                    String help = element.getType().name().substring(0, 1).toUpperCase() + element.getType().name().substring(1).toLowerCase();
                    String input = ("§a".concat(help)).replace("_", " ");
                    meta.setDisplayName(input);
                    element.setItemMeta(meta);
                    inv.setItem(i, VillagerGUI.disableItem(element, 1000, true));}
            }
        }

        this.setInventory(inv);
        return this;
    }

    public Villager makeVillager(Player player){
        Location location = player.getLocation();
        Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

        // You can set any additional properties for the villager here
        villager.setCustomName(this.name);
        villager.setProfession(Villager.Profession.CLERIC);
        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setRotation(180,0);

        return villager;
    }
}
