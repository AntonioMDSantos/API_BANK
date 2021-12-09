package nome.Tone.BANK.v1.dao;

import com.google.gson.Gson;
import nome.Tone.BANK.v1.models.Account;
import nome.Tone.BANK.v1.models.Error;
import nome.Tone.BANK.v1.models.Transfer;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class TransferenciaDao {

    private static final List<Transfer> DATA = new ArrayList<>();

    public static Object addTransferencia(final Request request, final Response response) {
        final Transfer data = new Gson().fromJson(request.body(), Transfer.class);
        if (data == null) {
            return Error.from(400);
        }
        final Account from = ContaDao.getConta(data.getFrom());
        final Account to = ContaDao.getConta(data.getTo());
        if (from == null || to == null) {
            return Error.from(404);
        }
        final int fromSaldo = from.getSaldo();
        final int toSaldo = to.getSaldo();
        from.setSaldo(fromSaldo - data.getAmount());
        to.setSaldo(toSaldo + data.getAmount());
        if (ContaDao.update(from) && ContaDao.update(to)) {
            final Transfer transfer = Transfer.from(data);
            DATA.add(transfer);
            return transfer;
        } else {
            from.setSaldo(fromSaldo);
            to.setSaldo(toSaldo);
            ContaDao.update(from);
            ContaDao.update(to);
            return Error.from(500);
        }
    }

    public static List<Transfer> getTransferencia(final Request request, final Response response) {
        return DATA;
    }

    public static Object getTransfer(final Request request, final Response response) {
        final String uuid = request.params("uuid");
        final Transfer transfer = getTransfer(uuid);
        if (transfer == null) {
            return Error.from(404);
        }
        return transfer;
    }

    public static Transfer getTransfer(final String uuid) {
        for (Transfer transfer: DATA) {
            if (transfer.getUuid().equalsIgnoreCase(uuid)) {
                return transfer;
            }
        }
        return null;
    }
}
