package nome.Tone.BANK.v1.dao;

import com.google.gson.Gson;
import nome.Tone.BANK.v1.models.Account;
import nome.Tone.BANK.v1.models.Error;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class ContaDao {

    private static final List<Account> DATA = new ArrayList<>();

    public static Object addConta(final Request request, final Response response) {
        final Account data = new Gson().fromJson(request.body(), Account.class);
        if (data == null) {
            return Error.from(400);
        }
        final Account account = Account.from(data);
        DATA.add(account);
        return account;
    }

    public static List<Account> getContas(final Request request, final Response response) {
        return DATA;
    }

    public static Object getConta(final Request request, final Response response) {
        final String uuid = request.params("uuid");
        final Account account =  getConta(uuid);
        if (account == null) {
            return Error.from(404);
        }
        return account;
    }

    public static Account getConta(final String uuid) {
        for (Account account: DATA) {
            if (account.getUuid().equalsIgnoreCase(uuid)) {
                return account;
            }
        }
        return null;
    }

    public static boolean update(final Account account) {
        for (Account persistedAccount: DATA) {
            if (persistedAccount.getUuid().equalsIgnoreCase(account.getUuid())) {
                persistedAccount.setSaldo(account.getSaldo());
                return true;
            }
        }
        return false;
    }
}
