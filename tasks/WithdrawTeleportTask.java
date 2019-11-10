import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;

public class WithdrawTeleportTask extends Task {

    public WithdrawTeleportTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return api.bank.isOpen() && !api.inventory.contains("Revenant cave teleport");
    }

    @Override
    public void process() throws InterruptedException {
        if (api.bank.withdraw("Revenant cave teleport", 1)) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return !api.inventory.contains("Revenant cave teleport");
                }
            }.sleep();
        }
        if (api.bank.isOpen()) {
            if (api.bank.close()) {
                new ConditionalSleep(2000) {
                    public boolean condition() throws InterruptedException {
                        return !api.bank.isOpen();
                    }
                }.sleep();
            }
        }
    }
}
