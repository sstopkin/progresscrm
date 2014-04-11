package org.progress.crm.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.progress.crm.controllers.CommentsController;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.util.Command;
import org.progress.crm.util.TransactionService;

@Stateless
@Path("comments")
public class CommentsApi {

    @EJB
    CommentsController commentsController;

    @GET
    @Path("getcomments")
    public Response getCommentByObjectId(@QueryParam("objectUUID") final String objectUUID,
            @CookieParam("token") final String token) throws CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException {
                Gson response = new GsonBuilder().create();
                String result = response.toJson(commentsController.getCommentsByObjectUUID(session, token, objectUUID));
                return ApiHelper.getResponse(result);
            }
        });
    }

    @POST
    @Path("addcomment")
    public Response addCall(@CookieParam("token") final String token,
            @FormParam("objectUUID") final String objectUUID,
            @FormParam("text") final String text) throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException {
                boolean result = commentsController.addCommentByObjectId(session, token, objectUUID, text);
                return ApiHelper.getResponse(result);
            }
        });
    }
}
