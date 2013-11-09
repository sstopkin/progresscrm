package org.progress.crm.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.progress.crm.controllers.AuthenticationManager;
import org.progress.crm.controllers.RoleController;
import org.progress.crm.controllers.WorkersController;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.util.Command;
import org.progress.crm.util.TransactionService;

@Stateless
@Path("auth")
public class AuthApi {

    @EJB
    AuthenticationManager authManager;
    @EJB
    RoleController roleController;
    @EJB
    WorkersController workersController;

    @GET
    @Path("validate")
    public Response validation(@CookieParam("token") final String token)
            throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException {
                int success = roleController.getPermissions(session, token);
                return ApiHelper.getResponse(success);
            }
        });
    }

    @GET
    @Path("author")
    public Response validateAuthor(@CookieParam("token") String token)
            throws SQLException, CustomException {
        int succes = roleController.getUserIdByToken(token);
        return ApiHelper.getResponse(succes);
    }

    @GET
    public Response getStatus(@CookieParam("token") final String token)
            throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException {
                String success = authManager.getStatus(session, token);
                return ApiHelper.getResponse(success);
            }
        });
    }

    @POST
    @Path("login")
    public Response logIn(@FormParam("login") final String theEmail,
            @FormParam("pass") final String pass) throws NoSuchAlgorithmException,
            SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException,
                    NoSuchAlgorithmException {
                String success = authManager.authUser(session, theEmail, pass);
                return ApiHelper.getResponse(success);
            }
        });
    }

    @GET
    @Path("logout")
    public Response logOut(@CookieParam("token") String token) throws CustomException {
        boolean success = authManager.logOut(token);
        return ApiHelper.getResponse(success);
    }

    @GET
    @Path("info")
    public Response getUserInfo(@CookieParam("token") final String token)
            throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException {
                Gson userProfile = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String profileJson = userProfile.toJson(workersController
                        .getProfileInfo(session, token));
                return ApiHelper.getResponse(profileJson);
            }
        });
    }

    @POST
    @Path("chngpwd")
    public Response changePassword(@CookieParam("token") final String token,
            @FormParam("oldpwd") final String oldPwd, @FormParam("newpwd") final String newPwd)
            throws NoSuchAlgorithmException, SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException,
                    NoSuchAlgorithmException {
                workersController.changePwd(session, token, oldPwd, newPwd);
                return ApiHelper.getResponse(true);
            }
        });
    }

    @GET
    @Path("userslist")
    public Response getAllUsersList(@CookieParam("token") final String token)
            throws SQLException, CustomException {
        return TransactionService.runInScope(new Command<Response>() {
            @Override
            public Response execute(Session session) throws CustomException, SQLException {
                Gson allUsersList = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                String profileJson = allUsersList.toJson(workersController
                        .getAllUsers(session, token));
                return ApiHelper.getResponse(profileJson);
            }
        });
    }
}
