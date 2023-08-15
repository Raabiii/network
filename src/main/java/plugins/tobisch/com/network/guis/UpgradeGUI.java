package plugins.tobisch.com.network.guis;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Bukkit;

public class UpgradeGUI implements org.bukkit.inventory.InventoryHolder {
    private final Inventory inventory;

    public UpgradeGUI(int size, String title) {
        this.inventory = Bukkit.createInventory(this, size, title);
        ItemStack none = createButton(Material.BLACK_STAINED_GLASS_PANE, " ");
        ItemStack info = createButton(Material.DARK_OAK_SIGN, "§aInfo");

        for(int i = 0;i<36;i++)
            this.inventory.setItem(i, none);

        ItemStack goBack = createButton(Material.ARROW, "§aGo back");
        ItemStack close = createButton(Material.BARRIER, "§4Close");
        ItemStack anvil = createButton(Material.ANVIL, "§aUpgrade!");

        this.inventory.setItem(4, info);
        this.inventory.setItem(21, null);
        this.inventory.setItem(22, anvil);
        this.inventory.setItem(23, null);
        this.inventory.setItem(27, goBack);
        this.inventory.setItem(28, close);
    }

    private ItemStack createButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta meta = button.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        button.setItemMeta(meta);
        return button;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

}
