package ru.job4j.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.entity.Place;

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
                     "INSERT INTO places_default(row, col, cost, reserved) "
                             + "VALUES (?, ?, ?, ?);"
             )
        ) {
            st.setInt(1, model.getRow());
            st.setInt(2, model.getCol());
            st.setDouble(3, model.getCost());
            st.setBoolean(4, model.isReserved());
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
                tmpPlace.setReserved(rs.getBoolean("reserved"));
                place = tmpPlace;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return place;
    }

    @Override
    public Place findByParam(Place model) {
        Place place = null;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM places_default "
                             + "WHERE row = ? AND col = ?;"
             )
        ) {
            st.setInt(1, model.getRow());
            st.setInt(2, model.getCol());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Place tmpPlace = new Place(
                        rs.getInt("row"),
                        rs.getInt("col")
                );
                tmpPlace.setId(rs.getInt("id"));
                tmpPlace.setCost(rs.getDouble("cost"));
                tmpPlace.setReserved(rs.getBoolean("reserved"));
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
                place.setReserved(rs.getBoolean("reserved"));
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
                     + "WHERE reserved = TRUE;"
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
                place.setReserved(rs.getBoolean("reserved"));
                tmp.add(place);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tmp;
    }

    @Override
    public boolean updatePlace(Place place) {
        boolean result = false;
        try (Connection con = this.store.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "UPDATE places_default "
                             + "SET row = ?, "
                             + "col = ?, "
                             + "cost = ?, "
                             + "reserved = ?"
                             + "WHERE id = ?;"
             )
        ) {
            st.setInt(1, place.getRow());
            st.setInt(2, place.getCol());
            st.setDouble(3, place.getCost());
            st.setBoolean(4, place.isReserved());
            st.setInt(5, place.getId());
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
