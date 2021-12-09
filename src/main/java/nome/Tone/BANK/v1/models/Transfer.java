package nome.Tone.BANK.v1.models;

import java.util.UUID;

public class Transfer {

    private final String uuid;
    private final String from;
    private final String to;
    private final int amount;

    public Transfer(final String from, final String to, final int amount) {
        this.uuid = UUID.randomUUID().toString();
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getAmount() {
        return amount;
    }

    public static Transfer from(final Transfer data) {
        return new Transfer(data.getFrom(), data.getTo(), data.getAmount());
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "uuid=" + uuid +
                ", from=" + from +
                ", to=" + to +
                ", amount=" + amount +
                '}';
    }
}
