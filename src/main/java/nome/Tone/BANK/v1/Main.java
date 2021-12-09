package nome.Tone.BANK.v1;

import com.google.gson.Gson;
import nome.Tone.BANK.v1.dao.ContaDao;
import nome.Tone.BANK.v1.dao.ErrorDao;
import nome.Tone.BANK.v1.dao.TransferenciaDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.*;

public class Main {

    private static final Logger L = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        before((req, res) -> res.type("application/json"));
        setEndpoints();
        afterAfter("/*", (req, res) -> L.info("{} \"{} {}\" {} {}", req.ip(), req.requestMethod(), req.uri(), req.protocol(), res.status()));
    }

    private static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

    private static void setEndpoints() {
        Gson gson = new Gson();
        post("/api/v1/contas"        , "application/json", ContaDao::addConta   , gson::toJson);
        post("/api/v1/contas/"       , "application/json", ContaDao::addConta   , gson::toJson);
        get ("/api/v1/contas"        , "application/json", ContaDao::getContas  , gson::toJson);
        get ("/api/v1/contas/"       , "application/json", ContaDao::getContas  , gson::toJson);
        get ("/api/v1/contas/:uuid"  , "application/json", ContaDao::getConta   , gson::toJson);
        get ("/api/v1/contas/:uuid/" , "application/json", ContaDao::getConta   , gson::toJson);
        post("/api/v1/transferencia"       , "application/json", TransferenciaDao::addTransferencia , gson::toJson);
        post("/api/v1/transferencia/"      , "application/json", TransferenciaDao::addTransferencia , gson::toJson);
        get ("/api/v1/transferencia"       , "application/json", TransferenciaDao::getTransferencia, gson::toJson);
        get ("/api/v1/transferencia/"      , "application/json", TransferenciaDao::getTransferencia, gson::toJson);
        get ("/api/v1/transferencia/:uuid" , "application/json", TransferenciaDao::getTransfer , gson::toJson);
        get ("/api/v1/transferencia/:uuid/", "application/json", TransferenciaDao::getTransfer , gson::toJson);
        notFound(ErrorDao::notFound);
        internalServerError(ErrorDao::internalServerError);
    }
}