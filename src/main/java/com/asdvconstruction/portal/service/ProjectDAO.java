package com.asdvconstruction.portal.service;

import com.asdvconstruction.portal.model.Project;
import com.asdvconstruction.portal.model.User;
import com.asdvconstruction.portal.util.Database;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Project table Data Access Object
 *
 * @author Michael C. Herrera
 */
public class ProjectDAO implements DAO<Project> {

    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    User user = (User) session.getAttribute("user");

    /**
     * Set the fields of a {@linkplain Project} using specified {@linkplain ResultSet}.
     *
     * @param project      a {@linkplain Project}
     * @param resultSet a {@linkplain ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setProject(Project project, ResultSet resultSet) throws SQLException {

        project.setId(resultSet.getInt("id"));
        project.setName(resultSet.getString("name"));
        project.setLocation(resultSet.getString("location"));
    }

    /**
     * Insert a tuple.
     *
     * @param project a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int create(Project project) throws SQLException {

        String sql = "INSERT INTO Project (id, name, location) VALUES (DEFAULT, ?, ?)";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getLocation());
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
    public Project read(Object id) throws SQLException {

        String sql = "SELECT * FROM project WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, (Integer) id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Project project = new Project();
                setProject(project, resultSet);
                return project;
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
    public List<Project> readAll() throws SQLException {

        List<Project> list = new ArrayList<>();

        String sql = "SELECT * FROM project";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Project project = new Project();
                setProject(project, resultSet);
                list.add(project);
            }
        }

        return list;
    }

    /**
     * Update a tuple.
     *
     * @param id   unique identifier of the requested tuple
     * @param project an updated tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int update(Object id, Project project) throws SQLException {

        String sql = "UPDATE project SET id=?, name=?, location=? WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, project.getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getLocation());
            preparedStatement.setInt(4, (Integer) id);
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete a tuple.
     *
     * @param project a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int delete(Project project) throws SQLException {

        String sql = "DELETE FROM project WHERE id=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, project.getId());
            return preparedStatement.executeUpdate();
        }
    }
}