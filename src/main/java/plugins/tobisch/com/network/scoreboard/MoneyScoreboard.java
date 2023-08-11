package plugins.tobisch.com.network.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import plugins.tobisch.com.network.utils.Utils;

public class MoneyScoreboard extends ScoreboardBuilder {

    private double tempAmount;
    private String worldName;

    public MoneyScoreboard(Player p, String worldName, double amount) {
        super(p, "         §5§lNazis         ");
        this.tempAmount = amount;
        this.worldName = worldName;
        this.createScoreboard();
    }

    @Override
    public void update() {

    }

    @Override
    public void createScoreboard() {
        setScore(ChatColor.DARK_GRAY.toString(), 10);
        setScore(ChatColor.GRAY + "Dein Rang" + ChatColor.DARK_GRAY + ":", 9);

        if(this.getPlayer().isOp()){
            setScore(ChatColor.RED + "Operator", 8);
        }else{
            setScore(ChatColor.GRAY + "Player", 8);
        }
        setScore(ChatColor.GRAY.toString(), 7);

        setScore(ChatColor.GRAY.toString() + "World", 6);
        setScore(ChatColor.BLUE.toString() + this.worldName, 5);

        setScore(ChatColor.GRAY.toString(), 4);
        setScore(ChatColor.AQUA+ "Money:", 3);
        setScore(ChatColor.GOLD.toString() + Utils.formatMoney(this.tempAmount) + " €", 2);
    }
}
