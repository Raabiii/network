package plugins.tobisch.com.network.guis;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompacterGUI implements org.bukkit.inventory.InventoryHolder {
    private final Inventory inventory;

    public CompacterGUI(int size, String title) {
        this.inventory = Bukkit.createInventory(this, size, title);
        ItemStack none = createButton(Material.BLACK_STAINED_GLASS_PANE, " ");

        for(int i = 0;i<27;i++) {
            this.inventory.setItem(i, none);
        }

        for(int i = 9;i<18;i++){
            this.inventory.setItem(i, null);
        }

        ItemStack cobblestone = createEnchanted(Material.COBBLESTONE, "§aCobblestone compactor");
        ItemStack wood = createEnchanted(Material.DARK_OAK_LOG, "§aForaging compactor");
        ItemStack wheat = createEnchanted(Material.WHEAT, "§aWheat compactor");
        ItemStack carrot = createEnchanted(Material.CARROT, "§aCarrot compactor");
        ItemStack potato = createEnchanted(Material.POTATO, "§aPotato compactor");
        ItemStack netherwart = createEnchanted(Material.NETHER_WART, "§aNether Wart compactor");
        ItemStack pumpkin = createEnchanted(Material.PUMPKIN, "§aPumpkin compactor");
        ItemStack beetroot = createEnchanted(Material.BEETROOT, "§aBeetroot compactor");
        ItemStack sweetPerries = createEnchanted(Material.SWEET_BERRIES, "§aSweet Berries compactor");


        ItemStack goBack = createButton(Material.ARROW, "§aGo back");
        ItemStack close = createButton(Material.BARRIER, "§4Close");

        this.inventory.setItem(9, cobblestone);
        this.inventory.setItem(10, wood);
        this.inventory.setItem(11, wheat);
        this.inventory.setItem(12, carrot);
        this.inventory.setItem(13, potato);
        this.inventory.setItem(14, netherwart);
        this.inventory.setItem(15, pumpkin);
        this.inventory.setItem(16, beetroot);
        this.inventory.setItem(17, sweetPerries);
        this.inventory.setItem(18, goBack);
        this.inventory.setItem(19, close);
    }

    private ItemStack createButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta meta = button.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        button.setItemMeta(meta);
        return button;
    }

    public static ItemStack createEnchanted(Material material, String name){
        ItemStack button = new ItemStack(material);
        ItemMeta meta = button.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        meta.setLore(List.of("§4Deactivate"));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        button.setItemMeta(meta);
        return button;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

}
