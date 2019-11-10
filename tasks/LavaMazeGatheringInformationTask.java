import org.osbot.rs07.api.Equipment;
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

public class LavaMazeGatheringInformationTask extends Task {
    private List<Player> localPlayers;

    Position northMaze = new Position(3024, 3839, 0);
    Position southMaze = new Position(3034, 3822, 0);

    public LavaMazeGatheringInformationTask(MethodProvider api, String name) {
        super(api, name);
        localPlayers = new ArrayList<>();
    }

    @Override
    public boolean canProcess() {
        return Loc.LAVA_MAZE_SCOUT_AREA.getArea().contains(api.myPosition());
    }

    //
    @Override
    public void process() throws InterruptedException {
        DiscordWebhook webhook = new DiscordWebhook("https://discordapp.com/api/webhooks/631977624641470464/otnghB7jkjvFoNRT89y9TzxJa6DwPJq1WZPesIEL_b_EwLMcHIUdZQ3tghVx2H7XWia9");

        final WebWalkEvent south = new WebWalkEvent(northMaze);
        south.setBreakCondition(new Condition() {
            @Override
            public boolean evaluate() {
                return api.myPlayer().isMoving();
            }
        });
        south.setEnergyThreshold(20);
        api.execute(south);

        if (south.hasFinished()) {
            final WebWalkEvent north = new WebWalkEvent(southMaze);
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
                if (player.getCombatLevel() > 35 && player.getCombatLevel() < 90) {
                    LocalDateTime myDateObj = LocalDateTime.now();
                    System.out.println("Before formatting: " + myDateObj);
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("MMM dd HH:mm:ss");
                    String formattedDate = myDateObj.format(myFormatObj);

                    String skulled = player.getSkullIcon() == 0 ? "**SKULLED**" : "";
                    String world = "[" + formattedDate + "] W" + api.getWorlds().getCurrentWorld();
                    String message = world + " " + skulled + " " + player.getName() + " (" +
                            player.getCombatLevel() + ") " + getOthersEquipment(player).toString() + " RISKING " + calculateRisk(player);

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


    }

    private List<String> getOthersEquipment(Player p) {
        List<String> equipmentList = new LinkedList<String>();
        if (p != null) {
            int[] equipment = p.getDefinition().getAppearance();
            for (int i = 0; i < equipment.length; i++) {
                if (equipment[i] - 512 > 0) {
                    String name = ItemDefinition.forId(equipment[i] - 512).getName();
                    equipmentList.add(name);
                }

            }
        }
        return equipmentList;
    }
//ghostface147@hotmail.com
//pass
//4225124jr

    private String calculateRisk(Player p) {
        int first = 0;
        int second = 0;
        int third = 0;

        int total = 0;

        List<String> equipmentList = new LinkedList<String>();

        if (p != null) {
            int[] equipment = p.getDefinition().getAppearance();
            for (int i = 0; i < equipment.length; i++) {
                if (equipment[i] - 512 > 0) {
                    String name = ItemDefinition.forId(equipment[i] - 512).getName();
                    equipmentList.add(name);
                    ItemResource item = new ItemResource(name, equipment[i] - 512);
                    if (item.getPrice() != -1) {
                        total += item.getPrice();
                    }

                    if (item.getPrice() > first) {
                        third = second;
                        second = first;
                        first = item.getPrice();
                    } else if (item.getPrice() > second) {
                        third = second;
                        second = item.getPrice();
                    } else if (item.getPrice() > third)
                        third = item.getPrice();

                }
            }
            if (p.getSkullIcon() == 0) {
                return Integer.toString(total);
            } else {
                return Integer.toString(total - (first - second - third));
            }
        }
        return "N/A";
    }

    public boolean isNewPlayer(Player player) {
        for (Player p : localPlayers) {
            if (p.getName().equals(player.getName()))
                return false;
        }
        return true;
    }
}
