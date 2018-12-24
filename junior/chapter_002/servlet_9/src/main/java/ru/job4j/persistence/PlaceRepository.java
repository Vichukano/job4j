package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlaceRepository implements Store<Place> {
    private final DbStore store = DbStore.getStoreInstance();
    private final Logger logger = LogManager.getLogger(PlaceRepository.class);


    @Override
    public boolean add(Place model) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "INSERT INTO places_default(row, col) "
                             + "VALUES (?, ?);"
             )
        ) {
            st.setInt(1, model.getRow());
            st.setInt(2, model.getCol());
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("Place {} added", model.toString());
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
                    "DELETE FROM places_default "
                    + "WHERE id = ?;"
            )
        ) {
            st.setInt(1, id);
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("Place with id = {} deleted", id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Place findById(int id) {
        Place place = null;
        try (Connection con = this.store.getConnection();
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM places_default "
                    + "WHERE id = ?;"
            )
        ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Place tmpPlace = new Place(
                        rs.getInt("row"),
                        rs.getInt("col")
                );
                tmpPlace.setId(rs.getInt("id"));
                tmpPlace.setCost(rs.getDouble("cost"));
                place = tmpPlace;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return place;
    }

    @Override
    public List<Place> findAll() {
        List<Place> tmp = new CopyOnWriteArrayList<>();
        try (Connection con = this.store.getConnection();
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM places_default;"
            );
            ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Place place = new Place(
                        rs.getInt("row"),
                        rs.getInt("col")
                );
                place.setId(rs.getInt("id"));
                place.setCost(rs.getDouble("cost"));
                tmp.add(place);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tmp;
    }

    @Override
    public List<Place> findAllReserved() {
        List<Place> tmp = new CopyOnWriteArrayList<>();
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM places_default "
                     + "WHERE reserved = TRUE; "
             );
             ResultSet rs = st.executeQuery();
        ) {
            while (rs.next()) {
                Place place = new Place(
                        rs.getInt("row"),
                        rs.getInt("col")
                );
                place.setId(rs.getInt("id"));
                place.setCost(rs.getDouble("cost"));
                tmp.add(place);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tmp;
    }

    @Override
    public boolean reservePlace(Place place) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "UPDATE places_default "
                             + "SET reserved = TRUE "
                             + "WHERE id = ?;"
             )
        ) {
            st.setInt(1, place.getId());
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("Place {} updated", place.toString());
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
