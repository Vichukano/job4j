package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.CustomerDao;
import ru.job4j.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CustomerRepository implements CustomerDao {
    private final DbStore store = DbStore.getStoreInstance();
    private static final CustomerDao INSTANCE = new CustomerRepository();
    private final Logger logger = LogManager.getLogger(CustomerRepository.class);

    private CustomerRepository() {

    }

    public static CustomerDao getCustomerStoreInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Customer model) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "INSERT INTO customers(name, phone, place_id, row, col) "
                             + "VALUES (?, ?, ?, ?, ?);"
             )
        ) {
            st.setString(1, model.getName());
            st.setString(2, model.getPhone());
            st.setInt(3, model.getPlaceId());
            st.setInt(4, model.getRow());
            st.setInt(5, model.getCol());
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("Customer {} added", model.toString());
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "DELETE FROM customers "
                             + "WHERE id = ?;"
             )
        ) {
            st.setInt(1, id);
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("Customer with id = {} deleted", id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Customer findById(int id) {
        Customer customer = null;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM customers "
                             + "LEFT OUTER JOIN places_default "
                             + "ON customers.place_id = places_default.id "
                             + "WHERE customers.id = ?;"
             )
        ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer tmpCustomer = new Customer(
                        rs.getString("name"),
                        rs.getString("phone")
                );
                tmpCustomer.setId(rs.getInt("id"));
                tmpCustomer.setPlaceId(rs.getInt("place_id"));
                tmpCustomer.setRow(rs.getInt("row"));
                tmpCustomer.setCol(rs.getInt("col"));
                customer = tmpCustomer;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> tmp = new CopyOnWriteArrayList<>();
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM customers "
                     + "LEFT OUTER JOIN places_default p "
                     + "ON customers.place_id = p.id;"
             );
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("name"),
                        rs.getString("phone")
                );
                customer.setId(rs.getInt("id"));
                customer.setPlaceId(rs.getInt("place_id"));
                customer.setRow(rs.getInt("row"));
                customer.setCol(rs.getInt("col"));
                tmp.add(customer);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tmp;
    }
}
