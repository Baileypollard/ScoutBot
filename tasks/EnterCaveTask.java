package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;
import tasks.Task;

public class EnterCaveTask extends Task {

    public EnterCaveTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        RS2Object caveEntrance = api.getObjects().closest("Cavern");
        return caveEntrance != null && caveEntrance.isVisible() && !isInCave();
    }

    @Override
    public void process() throws InterruptedException {
        RS2Object caveEntrance = api.getObjects().closest("Cavern");

        api.camera.toEntity(caveEntrance);

        if (caveEntrance.interact("Enter")) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return isInCave();
                }
            }.sleep();
        }
    }

    boolean isInCave() {
        return api.myPosition().getY() <= 10235 && api.myPosition().getY() > 10139;
    }
}
