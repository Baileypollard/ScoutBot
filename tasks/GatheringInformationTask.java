package tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.osbot.rs07.api.Worlds;
import org.osbot.rs07.api.def.ItemDefinition;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Player;
import org.osbot.rs07.api.ui.World;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.Condition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GatheringInformationTask extends Task {
    private List<Player> localPlayers;

    Position southRevs = new Position(3250, 10141, 0);
    Position northRevs = new Position(3244, 10172, 0);

    private Position[] infoPath = {
            new Position(3244, 10178, 0), new Position(3244, 10168, 0), new Position(3246, 10156, 0),
            new Position(3253, 10143, 0)
    };
    public GatheringInformationTask(MethodProvider api, String name) {
        super(api, name);
        localPlayers = new ArrayList<>();
    }

    @Override
    public boolean canProcess() {
        return Loc.REV_SCOUT_AREA.getArea().contains(api.myPosition());
    }
    //
    @Override
    public void process() throws InterruptedException {
        DiscordWebhook webhook = new DiscordWebhook("https://discordapp.com/api/webhooks/631977624641470464/otnghB7jkjvFoNRT89y9TzxJa6DwPJq1WZPesIEL_b_EwLMcHIUdZQ3tghVx2H7XWia9");

        final WebWalkEvent south = new WebWalkEvent(southRevs);
        south.setBreakCondition(new Condition() {
            @Override
            public boolean evaluate() {
                return api.myPlayer().isMoving();
            }
        });
        south.setEnergyThreshold(20);
        api.execute(south);

        if (south.hasFinished()) {
            final WebWalkEvent north = new WebWalkEvent(northRevs);
            south.setBreakCondition(new Condition() {
                @Override
                public boolean evaluate() {
                    return api.myPlayer().isMoving();
                }
            });

            north.setEnergyThreshold(20);
            api.execute(north);

            if (north.hasFinished()) {
                api.log("Done, Hopping");
                if (api.worlds.hopToP2PWorld()) {
                    localPlayers = new ArrayList<>();
                }
            }
        }

        List<Player> allPlayers = api.getPlayers().getAll();
        for (Player player : allPlayers) {
            if (isNewPlayer(player) && !api.myPlayer().getName().equals(player.getName())) {
                String skulled = player.getSkullIcon() == 0 ? "**SKULLED**" : "";
                String world = "W" + api.getWorlds().getCurrentWorld();
                String message = world + " " + skulled + " " + player.getName() + " (" +
                        player.getCombatLevel() + ") " + getOthersEquipment(player).toString();
                api.log(message);
                localPlayers.add(player);
                webhook.setContent(message);
                try {
                    webhook.execute();
                } catch (IOException e) {
                    api.log(e.getLocalizedMessage());
                }
            }
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
