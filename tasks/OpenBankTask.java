package tasks;

import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.MethodProvider;
import tasks.Task;

public class OpenBankTask extends Task {

    public OpenBankTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return Loc.LUMBRIDGE_UPPER_FLOOR.getArea().contains(api.myPosition())
                && !api.inventory.contains("Revenant cave teleport");
    }

    @Override
    public void process() throws InterruptedException {
        if (api.walking.webWalk(Banks.LUMBRIDGE_UPPER.getRandomPosition())) {
            if (!api.bank.isOpen()) {
                api.bank.open();
            }
        }
    }
}
