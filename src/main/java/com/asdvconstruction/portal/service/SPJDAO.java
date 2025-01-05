package com.asdvconstruction.portal.service;

import com.asdvconstruction.portal.model.SPJ;
import com.asdvconstruction.portal.model.User;
import com.asdvconstruction.portal.util.Database;
import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SPJ table Data Access Object
 *
 * @author Michael C. Herrera
 */
public class SPJDAO implements DAO<SPJ> {

    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    User user = (User) session.getAttribute("user");

    /**
     * Set the fields of an {@linkplain SPJ} using specified {@linkplain ResultSet}.
     *
     * @param spj       an {@linkplain SPJ}
     * @param resultSet a {@linkplain ResultSet}
     * @throws SQLException if a database access error occurs
     */
    private void setSPJ(SPJ spj, ResultSet resultSet) throws SQLException {

        spj.setSid(resultSet.getInt("sid"));
        spj.setPid(resultSet.getInt("pid"));
        spj.setJid(resultSet.getInt("jid"));
        spj.setQty(resultSet.getInt("qty"));
    }

    /**
     * Return the count of tuples with the specified supplier ID.
     *
     * @param id supplier ID
     * @return the count of tuples with the specified supplier ID
     * @throws SQLException if a database access error occurs
     */
    public int supplierCount(Integer id) throws SQLException {

        String sql = "SELECT COUNT(*) FROM spj WHERE sid=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
        }
        
        return 0;
    }

    /**
     * Return the count of tuples with the specified part ID.
     *
     * @param id part ID
     * @return the count of tuples with the specified part ID
     * @throws SQLException if a database access error occurs
     */
    public int partCount(Integer id) throws SQLException {

        String sql = "SELECT COUNT(*) FROM spj WHERE pid=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
        }

        return 0;
    }

    /**
     * Return the count of tuples with the specified project ID.
     *
     * @param id project ID
     * @return the count of tuples with the specified project ID
     * @throws SQLException if a database access error occurs
     */
    public int projectCount(Integer id) throws SQLException {

        String sql = "SELECT COUNT(*) FROM spj WHERE jid=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
        }

        return 0;
    }

    /**
     * Insert a tuple.
     *
     * @param spj a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int create(SPJ spj) throws SQLException {

        String sql = "INSERT INTO SPJ (sid, pid, jid, qty) VALUES (?, ?, ?, ?)";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, spj.getSid());
            preparedStatement.setInt(2, spj.getPid());
            preparedStatement.setInt(3, spj.getJid());
            preparedStatement.setInt(4, spj.getQty());
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
    public SPJ read(Object id) throws SQLException {

        String sql = "SELECT * FROM spj WHERE sid=? AND pid=? AND jid=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, ((SPJ) id).getSid());
            preparedStatement.setInt(2, ((SPJ) id).getPid());
            preparedStatement.setInt(3, ((SPJ) id).getJid());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SPJ spj = new SPJ();
                setSPJ(spj, resultSet);
                return spj;
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
    public List<SPJ> readAll() throws SQLException {

        List<SPJ> list = new ArrayList<>();

        String sql = "SELECT * FROM spj";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                SPJ spj = new SPJ();
                setSPJ(spj, resultSet);
                list.add(spj);
            }
        }

        return list;
    }

    /**
     * Update a tuple.
     *
     * @param id  unique identifier of the requested tuple
     * @param spj an updated tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int update(Object id, SPJ spj) throws SQLException {

        String sql = "UPDATE spj SET sid=?, pid=?, jid=?, qty=? WHERE sid=? AND pid=? AND jid=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, spj.getSid());
            preparedStatement.setInt(2, spj.getPid());
            preparedStatement.setInt(3, spj.getJid());
            preparedStatement.setInt(4, spj.getQty());
            preparedStatement.setInt(5, ((SPJ) id).getSid());
            preparedStatement.setInt(6, ((SPJ) id).getPid());
            preparedStatement.setInt(7, ((SPJ) id).getJid());

            return preparedStatement.executeUpdate();
        }
    }

    /**
     * Delete a tuple.
     *
     * @param spj a tuple
     * @return row count of executed query
     * @throws SQLException if a database access error occurs
     */
    @Override
    public int delete(SPJ spj) throws SQLException {

        String sql = "DELETE FROM spj WHERE sid=? AND pid=? AND jid=?";

        try (Connection connection = Database.connection(user);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, spj.getSid());
            preparedStatement.setInt(2, spj.getPid());
            preparedStatement.setInt(3, spj.getJid());
            return preparedStatement.executeUpdate();
        }
    }
}
