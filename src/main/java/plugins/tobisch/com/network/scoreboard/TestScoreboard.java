package plugins.tobisch.com.network.scoreboard;

import org.bukkit.entity.Player;

public class TestScoreboard extends ScoreboardBuilder {
    @Override
    public void update() {

    }

    @Override
    public void createScoreboard() {
        super.setScore("bigCock", 0);
    }

    public TestScoreboard(Player p) {
        super(p, "       §5§lNazis       ");
    }
}
