import org.osbot.rs07.api.ui.Message;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;
import tasks.*;

import java.awt.*;
import java.util.ArrayList;


@ScriptManifest(author = "Snacking Rat", info = "EZ Scouter", logo = "", name = "RatsScouter", version = 1)
public class Main extends Script {

    private ArrayList<Task> tasks = new ArrayList<Task>();

    @Override
    public void onStart(){
        tasks.add(new WalkToLumbridgeStairsTask(this, "WALKING TO STAIRS..."));
        tasks.add(new WearBraceletTask(this, "WEARING BRACELET..."));
        tasks.add(new ClimbingStairsTask(this, "CLIMBING STAIRS..."));
        tasks.add(new OpenBankTask(this, "OPENING BANK..."));
        tasks.add(new WithdrawTask(this, "GETTING AMULET..."));
        tasks.add(new TeleportingToRevCaveTask(this, "TELEPORTING TO LAVA MAZE..."));
        tasks.add(new WalkToCaveTask(this, "WALKING TO CAVE..."));
        tasks.add(new EnterCaveTask(this, "ENTERING CAVE..."));
        tasks.add(new WalkToRevsTask(this, "WALKING TO REVS..."));
        tasks.add(new GatheringInformationTask(this, "GATHERING INFO..."));
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
