package nome.Tone.BANK.v1.models;

import java.util.UUID;

public class Account {

    private final String uuid;
    private String email;
    private int saldo;

    public Account(final String email, final int saldo) {
        this.uuid = UUID.randomUUID().toString();
        this.email = email;
        this.saldo = saldo;
    }

    public String getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(final int saldo) {
        this.saldo = saldo;
    }

    public static Account from(final Account data) {
        return new Account(data.getEmail(), data.getSaldo());
    }

    @Override
    public String toString() {
        return "Account{" +
                "uuid=" + uuid +
                ", email='" + email + '\'' +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Account that = (Account) obj;
        if (!uuid.equalsIgnoreCase(that.uuid)) {
            return false;
        }
        if (!email.equalsIgnoreCase(that.email)) {
            return false;
        }
        if (saldo != that.saldo) {
            return false;
        }
        return true;
    }
}
