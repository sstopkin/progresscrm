package org.progress.crm.controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.crm.dao.DaoFactory;
import org.progress.crm.exceptions.IsNotAuthenticatedException;

@Singleton
public class ReportGeneratorController {

    @EJB
    AuthenticationManager authManager;

    public File getPrice(Session session, String token) throws IsNotAuthenticatedException {
//        if (token == null) {
//            throw new IsNotAuthenticatedException();
//        }
//        UUID uuid = UUID.fromString(token);
//        int idWorker = authManager.getUserIdByToken(uuid);
        Date curDate = new Date();
        File result = DaoFactory.getReportGeneratorDao().reportGen(curDate);
        return result;
    }
}
