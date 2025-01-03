package com.asdvconstruction.portal.controller;

import com.asdvconstruction.portal.model.Part;
import com.asdvconstruction.portal.service.PartDAO;
import com.asdvconstruction.portal.util.Utilities;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.event.RowEditEvent;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code PartBean} is a CDI managed bean responsible for handling user operations related to the part.xhtml
 * page and the part table.
 *
 * @author Michael C. Herrera
 */
@Named(value = "partBean")
@ViewScoped
public class PartBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * Part Data Access Object containing methods to manipulate the part table.
     */
    private final PartDAO PART_DAO;

    /**
     * Part to add.
     */
    private Part createPart;

    /**
     * Part used for part search.
     */
    private Part readPart;

    /**
     * List containing all parts in the part table.
     */
    private List<Part> parts;

    /**
     * Part that is being updated or deleted.
     */
    private Part updatePart;

    /**
     * Construct the PartBean.
     */
    public PartBean() {

        PART_DAO = new PartDAO();
        createPart = new Part();
        readPart = new Part();

        try {
            parts = PART_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_FATAL, "Error retrieving data.", null);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updatePart = new Part();
    }

    /**
     * Get the value of createPart.
     *
     * @return the value of createPart
     */
    public Part getCreatePart() {return createPart;}

    /**
     * Set the value of createPart.
     *
     * @param createPart new value of createPart
     */
    public void setCreatePart(Part createPart) {

        this.createPart = createPart;
    }

    /**
     * Get the value of searchPart.
     *
     * @return the value of searchPart
     */
    public Part getReadPart() {return readPart;}

    /**
     * Set the value of searchPart.
     *
     * @param readPart new value of searchPart
     */
    public void setReadPart(Part readPart) {

        this.readPart = readPart;
    }

    /**
     * Get the value of parts.
     *
     * @return the value of parts
     */
    public List<Part> getParts() {return parts;}

    /**
     * Validate a value provided for a part number and store the previous value.
     *
     * @param facesContext per-request state information related to the processing of the Jakarta Faces request, and the
     *                     rendering of the corresponding response
     * @param uiComponent  user interface component that will contain the value to be validated
     * @param o            value to be validated
     */
    @SuppressWarnings("unused")
    public void validateID(FacesContext facesContext, UIComponent uiComponent, Object o) {

        for (Part part : parts)
            if (((InputNumber) uiComponent).getValue() == part.getId())
                updatePart = new Part(part);
    }

    /**
     * Insert a new part.
     */
    public void create() {

        if (createPart.getName().isEmpty() || createPart.getLocation().isEmpty()) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Part not added.",
                    "Name and location cannot be empty.");
            return;
        }

        try {
            PART_DAO.create(createPart);
            parts = PART_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Part not added.",
                    "An error occurred while attempted to insert the part.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        PrimeFaces.current().ajax().update(Utilities.findComponent("create"));
        PrimeFaces.current().executeScript("PF('create').hide()");
        createPart = new Part();
    }

    /**
     * Find a part by part ID.
     */
    public void read() {

        try {
            readPart = PART_DAO.read(readPart.getId());
            if (readPart != null) {
                showReadDialog();
                return;
            }
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Search not completed.",
                    "An error occurred while attempted to locate the part.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            readPart = new Part();
            return;
        }

        Utilities.addMessage(FacesMessage.SEVERITY_INFO, "Part not found.",
                "No part exists with that ID.");
        readPart = new Part();
    }

    /**
     * Update and show the find part dialog.
     */
    private void showReadDialog() {

        PrimeFaces.current().ajax().update(Utilities.findComponent("search"));
        PrimeFaces.current().executeScript("PF('search').show()");
        readPart.setId(null);
    }

    /**
     * Update a part.
     *
     * @param i index of a part in {@linkplain #parts}.
     */
    public void update(int i) {

        Part part = parts.get(i);

        try {
            PART_DAO.update(updatePart.getId(), part);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Part not updated.",
                    "An error occurred while attempted to update the part.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updatePart = new Part();
    }

    /**
     * Confirm part update was canceled.
     *
     * @param rowEditEvent Ajax behavior event
     */
    @SuppressWarnings("unused")
    public void updateCancel(RowEditEvent<Object> rowEditEvent) {

        Utilities.addMessage(FacesMessage.SEVERITY_WARN, "Update canceled.", null);
    }

    /**
     * Delete a part.
     */
    public void delete() {

        try {
            PART_DAO.delete(updatePart);
            parts = PART_DAO.readAll();
            Utilities.addMessage(FacesMessage.SEVERITY_INFO, "Part deleted.", null);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Part not deleted.",
                    "An error occurred while attempting to delete the part");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updatePart = new Part();
    }

    /**
     * Set the value of tempPart, update the content of the delete confirmation dialog, and show the dialog.
     *
     * @param part the value of tempPart
     */
    public void showDeleteDialog(Part part) {

        updatePart = part;
        PrimeFaces.current().ajax().update(Utilities.findComponent("confirm"));
        PrimeFaces.current().executeScript("PF('delete').show()");
    }

    /**
     * Return the content of the delete confirmation dialog.
     *
     * @return the content of the delete confirmation dialog
     */
    public String deleteConfirm() {

        String part = "ID = <b>" + updatePart.getId() + "</b>&nbsp;&nbsp;|&nbsp;&nbsp;Name = <b>" +
                updatePart.getName() + "</b>";
//            String warning =
//                    "<div class='confirm-warning'><span class='confirm-warning-text'><b>Warning: </b>" + spj() +
//                            " records in SPJ will be removed if you continue.</span></div>";
        String confirm = "<div class='confirm-text'>Are you sure you would like to delete the part?</div>";
        return part + confirm;
//            return part + confirm + warning;
    }

    /**
     * Cancel delete part.
     */
    public void deleteCancel() {

        updatePart = new Part();
    }
}

