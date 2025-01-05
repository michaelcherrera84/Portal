package com.asdvconstruction.portal.controller;

import com.asdvconstruction.portal.model.SPJ;
import com.asdvconstruction.portal.service.SPJDAO;
import com.asdvconstruction.portal.util.Utilities;
import jakarta.faces.application.FacesMessage;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The {@code SPJBean} is a CDI managed bean responsible for handling user operations related to the spj.xhtml page and
 * the spj table.
 *
 * @author Michael C. Herrera
 */
@Named(value = "spjBean")
@ViewScoped
public class SPJBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * SPJ Data Access Object containing methods to manipulate the spj table.
     */
    private final SPJDAO SPJ_DAO;

    /**
     * SPJ to add.
     */
    private SPJ createSPJ;

    /**
     * SPJ primary key used for spj search.
     */
    private String readID;

    /**
     * SPJ used for spj search.
     */
    private SPJ readSPJ;

    /**
     * List containing all spjs in the spj table.
     */
    private List<SPJ> spjs;

    /**
     * SPJ that is being updated or deleted.
     */
    private SPJ updateSPJ;

    /**
     * Construct the SPJBean.
     */
    public SPJBean() {

        SPJ_DAO = new SPJDAO();
        createSPJ = new SPJ();

        // Populate the list to be used by the datatable.
        try {
            spjs = SPJ_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_FATAL, "Error retrieving data.", null);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateSPJ = new SPJ();
    }

    /**
     * Get the value of SPJ_DAO.
     *
     * @return the value of SPJ_DAO
     */
    public SPJDAO getSPJ_DAO() {return SPJ_DAO;}

    /**
     * Get the value of createSPJ.
     *
     * @return the value of createSPJ
     */
    public SPJ getCreateSPJ() {return createSPJ;}

    /**
     * Set the value of createSPJ.
     *
     * @param createSPJ new value of createSPJ
     */
    public void setCreateSPJ(SPJ createSPJ) {this.createSPJ = createSPJ;}

    /**
     * Get the value of searchSPJ.
     *
     * @return the value of searchSPJ
     */
    public String getReadID() {return readID;}

    /**
     * Set the value of searchSPJ.
     *
     * @param readID new value of searchSPJ
     */
    public void setReadID(String readID) {this.readID = readID;}

    /**
     * Get the value of readSPJ.
     *
     * @return the value of readSPJ
     */
    public SPJ getReadSPJ() {return readSPJ;}

    /**
     * Set the value of readSPJ.
     *
     * @param readSPJ new value of readSPJ
     */
    public void setReadSPJ(SPJ readSPJ) {this.readSPJ = readSPJ;}

    /**
     * Get the value of spjs.
     *
     * @return the value of spjs
     */
    public List<SPJ> getSPJs() {return spjs;}

    /**
     * Insert a new spj.
     */
    public void create() {

        // If required columns are empty, display an error message and update the form.
        if (createSPJ.getSid() == null || createSPJ.getPid() == null || createSPJ.getJid() == null ||
                createSPJ.getQty() == null) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "SPJ not added.",
                    "Fields cannot be empty.");
            return;
        }

        // Add the tuple and update the list.
        try {
            SPJ_DAO.create(createSPJ);
            spjs = SPJ_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "SPJ not added.",
                    "An error occurred while attempted to insert the spj.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        // Reset the form.
        createSPJ = new SPJ();
        PrimeFaces.current().ajax().update(Utilities.findComponent("create"));
        PrimeFaces.current().executeScript("PF('create').hide()");
    }

    /**
     * Reset the create dialog.
     */
    public void createReset() {

        createSPJ = new SPJ();
        PrimeFaces.current().ajax().update(Utilities.findComponent("create"));
    }

    /**
     * Find an spj by spj ID.
     */
    public void read() {

        List<Integer> IDs = new ArrayList<>();  // List for IDs

        // Add the IDs to the list. If the list is empty, return.
        getIDs(IDs);
        if (IDs.isEmpty()) {
            return;
        }

        readSPJ = new SPJ(IDs.get(0), IDs.get(1), IDs.get(2), 0);

        try {
            readSPJ = SPJ_DAO.read(readSPJ);
            if (readSPJ != null) {
                showReadDialog();
                return;
            }
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Search not completed.",
                    "An error occurred while attempted to locate the spj.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            readID = null;
            return;
        }

        Utilities.addMessage(FacesMessage.SEVERITY_INFO, "SPJ not found.",
                "No spj exists with that ID.");
        readID = null;
    }

    /**
     * Split search parameters into individual IDs and add them to a list.
     *
     * @param IDs a list
     */
    private void getIDs(List<Integer> IDs) {

        // Split the string by commas.
        String[] substrings = readID.split(",\\s*");

        // If the substring does not contain at least 3 elements, display an error message and return.
        if (substrings.length < 3) {
            PrimeFaces.current().dialog().showMessageDynamic(new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid " +
                    "Search Parameters", "Search must contain a supplier ID, a part ID, and a project ID separated " +
                    "by commas."));
            return;
        }

        // Use regex to extract the IDs and store them in a list.
        Pattern pattern = Pattern.compile("\\d+");

        for (String substring : substrings) {
            Matcher matcher = pattern.matcher(substring);
            if (matcher.find())
                IDs.add(Integer.parseInt(matcher.group()));
        }
    }

    /**
     * Update and show the find spj dialog.
     */
    private void showReadDialog() {

        PrimeFaces.current().ajax().update(Utilities.findComponent("search"));
        PrimeFaces.current().executeScript("PF('search').show()");
        readID = null;
    }

    /**
     * Update a spj.
     *
     * @param i index of a spj in {@linkplain #spjs}.
     */
    public void update(int i) {

        SPJ spj = spjs.get(i);

        try {
            SPJ_DAO.update(updateSPJ, spj);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "SPJ not updated.",
                    "An error occurred while attempted to update the spj.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateSPJ = new SPJ();
    }

    /**
     * Store old IDs of an SPJ before update.
     *
     * @param i index of a spj in {@linkplain #spjs}
     */
    public void getIDs(int i) {updateSPJ = new SPJ(spjs.get(i));}

    /**
     * Confirm spj update was canceled.
     *
     * @param rowEditEvent Ajax behavior event
     */
    @SuppressWarnings("unused")
    public void updateCancel(RowEditEvent<Object> rowEditEvent) {

        Utilities.addMessage(FacesMessage.SEVERITY_WARN, "Update canceled.", null);
    }

    /**
     * Delete an spj.
     */
    public void delete() {

        try {
            SPJ_DAO.delete(updateSPJ);
            spjs = SPJ_DAO.readAll();
            Utilities.addMessage(FacesMessage.SEVERITY_INFO, "SPJ deleted.", null);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "SPJ not deleted.",
                    "An error occurred while attempting to delete the spj");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateSPJ = new SPJ();
    }

    /**
     * Set the value of tempSPJ, update the content of the delete confirmation dialog, and show the dialog.
     *
     * @param spj the value of tempSPJ
     */
    public void showDeleteDialog(SPJ spj) {

        updateSPJ = spj;
        PrimeFaces.current().ajax().update(Utilities.findComponent("confirm"));
        PrimeFaces.current().executeScript("PF('delete').show()");
    }

    /**
     * Return the content of the delete confirmation dialog.
     *
     * @return the content of the delete confirmation dialog
     */
    public String deleteConfirm() {

        return "<div class='confirm-text'>Are you sure you would like to delete the spj?</div>";
    }

    /**
     * Cancel delete spj.
     */
    public void deleteCancel() {

        updateSPJ = new SPJ();
    }
}
