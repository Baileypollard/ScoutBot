package tasks;

import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class WearBraceletTask extends Task
{
    public WearBraceletTask(MethodProvider api, String name)
    {
        super(api, name);
    }

    @Override
    public boolean canProcess()
    {
        return api.inventory.contains("Bracelet of ethereum");
    }

    @Override
    public void process() throws InterruptedException
    {

        if (api.inventory.contains("Bracelet of ethereum")) {
            if (api.inventory.interact("Wear", "Bracelet of ethereum")) {
                new ConditionalSleep(2000) {
                    public boolean condition() throws InterruptedException {
                        return !api.equipment.isWearingItem(EquipmentSlot.HANDS, "Bracelet of ethereum");
                    }
                }.sleep();
            }
        }
    }
}
