package tasks;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.MethodProvider;

public class WalkToRevsTask extends Task {
    Position middleRevs = new Position(3243, 10169, 0);

    public WalkToRevsTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return isInCave() && !Loc.REV_SCOUT_AREA.getArea().contains(api.myPosition());
    }

    @Override
    public void process() throws InterruptedException {
        api.walking.webWalk(middleRevs);
        api.log(Loc.REV_SCOUT_AREA.getArea().contains(api.myPosition()));
    }

    boolean isInCave() {
        return api.myPosition().getY() <= 10235 && api.myPosition().getY() > 10139;
    }

}
