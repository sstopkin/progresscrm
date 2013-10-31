package org.progress.crm.dao;

import java.util.List;
import org.hibernate.Session;
import org.progress.crm.exceptions.CustomException;
import org.progress.crm.logic.Customers;

public class CustomersDao {

    public int addCustomer(final Session session, final String fName, final String lName,
            final String mName, final int customersMonthOfBirthday, final int customersDayOfBirthday,
            final int customersYearOfBirthday, final int customersSex, final String customersPhone,
            final String customersEmail, final String customersAddress, final String customersExtra) throws CustomException {
        return (int) session.save(new Customers(fName, lName, mName, customersMonthOfBirthday,
                customersDayOfBirthday, customersYearOfBirthday, customersSex, customersPhone, customersEmail, customersAddress, customersExtra));
    }

    public boolean removeCustomerById(final Session session, final int customerId) throws CustomException {
        Customers customer = getCustomerById(session, customerId);
        session.delete(customer);
        return true;
    }

    public Customers getCustomerById(final Session session, final int customerId) throws CustomException {
        return (Customers) session.get(Customers.class, customerId);
    }

    public boolean modifyCustomer(final Session session, final Customers customers) throws CustomException {
        session.update(customers);
        return true;
    }

    public List<String> findCustomerByStr(final Session session, final String str) throws CustomException {
        List list = session.createSQLQuery("SELECT * FROM progresscrm.Customers WHERE LName like '%" + str + "%'").addEntity(Customers.class).list();
        return list;
    }
}
