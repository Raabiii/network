package plugins.tobisch.com.network.skills;

import org.checkerframework.checker.units.qual.C;
import plugins.tobisch.com.network.manager.CurrencyManager;

public class Skills {
    private static CurrencyManager currencyManager = new CurrencyManager();
    private static Mining mining = new Mining();
    private static Foraging foraging = new Foraging();
    private static Farming farming = new Farming();
    private static Combat combat = new Combat();
    private static Fishing fishing = new Fishing();
    private static Enchanting enchanting = new Enchanting();
    private static Alchemy alchemy = new Alchemy();

    public void saveSkill(){
        mining.saveSkill();
        foraging.saveSkill();
        farming.saveSkill();
        combat.saveSkill();
        fishing.saveSkill();
        enchanting.saveSkill();
        alchemy.saveSkill();
    }

    public void loadSkill(){
        mining.loadSkill();
        foraging.loadSkill();
        farming.loadSkill();
        combat.loadSkill();
        fishing.loadSkill();
        enchanting.loadSkill();
        alchemy.loadSkill();
    }

    public Mining getMining(){
        return mining;
    }

    public Foraging getForaging(){
        return foraging;
    }

    public Farming getFarming(){
        return farming;
    }

    public Combat getCombat(){
        return combat;
    }

    public Fishing getFishing(){ return fishing; }

    public Enchanting getEnchanting(){return enchanting;}

    public Alchemy getAlchemy(){return alchemy;}
}
