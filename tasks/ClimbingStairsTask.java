package tasks;

import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class ClimbingStairsTask extends Task {

    public ClimbingStairsTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        RS2Object stairs = api.getObjects().closest("Staircase");
        return !Loc.LUMBRIDGE_UPPER_FLOOR.getArea().contains(api.myPosition()) && (stairs != null && stairs.isVisible());
    }

    @Override
    public void process() throws InterruptedException {
        RS2Object stairs = api.getObjects().closest("Staircase");
        if (stairs.interact("Climb-up")) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return !Loc.LUMBRIDGE_GROUND_FLOOR.getArea().contains(api.myPosition());
                }
            }.sleep();
        }
    }
}
