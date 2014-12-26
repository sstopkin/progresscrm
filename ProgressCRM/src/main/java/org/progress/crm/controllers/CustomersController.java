package org.progress.crm.controllers;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Singleton;
import org.hibernate.Session;
import org.progress.crm.dao.DaoFactory;
import org.progress.crm.exceptions.BadRequestException;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.exceptions.IsNotAuthenticatedException;
import org.progress.crm.logic.Customers;
import org.progress.crm.util.ParamName;
import org.progress.crm.util.ParamUtil;

@Singleton
public class CustomersController {

    public Customers getCustomerById(Session session, String token, String customerId) throws CustomException, SQLException {
        if (customerId == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getCustomersDao().getCustomerById(session, Integer.valueOf(customerId));
    }

    public List getCustomerObjectsById(Session session, String token, String customerId) throws CustomException, SQLException {
        if (customerId == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getCustomersDao().getCustomerObjectsById(session, Integer.valueOf(customerId));
    }

    public boolean addCustomer(Session session, String token, Map<String, String> map) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        String fName = map.get(ParamName.CUSTOMERS_FNAME);
        String lName = map.get(ParamName.CUSTOMERS_LNAME);
        String mName = map.get(ParamName.CUSTOMERS_MNAME);
        Date customersDateOfBirthday = ParamUtil.getDate(map, ParamName.CUSTOMERS_DATE_OF_BIRTHDAY, null);
        int customersSex = ParamUtil.getInt(map, ParamName.CUSTOMERS_SEX);
        String customersPhone = map.get(ParamName.CUSTOMERS_PHONE);
        String customersEmail = map.get(ParamName.CUSTOMERS_EMAIL);
        String customersAddress = map.get(ParamName.CUSTOMERS_ADDRESS);
        String customersExtra = map.get(ParamName.CUSTOMERS_EXTRA);
        int status = ParamUtil.getInt(map, ParamName.CUSTOMERS_STATUS);

        DaoFactory.getCustomersDao().addCustomer(session,
                fName,
                lName,
                mName,
                customersDateOfBirthday,
                customersSex,
                customersPhone,
                customersEmail,
                customersAddress,
                customersExtra,
                status
        );
        return true;
    }

    public boolean removeCustomer(Session session, String token, String id) throws CustomException, SQLException {
        if (id == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        DaoFactory.getCustomersDao().removeCustomerById(session, Integer.valueOf(id));
        return true;
    }

    public List getCustomerByString(Session session, String token, String str) throws CustomException, SQLException {
        if (str == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getCustomersDao().findCustomerByStr(session, str);
    }

    public boolean editCustomer(Session session, String token, Map<String, String> map) throws CustomException, SQLException {
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        int customerId = ParamUtil.getInt(map, ParamName.CUSTOMERS_ID);
        Customers customers = DaoFactory.getCustomersDao().getCustomerById(session, customerId);
        customers.setCustomersFname(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_FNAME));
        customers.setCustomersMname(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_MNAME));
        customers.setCustomersLname(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_LNAME));
        customers.setCustomersDateOfBirthday(ParamUtil.getDate(map, ParamName.CUSTOMERS_DATE_OF_BIRTHDAY, null));
        customers.setCustomersPhone(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_PHONE));
        customers.setCustomersEmail(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_EMAIL));
        customers.setCustomersAddress(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_ADDRESS));
        customers.setCustomersExtra(ParamUtil.getNotNull(map, ParamName.CUSTOMERS_EXTRA));

        DaoFactory.getCustomersDao().modifyCustomer(session, customers);
        return true;
    }

    public List getCustomersListByQuery(Session session, String token, String str) throws CustomException, SQLException {
        if (str == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getCustomersDao().findCustomerByStr(session, str);
    }

    public List<Customers> getAllCustomers(Session session, String token, String status) throws CustomException, SQLException {
        if (status == null) {
            throw new BadRequestException();
        }
        if (token == null) {
            throw new IsNotAuthenticatedException();
        }
        return DaoFactory.getCustomersDao().getAllCustomers(session, Integer.valueOf(status));
    }

    public List<Customers> getCustomersListByBirthday(Session session, Date currentDay) throws SQLException {
        return DaoFactory.getCustomersDao().getCustomersListByBirthday(session, currentDay);
    }
}
