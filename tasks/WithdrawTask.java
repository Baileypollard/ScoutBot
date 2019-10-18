package tasks;

import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.script.MethodProvider;
import org.osbot.rs07.utility.ConditionalSleep;
import tasks.Task;

public class WithdrawTask extends Task {

    public WithdrawTask(MethodProvider api, String name) {
        super(api, name);
    }

    @Override
    public boolean canProcess() {
        return api.bank.isOpen() && (!api.inventory.contains("Burning amulet(5)",
                "Bracelet of ethereum (uncharged)", "Revenant ether"));
    }

    @Override
    public void process() throws InterruptedException {
        if (api.bank.withdraw("Burning amulet(5)", 1)) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return !api.inventory.contains("Burning amulet(5)");
                }
            }.sleep();
        }
        if (api.bank.withdraw("Bracelet of ethereum (uncharged)", 1)) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return !api.inventory.contains("Bracelet of ethereum (uncharged)");
                }
            }.sleep();
        }
        if (api.bank.withdraw("Revenant ether", 10)) {
            new ConditionalSleep(2000) {
                public boolean condition() throws InterruptedException {
                    return !api.inventory.contains("Revenant ether");
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

        if (api.inventory.contains("Revenant ether", "Bracelet of ethereum (uncharged)")) {
            api.inventory.interact("Use", "Revenant ether");
            if (api.inventory.interact("Use", "Bracelet of ethereum (uncharged)")) {
                new ConditionalSleep(2000) {
                    public boolean condition() throws InterruptedException {
                        return !api.inventory.contains("Bracelet of ethereum");
                    }
                }.sleep();
            }
        }
    }
}
