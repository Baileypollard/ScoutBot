import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

public class WalkToLumbridgeStairsTask extends Task {

    Position stairsPosition = new Position(3205, 3209, 0);

    public WalkToLumbridgeStairsTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        RS2Object stairs = api.getObjects().closest(16671);
        return Loc.LUMBRIDGE_GROUND_FLOOR.getArea().contains(api.myPosition()) && !stairs.isVisible();
    }

    @Override
    public void process() throws InterruptedException {
        api.walking.webWalk(stairsPosition);
    }


}