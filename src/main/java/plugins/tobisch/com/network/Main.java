package plugins.tobisch.com.network;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import plugins.tobisch.com.network.commands.CurrencyCommand;
import plugins.tobisch.com.network.commands.GetLifeSaver;
import plugins.tobisch.com.network.commands.PayCommand;
import plugins.tobisch.com.network.listener.*;
import plugins.tobisch.com.network.listener.talisman.NoFallDamageListener;
import plugins.tobisch.com.network.listener.skill.*;
import plugins.tobisch.com.network.listener.talisman.*;
import plugins.tobisch.com.network.manager.AccessoryBagManager;
import plugins.tobisch.com.network.manager.CompactorManager;
import plugins.tobisch.com.network.manager.CurrencyManager;
import plugins.tobisch.com.network.manager.QuiverManager;
import plugins.tobisch.com.network.skills.Skills;

import java.io.IOException;
import java.util.Objects;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        CurrencyManager currencyManager = new CurrencyManager();
        QuiverManager quiverManager = new QuiverManager();
        AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
        Skills skills = new Skills();
        CompactorManager compactorManager = new CompactorManager();

        registerManagers(currencyManager, quiverManager, accessoryBagManager, skills, compactorManager);
        registerCommands();
        registerListeners(currencyManager,  quiverManager, accessoryBagManager, skills);
    }

    @Override
    public void onDisable() {
        CurrencyManager currencyManager = new CurrencyManager();
        Skills skills = new Skills();
        CompactorManager compactorManager = new CompactorManager();
        skills.saveSkill();
        compactorManager.saveAccessoryBag();

        try {
            currencyManager.saveCurrencyFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        QuiverManager quiverManager = new QuiverManager();
        AccessoryBagManager accessoryBagManager = new AccessoryBagManager();
        quiverManager.savePlayerInventories();
        accessoryBagManager.saveAccessoryBag();
    }

    public void registerManagers(CurrencyManager currencyManager, QuiverManager quiverManager, AccessoryBagManager accessoryBagManager, Skills skills, CompactorManager compactorManager){
        try {
            currencyManager.loadCurrencyFile();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        quiverManager.loadPlayerInventories();
        accessoryBagManager.loadAccessoryBag();
        skills.loadSkill();
        compactorManager.loadAccessoryBag();
    }

    public void registerCommands(){
        Objects.requireNonNull(getCommand("life-saver")).setExecutor(new GetLifeSaver());
        Objects.requireNonNull(getCommand("currency")).setExecutor(new CurrencyCommand(this));
        Objects.requireNonNull(getCommand("pay")).setExecutor(new PayCommand(this));
    }

    public void registerListeners(CurrencyManager currencyManager, QuiverManager quiverManager, AccessoryBagManager accessoryBagGUI, Skills skills){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new JoinListener(currencyManager), this);
        pm.registerEvents(new ChangeWorldListener(currencyManager), this);
        pm.registerEvents(new SignClick(), this);
        pm.registerEvents(new MenuInteraction(quiverManager, accessoryBagGUI), this);
        pm.registerEvents(new CoinDropListener(this, 100), this);
        pm.registerEvents(new PickUpCoins(currencyManager), this);
        pm.registerEvents(new PlayerDeath(currencyManager), this);
        pm.registerEvents(new CustomINVListener(), this);
        pm.registerEvents(new MiningListener(skills.getMining()), this);
        pm.registerEvents(new ForagingListener(skills.getForaging()), this);
        pm.registerEvents(new FarmingListener(skills.getFarming()), this);
        pm.registerEvents(new CombatListener(skills.getCombat()), this);
        pm.registerEvents(new FishingListener(skills.getFishing()), this);
        pm.registerEvents(new EnchantingListener(skills.getEnchanting()), this);
        pm.registerEvents(new AlchemyListener(skills.getAlchemy()), this);
        pm.registerEvents(new StrengthListener(), this);
        pm.registerEvents(new ResistanceListener(), this);
        pm.registerEvents(new SpeedListener(), this);
        pm.registerEvents(new JumpBoostListener(), this);
        pm.registerEvents(new HasteListener(), this);
        pm.registerEvents(new RegenerationListener(), this);
        pm.registerEvents(new FireResistanceListener(), this);
        pm.registerEvents(new WaterBreathingListener(), this);
        pm.registerEvents(new NightVisionListener(), this);
        pm.registerEvents(new SaturationListener(), this);
        pm.registerEvents(new ExtraHeartsListener(), this);
        pm.registerEvents(new CoinListener(this), this);
        pm.registerEvents(new NoFallDamageListener(), this);
        pm.registerEvents(new CompacterListener(), this);
    }
}
