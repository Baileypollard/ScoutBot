import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.MethodProvider;

import java.util.Arrays;

public class WalkToCaveTask extends Task
{
    Position[] pathToCave = { new Position(3031,3837,0), new Position(3037,3827,0),
            new Position(3045,3816,0),  new Position(3057,3821,0),
            new Position(3069,3809,0), new Position(3079,3798,0),
            new Position(3094,3801,0), new Position(3100,3795,0),
            new Position(3112,3804,0), new Position(3120,3810,0),
            new Position(3128,3817,0), new Position(3132,3829,0)};

    public WalkToCaveTask(MethodProvider api, String name)
    {
        super(api, name);
    }

    @Override
    public boolean canProcess()
    {
        RS2Object caveEntrance = api.getObjects().closest("Cavern");
        return (api.myPosition().getX() > 3020 && api.myPosition().getX() < 3230)
                && (api.myPosition().getY() > 3750 && api.myPosition().getY() < 3900) && caveEntrance == null;
    }

    @Override
    public void process() throws InterruptedException
    {
        api.camera.moveYaw(172);

        if (api.walking.walkPath(Arrays.asList(pathToCave))) {
            api.log("DONE WALKING...");
        }
    }
}
