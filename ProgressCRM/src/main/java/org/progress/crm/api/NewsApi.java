package org.progress.crm.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.progress.crm.controllers.NewsController;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.util.Command;
import org.progress.crm.util.TransactionService;

@Stateless
@Path("news")
public class NewsApi {

    @EJB
    NewsController newsController;

    @GET
    public Response news(@CookieParam("token") final String token) throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    Gson newsList = new GsonBuilder().create();
                    String newsJson = newsList.toJson(newsController
                            .getNews(session, token));
                    return ApiHelper.getResponse(newsJson);
                } catch (CustomException ex) {
                    Logger.getLogger(NewsApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @POST
    @Path("deletenews")
    public Response deleteHelpDeskRequest(@CookieParam("token") final String token,
            @FormParam("id") final String id) throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    boolean result = newsController.deleteNewsById(session, token, id);
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(NewsApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }

    @POST
    @Path("addnews")
    public Response editCourse(@CookieParam("token") final String token, @FormParam("id") final String id,
            @FormParam("header") final String header,
            @FormParam("text") final String text)
            throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws SQLException {
                try {
                    boolean result = newsController.addNews(session, token, header, text);
                    return ApiHelper.getResponse(result);
                } catch (CustomException ex) {
                    Logger.getLogger(NewsApi.class.getName()).log(Level.SEVERE, null, ex);
                    return ApiHelper.getResponse(ex);
                }
            }
        });
    }
}
