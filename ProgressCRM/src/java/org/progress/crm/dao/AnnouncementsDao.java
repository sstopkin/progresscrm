package org.progress.crm.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.logic.Announcements;
import org.progress.crm.logic.DbFields;

/**
 *
 * @author best
 */
public class AnnouncementsDao {

    public boolean addAnnouncements(final Session session, final int idWorker, final String street,
            final int rooms, final int floor, final int floors, final String phone, final String description) throws SQLException, CustomException {
        Announcements hd = new Announcements(street, rooms, floor, floors, phone, description, idWorker);
        session.save(hd);
        return true;
    }

    public boolean updateAnnouncements(final Session session, final Announcements announcements) throws SQLException, CustomException {
        session.update(announcements);
        return true;
    }

    public Announcements getAnnouncementsById(final Session session, final Integer annId) throws SQLException, CustomException {
        return (Announcements) session.get(Announcements.class, annId);
    }

    public List<Announcements> getAllAnnouncements(final Session session) throws SQLException, CustomException {
        List<Announcements> list = session.createCriteria(Announcements.class).list();
        return list;
    }

    public boolean deleteAnnouncements(Session session, int idWorker, Integer annId) throws SQLException, CustomException {
        Announcements ann = getAnnouncementsById(session, annId);
        ann.setDeleted(true);
        session.update(ann);
        return true;
    }

    public List getAnnouncementsListByQuery(Session session, String street, int rooms, int floor, int floors, int idWorker, Date startDate, Date endDate) {
        Criteria criteria = session.createCriteria(Announcements.class);
        if (!street.equals("")) {
            criteria.add(Restrictions.like(DbFields.ANNOUNCEMENTS.STREETS, street));
        }
        if (rooms != -1) {
            criteria.add(Restrictions.like(DbFields.ANNOUNCEMENTS.ROOMS, rooms));
        }
        if (floor != -1) {
            criteria.add(Restrictions.like(DbFields.ANNOUNCEMENTS.FLOOR, floor));
        }
        if (floors != -1) {
            criteria.add(Restrictions.like(DbFields.ANNOUNCEMENTS.FLOORS, floors));
        }
        if (idWorker != -1) {
            criteria.add(Restrictions.like(DbFields.ANNOUNCEMENTS.IDWORKER, idWorker));
        }
        if (startDate != null) {
            criteria.add(Restrictions.ge(DbFields.ANNOUNCEMENTS.CREATIONDATE, startDate));
        }
        if (endDate != null) {
            criteria.add(Restrictions.le(DbFields.ANNOUNCEMENTS.CREATIONDATE, endDate));
        }
        criteria.add(Restrictions.eq(DbFields.ANNOUNCEMENTS.DELETED, false));
        return criteria.list();
//.add(Restrictions.between("weight", minWeight, maxWeight))
    }
}
