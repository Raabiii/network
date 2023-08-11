package plugins.tobisch.com.network.skills;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class Mining {
    private static HashMap<UUID, Integer> level = new HashMap<>();
    private static HashMap<UUID, Integer> currentAmount = new HashMap<>();
    private static CurrencyManager currencyManager = new CurrencyManager();

    public void addAmount(Player p, int amount){
        level.putIfAbsent(p.getUniqueId(), 1);
        currentAmount.putIfAbsent(p.getUniqueId(), 0);
        currentAmount.put(p.getUniqueId(), currentAmount.get(p.getUniqueId())+ amount);
        if (currentAmount.get(p.getUniqueId()) >= LevelRequirements.level(level.get(p.getUniqueId()))){
            level.put(p.getUniqueId(), level.get(p.getUniqueId()) + 1);
            p.sendMessage("§3You have reached Mining level: " + level.get(p.getUniqueId()) + " and got§6 " +  LevelRequirements.levelMoney(level.get(p.getUniqueId())) + "€");
            currencyManager.addCurrencyToPlayer(p, LevelRequirements.levelMoney(level.get(p.getUniqueId())));
            currentAmount.put(p.getUniqueId(), 0);
        }
        sendActionBar(p, "§3+ " + amount + " Mining: " + getCurrentAmount(p) + "/" + LevelRequirements.level(getCurrentLevel(p)) + " (" + (getCurrentAmount(p)*100)/LevelRequirements.level(getCurrentLevel(p)) + "%)");
    }

    private void sendActionBar(Player player, String message){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    public int getCurrentLevel(Player p){
        level.putIfAbsent(p.getUniqueId(), 1);
        return level.get(p.getUniqueId());
    }

    public int getCurrentAmount(Player p){
        currentAmount.putIfAbsent(p.getUniqueId(), 0);
        return currentAmount.get(p.getUniqueId());
    }

    public void saveSkill(){
        File fileLevel = new File("skill/miningLevel.yml");
        File fileAmount = new File("skill/miningAmount.yml");
        FileConfiguration configAmount = YamlConfiguration.loadConfiguration(fileAmount);
        FileConfiguration configLevel = YamlConfiguration.loadConfiguration(fileLevel);

        for(UUID id : level.keySet()) {
            YamlConfiguration configYAML = new YamlConfiguration();
            ConfigurationSection levelSection = configYAML.createSection("level");

            levelSection.set("level", level.get(id));

            configLevel.set(id.toString(), configYAML);
        }

        for(UUID id : currentAmount.keySet()) {
            YamlConfiguration configYAML = new YamlConfiguration();
            ConfigurationSection amountSection = configYAML.createSection("amount");

            amountSection.set("amount", currentAmount.get(id));

            configAmount.set(id.toString(), configYAML);
        }

        try {
            configLevel.save(fileLevel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            configAmount.save(fileAmount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSkill() {
        File fileLevel = new File("skill/miningLevel.yml");
        FileConfiguration configLevel = YamlConfiguration.loadConfiguration(fileLevel);
        File fileAmount = new File("skill/miningAmount.yml");
        FileConfiguration configAmount = YamlConfiguration.loadConfiguration(fileAmount);

        for (String key : configLevel.getKeys(false)) {
            int temp = 0;
            UUID playerId = UUID.fromString(key);

            ConfigurationSection inventorySection = Objects.requireNonNull(configLevel.getConfigurationSection(key)).getConfigurationSection("level");
            assert inventorySection != null;

            for(String element: inventorySection.getKeys(false)){
                temp = Integer.parseInt(inventorySection.getValues(true).get(element).toString());
            }

            level.put(playerId, temp);
        }

        for (String key : configAmount.getKeys(false)) {
            int temp = 0;
            UUID playerId = UUID.fromString(key);

            ConfigurationSection inventorySection = Objects.requireNonNull(configAmount.getConfigurationSection(key)).getConfigurationSection("amount");
            assert inventorySection != null;

            for(String element: inventorySection.getKeys(false)){
                temp = Integer.parseInt(inventorySection.getValues(true).get(element).toString());
            }

            currentAmount.put(playerId, temp);
        }


    }
}
