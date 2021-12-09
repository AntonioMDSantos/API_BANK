package nome.Tone.BANK.v1.dao;

import com.google.gson.Gson;
import nome.Tone.BANK.v1.models.Error;
import spark.Request;
import spark.Response;

public class ErrorDao {

    public static String notFound(final Request request, final Response response) {
        return new Gson().toJson(Error.from(404));
    }

    public static String internalServerError(final Request request, final Response response) {
        return new Gson().toJson(Error.from(500));
    }
}
