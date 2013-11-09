package org.progress.crm.controllers;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.crm.dao.DaoFactory;
import org.progress.crm.exceptions.BadRequestException;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.exceptions.IncorrectPasswordException;
import org.progress.crm.exceptions.IsNotAuthenticatedException;
import org.progress.crm.exceptions.PermissionsDeniedException;
import org.progress.crm.logic.Permissions;
import org.progress.crm.logic.Workers;
import org.progress.crm.util.SHA1;

@Singleton
public class WorkersController {

    @EJB
    AuthenticationManager authManager;
    @EJB
    RoleController roleController;

    public Workers getProfileInfo(Session session, String token) throws SQLException, CustomException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }

        UUID uuid = UUID.fromString(token);
        if (authManager.isAuthentificated(uuid)) {
            int userId = authManager.getUserIdByToken(uuid);
            Workers pr = DaoFactory.getWorkersDao().getWorkerById(session, userId);
            if (pr == null) {
                throw new IsNotAuthenticatedException();
            }
            return pr;
        }
        throw new IsNotAuthenticatedException();
    }

    public void changePwd(Session session, String token, String oldPwd, String newPwd)
            throws NoSuchAlgorithmException, SQLException, CustomException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        if (oldPwd == null || newPwd == null) {
            throw new BadRequestException();
        }

        UUID uuid = UUID.fromString(token);
        int userId = authManager.getUserIdByToken(uuid);
        Workers pr = DaoFactory.getWorkersDao().getWorkerById(session, userId);

        String oldUserPwdHash = SHA1.sha1(oldPwd);
        if (!(pr.getPwdhash().equals(oldUserPwdHash))) {
            throw new IncorrectPasswordException();
        }

        pr.setPwdhash(SHA1.sha1(newPwd));
        updateWorker(session, pr);
    }

    public void updateWorker(Session session, Workers worker) throws SQLException {
        DaoFactory.getWorkersDao().updateWorker(session, worker);
    }

    public List<Workers> getAllUsers(Session session, String token) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        //FIXME
//        if (!roleController.checkPermissions(session, token, Permissions.ADMIN)) {
//            throw new PermissionsDeniedException();
//        }
        List<Workers> workers = DaoFactory.getWorkersDao().getAllWorkers(session);
        List list = new ArrayList();
        for (Workers ws : workers) {
            List ll = new ArrayList();
            ll.add(ws.getId());
            ll.add(ws.getfName());
            ll.add(ws.getlName());
            ll.add(ws.getmName());
            list.add(ll);
        }
        return list;
    }
}
