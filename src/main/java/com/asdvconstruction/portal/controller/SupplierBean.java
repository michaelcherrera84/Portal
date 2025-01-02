package com.asdvconstruction.portal.controller;

import com.asdvconstruction.portal.model.Supplier;
import com.asdvconstruction.portal.model.User;
import com.asdvconstruction.portal.service.SupplierDAO;
import com.asdvconstruction.portal.util.Database;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code SupplierBean} is a CDI managed bean responsible for handling user operations related to the supplier.xhtml
 * page and the supplier table.
 *
 * @author Michael C. Herrera
 */
@Named(value = "supplierBean")
@ViewScoped
public class SupplierBean implements Serializable {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * Supplier Data Access Object containing methods to manipulate the supplier table.
     */
    private final SupplierDAO SUPPLIER_DAO;

    /**
     * Supplier to add.
     */
    private Supplier createSupplier;

    /**
     * Supplier used for supplier search.
     */
    private Supplier readSupplier;

    /**
     * List containing all suppliers in the supplier table.
     */
    private List<Supplier> suppliers;

    /**
     * Supplier that is being updated or deleted.
     */
    private Supplier updateSupplier;

    /**
     * Construct the SupplierBean.
     */
    public SupplierBean() {

        SUPPLIER_DAO = new SupplierDAO();
        createSupplier = new Supplier();
        readSupplier = new Supplier();

        try {
            suppliers = SUPPLIER_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_FATAL, "Error retrieving data.", null);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateSupplier = new Supplier();
    }

    /**
     * Get the value of createSupplier.
     *
     * @return the value of createSupplier
     */
    public Supplier getCreateSupplier() {return createSupplier;}

    /**
     * Set the value of createSupplier.
     *
     * @param createSupplier new value of createSupplier
     */
    public void setCreateSupplier(Supplier createSupplier) {

        this.createSupplier = createSupplier;
    }

    /**
     * Get the value of searchSupplier.
     *
     * @return the value of searchSupplier
     */
    public Supplier getReadSupplier() {return readSupplier;}

    /**
     * Set the value of searchSupplier.
     *
     * @param readSupplier new value of searchSupplier
     */
    public void setReadSupplier(Supplier readSupplier) {

        this.readSupplier = readSupplier;
    }

    /**
     * Get the value of suppliers.
     *
     * @return the value of suppliers
     */
    public List<Supplier> getSuppliers() {return suppliers;}

    /**
     * Validate a value provided for a supplier number and store the previous value.
     *
     * @param facesContext per-request state information related to the processing of the Jakarta Faces request, and the
     *                     rendering of the corresponding response
     * @param uiComponent  user interface component that will contain the value to be validated
     * @param o            value to be validated
     */
    @SuppressWarnings("unused")
    public void validateID(FacesContext facesContext, UIComponent uiComponent, Object o) {

        for (Supplier supplier : suppliers)
            if (((InputNumber) uiComponent).getValue() == supplier.getId())
                updateSupplier = new Supplier(supplier);
    }

    /**
     * Insert a new supplier.
     */
    public void create() {

        if (createSupplier.getName().isEmpty() || createSupplier.getDate() == null ||
                createSupplier.getLocation().isEmpty()) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Supplier not added.",
                    "Name, date, and location cannot be empty.");
            return;
        }

            try {
                SUPPLIER_DAO.create(createSupplier);
                suppliers = SUPPLIER_DAO.readAll();
            } catch (SQLException e) {
                Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Supplier not added.",
                        "An error occurred while attempted to insert the supplier.");
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }

        PrimeFaces.current().ajax().update(Utilities.findComponent("create"));
        PrimeFaces.current().executeScript("PF('create').hide()");
        createSupplier = new Supplier();
    }

    /**
     * Find a supplier by supplier ID.
     */
    public void read() {

        for (Supplier supplier : suppliers)
            if (Objects.equals(readSupplier.getId(), supplier.getId())) {
                readSupplier = new Supplier(supplier);
                showReadDialog();
                return;
            }

        readSupplier.setId(null);
        Utilities.addMessage(FacesMessage.SEVERITY_INFO, "Supplier not found.",
                "No supplier exists with that ID.");
    }

    /**
     * Update and show the find supplier dialog.
     */
    private void showReadDialog() {

        PrimeFaces.current().ajax().update(Utilities.findComponent("search"));
        PrimeFaces.current().executeScript("PF('search').show()");
        readSupplier.setId(null);
    }

    /**
     * Update a supplier.
     *
     * @param i index of a supplier in {@linkplain #suppliers}.
     */
    public void update(int i) {

        Supplier supplier = suppliers.get(i);

        try {
            SUPPLIER_DAO.update(updateSupplier.getId(), supplier);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Supplier not updated.",
                    "An error occurred while attempted to update the supplier.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateSupplier = new Supplier();
    }

    /**
     * Confirm supplier update was canceled.
     *
     * @param rowEditEvent Ajax behavior event
     */
    @SuppressWarnings("unused")
    public void updateCancel(RowEditEvent<Object> rowEditEvent) {

        Utilities.addMessage(FacesMessage.SEVERITY_WARN, "Update canceled.", null);
    }

    /**
     * Delete a supplier.
     */
    public void delete() {

        try {
            SUPPLIER_DAO.delete(updateSupplier);
            suppliers = SUPPLIER_DAO.readAll();
            Utilities.addMessage(FacesMessage.SEVERITY_INFO, "Supplier deleted.", null);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Supplier not deleted.",
                    "An error occurred while attempting to delete the supplier");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateSupplier = new Supplier();
    }

    /**
     * Set the value of tempSupplier, update the content of the delete confirmation dialog, and show the dialog.
     *
     * @param supplier the value of tempSupplier
     */
    public void showDeleteDialog(Supplier supplier) {

        updateSupplier = supplier;
        PrimeFaces.current().ajax().update(Utilities.findComponent("confirm"));
        PrimeFaces.current().executeScript("PF('delete').show()");
    }

    /**
     * Return the content of the delete confirmation dialog.
     *
     * @return the content of the delete confirmation dialog
     */
    public String deleteConfirm() {

        String supplier = "ID = <b>" + updateSupplier.getId() + "</b>&nbsp;&nbsp;|&nbsp;&nbsp;Name = <b>" +
                updateSupplier.getName() + "</b>";
//            String warning =
//                    "<div class='confirm-warning'><span class='confirm-warning-text'><b>Warning: </b>" + spj() +
//                            " records in SPJ will be removed if you continue.</span></div>";
        String confirm = "<div class='confirm-text'>Are you sure you would like to delete the supplier?</div>";
        return supplier + confirm;
//            return supplier + confirm + warning;
    }

    /**
     * Cancel delete supplier.
     */
    public void deleteCancel() {

        updateSupplier = new Supplier();
    }

    /* todo: transfer this method */
    int spj() {

        try {
            Connection connection = Database.connection(new User("root", "root", "admin"));
            String sql = "SELECT COUNT(*) FROM spj WHERE snumber='" + updateSupplier.getId() + "'";
            ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
            connection.close();
        } catch (Exception ignored) {

        }

        return 0;
    }
}
