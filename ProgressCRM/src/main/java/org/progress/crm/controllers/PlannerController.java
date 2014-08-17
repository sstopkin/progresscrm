package org.progress.crm.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.crm.dao.DaoFactory;
import org.progress.crm.exceptions.BadLogInException;
import org.progress.crm.exceptions.BadRequestException;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.exceptions.IsNotAuthenticatedException;
import org.progress.crm.logic.Planner;

@Singleton
public class PlannerController {

    @EJB
    AuthenticationManager authenticationManager;

    public class event {

        public String id;
        public String title;
        public String url;
        public String Class;
        public String start;
        public String end;

        public event(String id, String title, String url, String Class, String start, String end) {
            this.id = id;
            this.title = title;
            this.url = url;
            this.Class = Class;
            this.start = start;
            this.end = end;
        }
    }

    public class succ {

        public String success;
        public List<event> result;

        public succ() {
            this.success = "1";
            this.result = new ArrayList();
        }

        public void ret(event e) {
            this.result.add(e);
        }
    }

    public List<Planner> getTasks(Session session, String token) throws SQLException, CustomException {
        if (token == null) {
            throw new CustomException();
        }
        UUID uuid = UUID.fromString(token);
        int idWorker = authenticationManager.getUserIdByToken(uuid);
        return DaoFactory.getPlannerDao().getTasksByWorker(session, idWorker);
    }

    public succ getTasksByWorker(Session session, String token) throws SQLException, CustomException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        UUID uuid = UUID.fromString(token);
        int idWorker = authenticationManager.getUserIdByToken(uuid);
        List<Planner> tasks = DaoFactory.getPlannerDao().getTasksByWorker(session, idWorker);
        succ s = new succ();

//        int id, String title, String url, String Class, String start, String end
//        new event(295, "2", "3", "event-success", "1364320800000", "1364407286400")
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("Asia/Omsk"));
        for (Planner obj : tasks) {
            c.setTime(obj.getTaskStartDate());
            String startDate = String.valueOf(c.getTimeInMillis());
            c.setTime(obj.getTaskEndDate());
            String endDate = String.valueOf(c.getTimeInMillis());
            s.ret(new event(obj.getTargetOjectUUID(), obj.getTaskTitle() + " " + obj.getTaskStartDate().toString(), "url", obj.getTaskClass(), startDate, endDate));
        }
        return s;
    }

    public boolean addTask(Session session, String token, String taskClass,
            String targetObjectUUID, String taskTitle, String taskDescription, String taskStartDate, String taskEndDate) throws CustomException, SQLException {
        if (token == null) {
            throw new CustomException();
        }
        UUID uuid = UUID.fromString(token);
        int idWorker = authenticationManager.getUserIdByToken(uuid);
        DaoFactory.getPlannerDao().addTask(session, idWorker, taskClass, targetObjectUUID, taskTitle, taskDescription, null,
                null);
        return true;
    }

    public boolean deleteTaskById(Session session, String token, String id) throws CustomException, BadRequestException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new CustomException();
        }
        UUID uuid = UUID.fromString(token);
        int idWorker = authenticationManager.getUserIdByToken(uuid);
        DaoFactory.getPlannerDao().removeTaskById(session, idWorker, Integer.valueOf(id));
        return true;
    }

    public boolean editNewsById(Session session, String token, String plannerId, String taskType,
            String taskId, String taskDescription, String taskDate) throws CustomException, BadRequestException {
        if (plannerId == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new CustomException();
        }
        UUID uuid = UUID.fromString(token);
        int idWorker = authenticationManager.getUserIdByToken(uuid);
//        DaoFactory.getPlannerDao().editTaskById(session, Integer.valueOf(plannerId),
//                idWorker, Integer.valueOf(taskType), Integer.valueOf(taskId), taskDescription, taskDate);
        return true;
    }
}
