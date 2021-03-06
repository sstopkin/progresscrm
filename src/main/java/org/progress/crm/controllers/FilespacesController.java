package org.progress.crm.controllers;

import java.io.File;
import java.sql.SQLException;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.crm.dao.DaoFactory;
import org.progress.crm.exceptions.BadRequestException;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.exceptions.IsNotAuthenticatedException;
import org.progress.crm.logic.Apartaments;
import org.progress.crm.logic.Constants;
import org.progress.crm.logic.Filespaces;

@Singleton
public class FilespacesController {

    public String createFilespace(Session session, String token, String targetUUID, String type) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        String ret = "";

        if (Integer.valueOf(type) == 1) {
            Apartaments aparts = DaoFactory.getApartamentsDao().getApartamentsByUUID(session, targetUUID);
            Filespaces fs = new Filespaces(aparts.getCityName() + " " + aparts.getStreetName() + " " + aparts.getHouseNumber() + " " + aparts.getBuildingNumber());
            session.save(fs);
            aparts.setFilespaceUUID(fs.getFilespacesUUID());
            session.update(aparts);
            File myPath = new File(Constants.SETTINGS.BASEPATH + aparts.getFilespaceUUID());
            myPath.mkdirs();
            myPath = new File(Constants.SETTINGS.BASEPATH + aparts.getFilespaceUUID() + "/gallery");
            myPath.mkdirs();
            ret = myPath.toString().replace(Constants.SETTINGS.BASEPATH, "");
        }
        return ret;
    }

    public String getFilespacePathByTargetUUID(Session session, String token, String uuid) throws BadRequestException, CustomException, SQLException {
        if (uuid == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getFilespacesDao().getFilespacePathByUUID(session, uuid);
    }

}
