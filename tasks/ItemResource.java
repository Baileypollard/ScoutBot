import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

class ItemResource {

    private String name;
    private String url;

    private int id;
    private int price;
    private int amount;

    public ItemResource(String name, int id) {

        this.url = "http://services.runescape.com/m=itemdb_oldschool/api/catalogue/detail.json?item=" + id;
        this.name = name;
        this.id = id;
        this.price = fetchPrice();

    }

    // Getters

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    // Setters

    public void setAmount(int i) {
        amount = amount + i;
    }

    // Reset Amount

    public void resetAmount() {
        amount = 0;
    }

    private int fetchPrice() {
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream()))) {
            final String raw = reader.readLine().replace(",", "").replace("\"", "").split("price:")[1].split("}")[0];

            reader.close();
            return raw.endsWith("m") || raw.endsWith("k") ? (int) (Double.parseDouble(raw.substring(0, raw.length() - 1))
                    * (raw.endsWith("m") ? 1_000_000 : 1_000)) : Integer.parseInt(raw);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}