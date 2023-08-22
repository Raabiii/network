package plugins.tobisch.com.network.guis.villager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plugins.tobisch.com.network.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public abstract class VillagerGUI implements org.bukkit.inventory.InventoryHolder {
    private Inventory inventory;
    protected String name;
    @Override
    public Inventory getInventory() {
        return inventory;
    }
    public VillagerGUI(String name){
        this.name = name;
        this.inventory = Bukkit.createInventory(this, 54, name);
    }

    public VillagerGUI createBasic(String name){
        ItemStack none = createButton(Material.BLACK_STAINED_GLASS_PANE, " ");

        for(int i = 0;i<9;i++) {
            this.inventory.setItem(i, none);
            this.inventory.setItem(i+45, none);
        }

        for (int i = 0; i<6;i++){
            this.inventory.setItem(i*9, none);
            this.inventory.setItem(8+i*9, none);
        }

        return this;
    }

    public abstract Villager makeVillager(Player player);

    private ItemStack createButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta meta = button.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        button.setItemMeta(meta);
        return button;
    }

    public abstract VillagerGUI createGUI();

    public static ItemStack removeNeeded(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        assert meta != null;
        lore.remove(lore.size()-1);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack addPriceLore(ItemStack item, int price){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.add(String.valueOf(price));
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack removePriceLore(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.remove(lore.size()-1);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public void setInventory(Inventory inv){
        this.inventory = inv;
    }

    public static ItemStack disableItem(ItemStack item, int price, boolean showItems){
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore == null){
            lore = new ArrayList<>();
        }
        assert meta != null;
        lore.add("");
        lore.add("§6" + Utils.formatMoney(price) + " coins");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack enableItem(ItemStack item){
        VillagerGUI.removePriceLore(item);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        assert lore != null;
        lore.remove(lore.size()-1);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}