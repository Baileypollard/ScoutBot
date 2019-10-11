package tasks;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;
import tasks.Task;

public class TeleportingToRevCaveTask extends Task {

    public TeleportingToRevCaveTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return api.inventory.contains("Revenant cave teleport") && api.myPosition().getY() <= 3755;
    }

    @Override
    public void process() throws InterruptedException {
        RS2Widget teleportLavaMaze = api.getWidgets().get(219, 1, 1);

        if (teleportLavaMaze == null) {
            if (api.inventory.interact("Teleport", "Revenant cave teleport")) {
                new ConditionalSleep(2000) {
                    public boolean condition() throws InterruptedException {
                        return teleportLavaMaze != null && teleportLavaMaze.isVisible();
                    }
                }.sleep();
            }
        } else {
            if (teleportLavaMaze.interact("Continue")) {
                new ConditionalSleep(2000) {
                    public boolean condition() throws InterruptedException {
                        return api.myPosition().getY() > 3755;
                    }
                }.sleep();
            }
        }
    }
}

