import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class TeleportingToLavaMazeTask extends Task {

    public TeleportingToLavaMazeTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return api.inventory.contains("Burning amulet(5)")
                && api.myPosition().getY() <= 3755
                && api.equipment.isWearingItem(EquipmentSlot.HANDS, "Bracelet of ethereum");
    }

    @Override
    public void process() throws InterruptedException {
        RS2Widget teleportLavaMaze = api.getWidgets().get(219, 1, 3);

        if (teleportLavaMaze == null) {
            if (api.inventory.interact("Rub", "Burning amulet(5)")) {
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
                        return api.getWidgets().get(219, 1, 3) == null;
                    }
                }.sleep();
            }
            RS2Widget confirmTeleport = api.getWidgets().get(219, 1, 1);
            if (confirmTeleport != null) {
                if (confirmTeleport.interact("Continue")) {
                    new ConditionalSleep(2000) {
                        public boolean condition() throws InterruptedException {
                            return api.myPosition().getY() > 3755;
                        }
                    }.sleep();
                }
            }
        }
    }
}

