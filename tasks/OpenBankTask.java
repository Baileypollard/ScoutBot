import org.osbot.rs07.api.map.constants.Banks;
import org.osbot.rs07.script.MethodProvider;

public class OpenBankTask extends Task {

    public OpenBankTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return Loc.LUMBRIDGE_UPPER_FLOOR.getArea().contains(api.myPosition())
                && !api.inventory.contains("Burning amulet(5)");
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
