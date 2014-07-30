package org.progress.crm.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.progress.crm.logic.DbFields;
import org.progress.crm.logic.Planner;

public class PlannerDao {

    public List<Planner> getTasksByWorker(Session session, int idWorker) throws SQLException {
        List<Planner> list = session.createCriteria(Planner.class).
                add(Restrictions.eq(DbFields.PLANNER.IDWORKER, idWorker)).
                add(Restrictions.eq(DbFields.PLANNER.DELETED, false)).
                addOrder(Order.desc(DbFields.PLANNER.CREATIONDATE)).list();
        return list;
    }

    public List<Planner> getTasks(Session session) throws SQLException {
        List<Planner> list = session.createCriteria(Planner.class).
                add(Restrictions.eq(DbFields.PLANNER.DELETED, false)).
                addOrder(Order.desc(DbFields.PLANNER.CREATIONDATE)).list();
        return list;
    }

    public void addTask(final Session session, final int idWorker, final String taskClass,
            final int taskId, final String taskTitle, final String taskDescription, final Date taskStartDate,
            final Date taskEndDate) throws SQLException {
        session.save(new Planner(idWorker, taskClass, taskId, taskTitle, taskDescription, taskStartDate, taskEndDate));
    }

    public Planner getTaskById(final Session session, final int taskId) throws SQLException {
        return (Planner) session.get(Planner.class, taskId);
    }

    public void editTaskById(final Session session, final int plannerId, final int idWorker, final String taskClass,
            final int taskId, final String taskTitle, final String taskDescription, final Date taskStartDate,
            final Date taskEndDate) throws SQLException {
        Planner task = getTaskById(session, plannerId);
        task.setIdWorker(idWorker);
        task.setTaskClass(taskClass);
        task.setTaskId(taskId);
        task.setTaskTitle(taskTitle);
        task.setTaskDescription(taskDescription);
        task.setTaskStartDate(taskStartDate);
        task.setTaskEndDate(taskEndDate);
        session.update(task);
    }

    public void removeTaskById(Session session, int idWorker, int plannerId) throws SQLException {
        Planner task = getTaskById(session, plannerId);
        task.setDeleted(true);
        session.update(task);
    }
}
