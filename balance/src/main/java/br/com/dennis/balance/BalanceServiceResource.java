package balance;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.WebApplicationException;


@Path("/balance")
public class BalanceServiceResource {

    @Inject
    BalanceRepository balanceRepository;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Balance getBalance(@PathParam("userId") Long userId) {
        Balance balance = balanceRepository.find("userId", userId).firstResult();
        if (balance != null) {
            return balance;
        } else {
            throw new WebApplicationException("Not found", 404);
        }
    }
}
