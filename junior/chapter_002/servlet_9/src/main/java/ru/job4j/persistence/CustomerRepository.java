package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.CustomerDao;
import ru.job4j.entity.Customer;
import ru.job4j.entity.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singleton class for crud methods with customer and place object.
 */
public class CustomerRepository implements CustomerDao {
    private final DbStore store = DbStore.getStoreInstance();
    private static final CustomerDao INSTANCE = new CustomerRepository();
    private final static Logger LOG = LogManager.getLogger(CustomerRepository.class);

    private CustomerRepository() {

    }

    public static CustomerDao getCustomerStoreInstance() {
        return INSTANCE;
    }

    /**
     * Method for adding customer and place entities in database in one transaction.
     * @param customer customer object.
     * @param place place object.
     * @return true id added, else false.
     */
    @Override
    public boolean addCustomerWithPlace(Customer customer, Place place) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement customerSt = con.prepareStatement(
                     "INSERT INTO customers(name, phone, place_id, row, col) "
                             + "VALUES (?, ?, ?, ?, ?);"
             );
             PreparedStatement placeSt = con.prepareStatement(
                     "UPDATE places_default "
                             + "SET "
                             + "reserved = ? "
                             + "WHERE id = ?;"
             )

        ) {
            try {
                con.setAutoCommit(false);
                customerSt.setString(1, customer.getName());
                customerSt.setString(2, customer.getPhone());
                customerSt.setInt(3, customer.getPlaceId());
                customerSt.setInt(4, place.getRow());
                customerSt.setInt(5, place.getCol());
                placeSt.setBoolean(1, true);
                placeSt.setInt(2, customer.getPlaceId());
                if (customerSt.executeUpdate() > 0 && placeSt.executeUpdate() > 0) {
                    con.commit();
                    result = true;
                    LOG.debug("{} added with {}", customer.toString(), place.toString());
                }
            } catch (SQLException e) {
                con.rollback();
                LOG.error(e.getMessage());
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    /**
     * Method for deleting customer and setting unreserved place parameter in database.
     * @param id id of customer for deleting.
     * @return true id deleted, else false.
     */
    @Override
    public boolean deleteCustomerWithPlace(int id) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement getPlaceIdSt = con.prepareStatement(
                     "SELECT * FROM customers "
                             + "LEFT OUTER JOIN places_default "
                             + "ON customers.place_id = places_default.id "
                             + "WHERE customers.id = ?;"
             );
             PreparedStatement customerSt = con.prepareStatement(
                     "DELETE FROM customers "
                             + "WHERE id = ?;"
             );
             PreparedStatement placeSt = con.prepareStatement(
                     "UPDATE places_default "
                             + "SET reserved = ? "
                             + "WHERE id = ?;"
             )
        ) {
            try {
                con.setAutoCommit(false);
                int placeId = 0;
                getPlaceIdSt.setInt(1, id);
                ResultSet rs = getPlaceIdSt.executeQuery();
                while (rs.next()) {
                    placeId = rs.getInt("place_id");
                }
                customerSt.setInt(1, id);
                placeSt.setBoolean(1, false);
                placeSt.setInt(2, placeId);
                if (customerSt.executeUpdate() > 0 && placeSt.executeUpdate() > 0) {
                    con.commit();
                    result = true;
                    LOG.debug("Customer with id = {} deleted", id);
                }
            } catch (SQLException e) {
                con.rollback();
                LOG.error(e.getMessage());
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return result;
    }

    /**
     * Method for finding customer with id parameter in database.
     * @param id id of customer for finding.
     * @return customer object with this id.
     */
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
            LOG.error(e.getMessage());
        }
        return customer;
    }

    /**
     * Method for finding all customers stored in database.
     * @return list of customers.
     */
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
            LOG.error(e.getMessage());
        }
        return tmp;
    }
}
