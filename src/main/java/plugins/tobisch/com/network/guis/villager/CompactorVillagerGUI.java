package plugins.tobisch.com.network.guis.villager;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import plugins.tobisch.com.network.guis.CompacterGUI;

public class CompactorVillagerGUI extends VillagerGUI {
    private ItemStack[] compactors = {
            CompacterGUI.createEnchanted(Material.COBBLESTONE,"§aCobblestone compactor"),
            CompacterGUI.createEnchanted(Material.DARK_OAK_LOG,"§aForaging compactor"),
            CompacterGUI.createEnchanted(Material.WHEAT,"§aWheat compactor"),
            CompacterGUI.createEnchanted(Material.POTATO,"§aPotato compactor"),
            CompacterGUI.createEnchanted(Material.NETHER_WART,"§aNether Wart compactor"),
            CompacterGUI.createEnchanted(Material.PUMPKIN,"§aPumpkin compactor"),
            CompacterGUI.createEnchanted(Material.BEETROOT, "§aBeetroot compactor"),
            CompacterGUI.createEnchanted(Material.SWEET_BERRIES, "§aSweet Berries compactor"),
            CompacterGUI.createEnchanted(Material.BLAZE_POWDER,  "§aBlaze Powder compactor"),
            CompacterGUI.createEnchanted(Material.IRON_INGOT, "§aIron ingot compactor"),
            CompacterGUI.createEnchanted(Material.SUGAR_CANE, "§aSugar Cane compactor"),
            CompacterGUI.createEnchanted(Material.GOLD_INGOT, "§aGold ingot compactor"),
            CompacterGUI.createEnchanted(Material.RABBIT_FOOT, "§aRabbit Foot compactor"),
            CompacterGUI.createEnchanted(Material.FEATHER, "§aFeather compactor"),
            CompacterGUI.createEnchanted(Material.GHAST_TEAR, "§aGhast Tear compactor"),
            CompacterGUI.createEnchanted(Material.MAGMA_CREAM, "§aMagma Cream compactor"),
            CompacterGUI.createEnchanted(Material.PUFFERFISH, "§aPufferfisch compactor"),
            CompacterGUI.createEnchanted(Material.CARROT, "§aCarrot compactor"),
            CompacterGUI.createEnchanted(Material.MELON_SLICE, "§aMelon Slice compactor"),
    };

    public CompactorVillagerGUI(String name){
        super(name);
        this.createGUI();
    }

    public VillagerGUI createGUI(){
        Inventory inv = this.createBasic("Compactor").getInventory();
        int i = 10;
        int index = 0;
        for(index = 0;index<this.compactors.length;i++){
            if(i % 9 != 0){
                if((i+1)%9 != 0){
                    inv.setItem(i, VillagerGUI.disableItem(this.compactors[index++], 10000, true));}
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
        villager.setProfession(Villager.Profession.ARMORER);
        villager.setAI(false);
        villager.setInvulnerable(true);

        villager.setRotation(180,0);

        return villager;
    }
}
