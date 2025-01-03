package com.asdvconstruction.portal.service;

import com.asdvconstruction.portal.model.Part;
import com.asdvconstruction.portal.model.User;
import com.asdvconstruction.portal.util.Database;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Part table Data Access Object
 *
 * @author Michael C. Herrera
 */
public class PartDAO implements DAO<Part> {

    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    User user = (User) session.getAttribute("user");
//    User user = new User("root", "root", "admin");

    /**
     * Set the fields of a {@linkplain Part} using specified {@linkplain ResultSet}.
     *
     * @param part      a {@linkplain Part}
     * @param resultSet a {@linkplain ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setPart(Part part, ResultSet resultSet) throws SQLException {

        part.setId(resultSet.getInt("id"));
        part.setName(resultSet.getString("name"));
        part.setCategory(resultSet.getString("category"));
        part.setLocation(resultSet.getString("location"));
    }

    /**
     * Insert a tuple.
     *
     * @param part a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int create(Part part) throws SQLException {

        String sql = "INSERT INTO Part (id, name, category, location) VALUES (DEFAULT, ?, ?, ?)";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, part.getName());
            preparedStatement.setString(2, part.getCategory());
            preparedStatement.setString(3, part.getLocation());
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
    public Part read(Object id) throws SQLException {

        String sql = "SELECT * FROM part WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (Integer) id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Part part = new Part();
                setPart(part, resultSet);
                return part;
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
    public List<Part> readAll() throws SQLException {

        List<Part> list = new ArrayList<>();

        String sql = "SELECT * FROM part";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Part part = new Part();
                setPart(part, resultSet);
                list.add(part);
            }
        }

        return list;
    }

    /**
     * Update a tuple.
     *
     * @param id   unique identifier of the requested tuple
     * @param part an updated tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int update(Object id, Part part) throws SQLException {

        String sql = "UPDATE part SET id=?, name=?, category=?, location=? WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, part.getId());
            preparedStatement.setString(2, part.getName());
            preparedStatement.setString(3, part.getCategory());
            preparedStatement.setString(4, part.getLocation());
            preparedStatement.setInt(5, (Integer) id);
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete a tuple.
     *
     * @param part a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int delete(Part part) throws SQLException {

        String sql = "DELETE FROM part WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, part.getId());
            return preparedStatement.executeUpdate();
        }
    }
}