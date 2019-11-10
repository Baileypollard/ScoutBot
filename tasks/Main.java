import org.osbot.rs07.api.ui.Message;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import java.awt.*;
import java.util.ArrayList;

enum ScoutingAreas {
    SCOUT_REVS, SCOUT_LAVA_MAZE, SCOUT_BLACK_CHINS, SCOUT_CHAOS_ALTER, SCOUT_CHAOS_MAGES
}


@ScriptManifest(author = "Snacking Rat", info = "EZ Scouter", logo = "", name = "RatsScouter", version = 1)
public class Main extends Script {

    private ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void onStart(){

        setUpScoutLavaMaze();

    }

    void setUpScoutLavaMaze() {
        tasks.add(new WalkToLumbridgeStairsTask(this, "WALKING TO STAIRS..."));
        tasks.add(new WearBraceletTask(this, "WEARING BRACELET..."));
        tasks.add(new ClimbingStairsTask(this, "CLIMBING STAIRS..."));
        tasks.add(new OpenBankTask(this, "OPENING BANK..."));
        tasks.add(new WithdrawTask(this, "GETTING AMULET..."));
        tasks.add(new TeleportingToLavaMazeTask(this, "TELEPORTING TO LAVA MAZE..."));
        tasks.add(new LavaMazeGatheringInformationTask(this, "GATHERING INFO..."));


    }

    void setUpScoutRevs() {
        tasks.add(new WalkToLumbridgeStairsTask(this, "WALKING TO STAIRS..."));
        tasks.add(new WearBraceletTask(this, "WEARING BRACELET..."));
        tasks.add(new ClimbingStairsTask(this, "CLIMBING STAIRS..."));
        tasks.add(new OpenBankTask(this, "OPENING BANK..."));
        tasks.add(new WithdrawTask(this, "GETTING AMULET..."));
        tasks.add(new TeleportingToLavaMazeTask(this, "TELEPORTING TO LAVA MAZE..."));
        tasks.add(new WalkToCaveTask(this, "WALKING TO CAVE..."));
        tasks.add(new EnterCaveTask(this, "ENTERING CAVE..."));
        tasks.add(new WalkToRevsTask(this, "WALKING TO REVS..."));
        tasks.add(new RevGatheringInformationTask(this, "GATHERING INFO..."));
    }


    @Override
    public int onLoop() {

        tasks.forEach(task ->  {
            if (task.canProcess()) {
                try {
                    task.process();
                    log(task.getStatus());
                } catch (InterruptedException e) {
                    log(e);
                }
            }
        } );
        return Util.random(200, 1200);
    }

    @Override
    public void onMessage(Message message){

    }

    @Override
    public void onPaint(final Graphics2D g) {

    }
}
