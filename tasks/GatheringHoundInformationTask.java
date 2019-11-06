package tasks;

import org.osbot.rs07.api.def.ItemDefinition;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.Condition;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GatheringHoundInformationTask extends Task {
    private List<Player> localPlayers;

    public GatheringHoundInformationTask(MethodProvider api, String name) {
        super(api, name);
        localPlayers = new ArrayList<>();
    }

    @Override
    public boolean canProcess() {
        return Loc.HOUND_SCOUT_AREA.getArea().contains(api.myPosition());
    }

    @Override
    public void process() throws InterruptedException {
        localPlayers = new ArrayList<>();
        DiscordWebhook webhook = new DiscordWebhook("https://discordapp.com/api/webhooks/631977624641470464/otnghB7jkjvFoNRT89y9TzxJa6DwPJq1WZPesIEL_b_EwLMcHIUdZQ3tghVx2H7XWia9");
        Position pos = new Position(3202, 10062, 0);

        if (!api.myPlayer().getPosition().equals(pos)) {
            api.walking.webWalk(new Position(3202, 10062, 0));
        }

        List<Player> allPlayers = api.getPlayers().getAll();
        for (Player player : allPlayers)
        {
            if (isNewPlayer(player) && !api.myPlayer().getName().equals(player.getName()))
            {
                if (player.getCombatLevel() > 35 && player.getCombatLevel() < 90)
                {
                    LocalDateTime myDateObj = LocalDateTime.now();
                    System.out.println("Before formatting: " + myDateObj);
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM dd HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);

                    String skulled = player.getSkullIcon() == 0 ? "**SKULLED**" : "";
                    String world = "[" + formattedDate + "] W" + api.getWorlds().getCurrentWorld();
                    String message = world + " " + skulled + " " + player.getName() + " (" +
                            player.getCombatLevel() + ") " + getOthersEquipment(player).toString();

                    api.log(message);
                    localPlayers.add(player);
                    webhook.setContent(message);
                    try
                    {
                        webhook.execute();
                    } catch (IOException e)
                    {
                        api.log(e.getLocalizedMessage());
                    }
                }
            }
        }
        if (api.worlds.hopToP2PWorld()) {
        }
    }

    private List<String> getOthersEquipment(Player p) {
        List<String> equipmentList = new LinkedList<String>();
        if (p != null) {
            int[] equipment = p.getDefinition().getAppearance();
            for (int i = 0; i < equipment.length; i++) {
                if (equipment[i] - 512 > 0)
                    equipmentList.add(ItemDefinition.forId(equipment[i] - 512).getName());
            }
        }
        return equipmentList;
    }

    public boolean isNewPlayer(Player player) {
        for (Player p : localPlayers) {
            if (p.getName().equals(player.getName()))
                return false;
        }
        return true;
    }
}
