package plugins.tobisch.com.network.listener;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import plugins.tobisch.com.network.guis.*;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.manager.QuiverManager;
import plugins.tobisch.com.network.skills.LevelRequirements;
import plugins.tobisch.com.network.skills.Skills;
import plugins.tobisch.com.network.utils.Utils;

import java.util.Objects;

public class MenuInteraction implements Listener {
    private final String arrow = "§aGo back";
    private final String close = "§4Close";

    private final QuiverManager quiverManager;
    private final AccessoryBagManager accessoryBagManager;

    public MenuInteraction(QuiverManager quiverManager, AccessoryBagManager accessoryBagManager){
        this.quiverManager = quiverManager;
        this.accessoryBagManager = accessoryBagManager;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemInHand = player.getInventory().getItemInMainHand();

            if (itemInHand.getType() == Material.NETHER_STAR && itemInHand.hasItemMeta()) {
                // Open the custom GUI
                this.openCustomGUI(player);
            }
        }
    }

    private void openCustomGUI(Player player) {
        Inventory gui = Bukkit.createInventory(new MenuGUI(), 45, "Menu");

        ItemStack none = createButton(Material.BLACK_STAINED_GLASS_PANE, " ");

        for(int i = 0;i<45;i++)
            gui.setItem(i, none);


        ItemStack button1 = createButton(Material.COMPASS, "§aWorlds");
        ItemStack upgrade = createButton(Material.ANVIL, "§aUpgrade");
        ItemStack accessoryBag = createButton(Material.COMPOSTER, "§aAccessory Bag");
        ItemStack quiver = createButton(Material.ARROW, "§aQuiver");
        ItemStack close = createButton(Material.BARRIER, this.close);
        ItemStack status = createButton(Material.PLAYER_HEAD, "§aStatus");

        SkullMeta skullMeta = (SkullMeta) status.getItemMeta();
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getName()));
        skullMeta.setDisplayName("§aStatus");
        status.setItemMeta(skullMeta);

        gui.setItem(4, status);
        gui.setItem(22, button1);
        gui.setItem(20, upgrade);
        gui.setItem(21, accessoryBag);
        gui.setItem(23, quiver);
        gui.setItem(40, close);

        player.openInventory(gui);
    }

    private void openQuiverGui(Player player){
        player.openInventory(this.quiverManager.getInventory(player));
    }

    private void openAccessoryBagGui(Player player){
        player.openInventory(this.accessoryBagManager.getInventory(player));
    }

    private void openUpgradeGUI(Player player){
        player.openInventory(new UpgradeGUI(36, "Upgrade").getInventory());
    }

    private void openTeleportGui(Player player) {
        Inventory gui = Bukkit.createInventory(new TeleportGUI(), 36, "Worlds");

        ItemStack none = createButton(Material.BLACK_STAINED_GLASS_PANE, " ");

        for(int i = 0;i<36;i++)
            gui.setItem(i, none);

        ItemStack button1 = createButton(Material.END_CRYSTAL, "§aLobby");
        ItemStack button2 = createButton(Material.GRASS_BLOCK, "§askyblock");
        ItemStack button3 = createButton(Material.STONE, "§astoneblock");
        ItemStack button4 = createButton(Material.NETHERRACK, "§anetherblock");
        ItemStack goBack = createButton(Material.ARROW, arrow);
        ItemStack close = createButton(Material.BARRIER, this.close);

        gui.setItem(13, button1);
        gui.setItem(21, button2);
        gui.setItem(22, button3);
        gui.setItem(23, button4);
        gui.setItem(27, goBack);
        gui.setItem(28, close);

        player.openInventory(gui);
    }

    public void openStatus(Player player){
        Skills skills = new Skills();
        Inventory gui = Bukkit.createInventory(new TeleportGUI(), 45, "Status");

        ItemStack none = createButton(Material.BLACK_STAINED_GLASS_PANE, " ");

        for(int i = 0;i<45;i++)
            gui.setItem(i, none);

        ItemStack status = createButton(Material.PLAYER_HEAD, ChatColor.GOLD + player.getName() + "'s Status");
        ItemStack farming = skills.getFarming().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.GOLDEN_HOE, "§aFarming " + Utils.toRoman(skills.getFarming().getCurrentLevel(player))) : createButton(Material.GOLDEN_HOE, "§6Farming " + Utils.toRoman(skills.getFarming().getCurrentLevel(player)));
        ItemStack mining = skills.getMining().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.STONE_PICKAXE, "§aMing " + Utils.toRoman(skills.getFarming().getCurrentLevel(player))) : createButton(Material.STONE_PICKAXE, "§6Mining " + Utils.toRoman(skills.getMining().getCurrentLevel(player)));
        ItemStack combat = skills.getCombat().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.DIAMOND_SWORD, "§aCombat " + Utils.toRoman(skills.getCombat().getCurrentLevel(player))) : createButton(Material.DIAMOND_SWORD, "§6Combat " + Utils.toRoman(skills.getCombat().getCurrentLevel(player)));
        ItemStack foraging = skills.getForaging().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.JUNGLE_SAPLING, "§aForaging " + Utils.toRoman(skills.getForaging().getCurrentLevel(player))) : createButton(Material.JUNGLE_SAPLING, "§6Foraging " + Utils.toRoman(skills.getForaging().getCurrentLevel(player)));
        ItemStack fishing = skills.getFishing().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.FISHING_ROD, "§aFishing " + Utils.toRoman(skills.getFishing().getCurrentLevel(player))) : createButton(Material.FISHING_ROD, "§6Fishing " + Utils.toRoman(skills.getFishing().getCurrentLevel(player)));
        ItemStack enchanting = skills.getEnchanting().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.ENCHANTING_TABLE, "§aEnchanting " + Utils.toRoman(skills.getEnchanting().getCurrentLevel(player))) : createButton(Material.ENCHANTING_TABLE, "§6Enchanting " + Utils.toRoman(skills.getEnchanting().getCurrentLevel(player)));
        ItemStack alchemy = skills.getAlchemy().getCurrentLevel(player) < LevelRequirements.getMax() ? createButton(Material.BREWING_STAND, "§aAlchemy " + Utils.toRoman(skills.getAlchemy().getCurrentLevel(player))) : createButton(Material.BREWING_STAND, "§6Alchemy " + Utils.toRoman(skills.getAlchemy().getCurrentLevel(player)));

        ItemMeta farmingMeta = farming.getItemMeta();
        ItemMeta miningMeta = mining.getItemMeta();
        ItemMeta combatMeta = combat.getItemMeta();

        assert farmingMeta != null;
        farmingMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        assert miningMeta != null;
        miningMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        assert combatMeta != null;
        combatMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        farming.setItemMeta(farmingMeta);
        mining.setItemMeta(miningMeta);
        combat.setItemMeta(combatMeta);

        SkullMeta skullMeta = (SkullMeta) status.getItemMeta();
        assert skullMeta != null;
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(player.getName()));
        skullMeta.setDisplayName(ChatColor.GREEN + player.getDisplayName() + "'s status");
        status.setItemMeta(skullMeta);

        ItemStack goBack = createButton(Material.ARROW, arrow);
        ItemStack close = createButton(Material.BARRIER, this.close);

        gui.setItem(4, status);
        gui.setItem(19, farming);
        gui.setItem(20, mining);
        gui.setItem(21, combat);
        gui.setItem(22, foraging);
        gui.setItem(23, fishing);
        gui.setItem(24, enchanting);
        gui.setItem(25, alchemy);
        gui.setItem(36, goBack);
        gui.setItem(37, close);


        player.openInventory(gui);
    }

    private ItemStack createButton(Material material, String name) {
        ItemStack button = new ItemStack(material);
        ItemMeta meta = button.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        button.setItemMeta(meta);
        return button;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MenuGUI) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if(clickedItem == null){
                return;
            }

            if (clickedItem.getType() == Material.COMPASS) {
                this.openTeleportGui(player);
            } else if (clickedItem.getType() == Material.COMPOSTER) {
                this.openAccessoryBagGui(player);
            } else if (clickedItem.getType() == Material.ARROW) {
                this.openQuiverGui(player);
            } else if (clickedItem.getType() == Material.BARRIER) {
                player.closeInventory();
            } else if (clickedItem.getType() == Material.PLAYER_HEAD) {
                this.openStatus(player);
            }else if (clickedItem.getType() == Material.ANVIL && "§aUpgrade".equals(Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName())) {
                this.openUpgradeGUI(player);
            }
        }

        if (event.getInventory().getHolder() instanceof QuiverGUI ||event.getInventory().getHolder() instanceof UpgradeGUI || event.getInventory().getHolder() instanceof AccessoryBagGUI) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if(clickedItem == null ) return;
            ItemMeta arrowMeta = clickedItem.getItemMeta();

            Bukkit.getConsoleSender().sendMessage( Objects.requireNonNull(arrowMeta).getDisplayName() + " " + arrow);

            if (clickedItem.getType() == Material.ARROW && Objects.requireNonNull(arrowMeta).getDisplayName().equals(arrow)) {
                openCustomGUI(player);
            } else if (clickedItem.getType() == Material.BARRIER) {
                player.closeInventory();
            }
        }

        if (event.getInventory().getHolder() instanceof TeleportGUI) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            Bukkit.getConsoleSender().sendMessage("§4" + player.getName());
            ItemStack clickedItem = event.getCurrentItem();
            Bukkit.getConsoleSender().sendMessage(clickedItem + "");
            assert clickedItem != null;
            ItemMeta arrowMeta = clickedItem.getItemMeta();

            if (clickedItem.getType() == Material.END_CRYSTAL) {
                teleportToWorld("lobby", player);
            } else if (clickedItem.getType() == Material.GRASS_BLOCK) {
                teleportToWorld("skyblock", player);
            }else if (clickedItem.getType() == Material.STONE) {
                teleportToWorld("stoneblock", player);
            }else if (clickedItem.getType() == Material.NETHERRACK) {
                teleportToWorld("netherblock", player);
            } else if (clickedItem.getType() == Material.ARROW && Objects.requireNonNull(arrowMeta).getDisplayName().equals(arrow)) {
                openCustomGUI(player);
            } else if (clickedItem.getType() == Material.BARRIER) {
                player.closeInventory();
            }
        }

        if (event.getClickedInventory() != null && event.getClickedInventory().equals(event.getWhoClicked().getInventory())) {
            if (event.getRawSlot() == 44) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        ItemStack droppedItem = event.getItemDrop().getItemStack();
        if (droppedItem.getType() == Material.NETHER_STAR && droppedItem.hasItemMeta()) {
            event.setCancelled(true);
        }
    }

    private void teleportToWorld(String worldName, Player player) {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            Location loc = world.getSpawnLocation();
            loc.add(0.5, 0, 0.5);
            player.teleport(loc);
        } else {
            player.sendMessage("The stone map world is not available.");
        }
    }
}
