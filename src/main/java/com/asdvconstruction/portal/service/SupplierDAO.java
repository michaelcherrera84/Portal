package com.asdvconstruction.portal.service;

import com.asdvconstruction.portal.model.Supplier;
import com.asdvconstruction.portal.model.User;
import com.asdvconstruction.portal.util.Database;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Supplier table Data Access Object
 *
 * @author Michael C. Herrera
 */
public class SupplierDAO implements DAO<Supplier> {

     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
     User user = (User) session.getAttribute("user");
//    User user = new User("root", "root", "admin");


    /**
     * Set the fields of a {@linkplain Supplier} using specified {@linkplain ResultSet}.
     *
     * @param supplier  a {@linkplain Supplier}
     * @param resultSet a {@linkplain ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setSupplier(Supplier supplier, ResultSet resultSet) throws SQLException {

        supplier.setId(resultSet.getInt("id"));
        supplier.setName(resultSet.getString("name"));
        supplier.setDate(resultSet.getDate("date").toLocalDate());
        supplier.setCount(resultSet.getInt("count"));
        supplier.setLocation(resultSet.getString("location"));
    }

    /**
     * Insert a tuple.
     *
     * @param supplier a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int create(Supplier supplier) throws SQLException {

        String sql = "INSERT INTO Supplier (id, name, date, count, location) VALUES (DEFAULT, ?, ?, ?, ?)";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, supplier.getName());
            preparedStatement.setDate(2, Date.valueOf(supplier.getDate()));
            preparedStatement.setInt(3, supplier.getCount() == null ? 0 : supplier.getCount());
            preparedStatement.setString(4, supplier.getLocation());
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * Return a tuple.
     *
     * @param id unique identifier of the requested tuple
     * @return the requested tuple or {@code null} if the requested tuple does not exist
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Supplier read(Object id) throws SQLException {

        String sql = "SELECT * FROM supplier WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (Integer) id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Supplier supplier = new Supplier();
                setSupplier(supplier, resultSet);
                return supplier;
            }
        }

        return null;
    }

    /**
     * Return a {@linkplain List} of all tuples.
     *
     * @return a {@linkplain List} of all tuples.
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<Supplier> readAll() throws SQLException {

        List<Supplier> list = new ArrayList<>();

        String sql = "SELECT * FROM supplier";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                setSupplier(supplier, resultSet);
                list.add(supplier);
            }
        }

        return list;
    }

    /**
     * Update a tuple.
     *
     * @param id       unique identifier of the requested tuple
     * @param supplier an updated tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int update(Object id, Supplier supplier) throws SQLException {

        String sql = "UPDATE supplier SET id=?, name=?, date=?, count=?, location=? WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, supplier.getId());
            preparedStatement.setString(2, supplier.getName());
            preparedStatement.setDate(3, Date.valueOf(supplier.getDate()));
            preparedStatement.setInt(4, supplier.getCount());
            preparedStatement.setString(5, supplier.getLocation());
            preparedStatement.setInt(6, (Integer) id);
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete a tuple.
     *
     * @param supplier a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int delete(Supplier supplier) throws SQLException {

        String sql = "DELETE FROM supplier WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, supplier.getId());
            return preparedStatement.executeUpdate();
        }
    }
}