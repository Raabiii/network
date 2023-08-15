package plugins.tobisch.com.network.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import plugins.tobisch.com.network.Main;
import plugins.tobisch.com.network.scoreboard.MoneyScoreboard;
import plugins.tobisch.com.network.utils.Utils;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CurrencyManager {

    public static HashMap<UUID, Double> currency = new HashMap<>();

    private MoneyScoreboard scoreboard;

    public void saveCurrencyFile() throws IOException {
        for(OfflinePlayer p: Bukkit.getOfflinePlayers()){
            File file = new File("currencyData/currency.dat");

            ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(Files.newOutputStream(file.toPath())));

            try {
                output.writeObject(currency);
                output.flush();
                output.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void loadCurrencyFile() throws IOException, ClassNotFoundException {
            File file = new File("currencyData/currency.dat");

            if(file != null) {
                ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(Files.newInputStream(file.toPath())));
                Object readObject = input.readObject();
                input.close();

                if(!(readObject instanceof HashMap)){
                    throw new IOException("Data is not hashmap");
                }

                currency = (HashMap<UUID, Double>) readObject;
                for(UUID key: currency.keySet()){
                    currency.put(key, currency.get(key));
                }
            }
    }
    /*
        create add, remove, set, and get
    */

    public void addCurrencyToPlayer(OfflinePlayer p, double amount){
        if(currency.get(p.getUniqueId()) != null){
            currency.put(p.getUniqueId(), currency.get(p.getUniqueId()) + amount);
        }else{
            currency.put(p.getUniqueId(), amount);
        }

        if(amount > 0){
            setTimeout(() -> setScoreboard((Player)p, ChatColor.GOLD.toString() + Utils.formatMoney(currency.get(p.getUniqueId()))+ " €"), 3000);

            setScoreboard((Player)p, ChatColor.GOLD.toString() + Utils.formatMoney(currency.get(p.getUniqueId()))+ " € (+" + amount + ")" );
        }else
            setScoreboard((Player)p, ChatColor.GOLD.toString() + Utils.formatMoney(currency.get(p.getUniqueId()))+ " €");
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }

    private void setScoreboard(Player p, String content){
        MoneyScoreboard scoreboard = new MoneyScoreboard((Player)p, p.getWorld().getName(), currency.get(p.getUniqueId()));

        scoreboard.setScore(content, 2);

    }

    public boolean removeCurrencyFromPlayer(OfflinePlayer p, double amount){
        if(currency.get(p.getUniqueId()) != null){
            if(currency.get(p.getUniqueId())-amount < 0 ){
                return false;
            }
            currency.put(p.getUniqueId(), currency.get(p.getUniqueId()) - amount);
            setScoreboard((Player)p, ChatColor.GOLD.toString() + Utils.formatMoney(currency.get(p.getUniqueId()))+ " €");
            return true;
        }

        return false;
    }

    public void setPlayerCurrency(OfflinePlayer p, double amount){
        currency.put(p.getUniqueId(), amount);
        setScoreboard((Player)p, ChatColor.GOLD.toString() + Utils.formatMoney(currency.get(p.getUniqueId()))+ " €");
    }

    public double getPlayerCurrency(OfflinePlayer p){
        if (currency.get(p.getUniqueId()) != null){
            return currency.get(p.getUniqueId());
        }else{
            return 0;
        }
    }

    public double getPlayerCurrency(UUID p){
        if (currency.get(p) != null){
            return currency.get(p);
        }else{
            return 0;
        }
    }
}
