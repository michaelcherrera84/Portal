package com.asdvconstruction.portal.controller;

import com.asdvconstruction.portal.model.Project;
import com.asdvconstruction.portal.service.ProjectDAO;
import com.asdvconstruction.portal.util.Utilities;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.ValidatorException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
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
 * The {@code ProjectBean} is a CDI managed bean responsible for handling user operations related to the project.xhtml
 * page and the project table.
 *
 * @author Michael C. Herrera
 */
@Named(value = "projectBean")
@ViewScoped
public class ProjectBean implements Serializable {

    /**
     * The SPJBean is a CDI managed bean responsible for handling user operations related to the spj. xhtml page and the
     * spj table.
     */
    @Inject
    SPJBean spjBean;

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * Project Data Access Object containing methods to manipulate the project table.
     */
    private final ProjectDAO PROJECT_DAO;

    /**
     * Project to add.
     */
    private Project createProject;

    /**
     * Project used for project search.
     */
    private Project readProject;

    /**
     * List containing all projects in the project table.
     */
    private List<Project> projects;

    /**
     * Project that is being updated or deleted.
     */
    private Project updateProject;

    /**
     * Construct the ProjectBean.
     */
    public ProjectBean() {

        PROJECT_DAO = new ProjectDAO();
        createProject = new Project();
        readProject = new Project();

        // Populate the list to be used by the datatable.
        try {
            projects = PROJECT_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_FATAL, "Error retrieving data.", null);
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateProject = new Project();
    }

    /**
     * Get the value of createProject.
     *
     * @return the value of createProject
     */
    public Project getCreateProject() {return createProject;}

    /**
     * Set the value of createProject.
     *
     * @param createProject new value of createProject
     */
    public void setCreateProject(Project createProject) {this.createProject = createProject;}

    /**
     * Get the value of searchProject.
     *
     * @return the value of searchProject
     */
    public Project getReadProject() {return readProject;}

    /**
     * Set the value of searchProject.
     *
     * @param readProject new value of searchProject
     */
    public void setReadProject(Project readProject) {this.readProject = readProject;}

    /**
     * Get the value of projects.
     *
     * @return the value of projects
     */
    public List<Project> getProjects() {return projects;}

    /**
     * Validate a value provided for a project number and store the previous value.
     *
     * @param facesContext per-request state information related to the processing of the Jakarta Faces request, and the
     *                     rendering of the corresponding response
     * @param uiComponent  user interface component that will contain the value to be validated
     * @param o            value to be validated
     */
    @SuppressWarnings("unused")
    public void validateID(FacesContext facesContext, UIComponent uiComponent, Object o) {

        if (o == null)
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "The ID field is required.", null));

        for (Project project : projects)
            if (((InputNumber) uiComponent).getValue() == project.getId())
                updateProject = new Project(project);
    }

    /**
     * Validate required fields.
     *
     * @param facesContext per-request state information related to the processing of the Jakarta Faces request, and the
     *                     rendering of the corresponding response
     * @param uiComponent  user interface component that will contain the value to be validated
     * @param o            value to be validated
     */
    @SuppressWarnings("unused")
    public void validateRequired(FacesContext facesContext, UIComponent uiComponent, Object o) {

        if (o == null)
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "All fields are required", null));
        else if (o instanceof String && ((String) o).isEmpty())
            throw new ValidatorException(
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR, "All fields are required", null));
    }

    /**
     * Insert a new project.
     */
    public void create() {

        // Add the tuple and update the list.
        try {
            PROJECT_DAO.create(createProject);
            projects = PROJECT_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Project not added.",
                    "An error occurred while attempted to insert the project.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        createProject = new Project();
        PrimeFaces.current().executeScript("PF('create').hide()");
    }

    /**
     * Find a project by project ID.
     */
    public void read() {

        if (readProject.getId() == null) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Invalid search parameter.", null);
            return;
        }

        try {
            readProject = PROJECT_DAO.read(readProject.getId());
            if (readProject != null) {
                showReadDialog();
                return;
            }
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Search not completed.",
                    "An error occurred while attempted to locate the project.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            readProject = new Project();
            return;
        }

        Utilities.addMessage(FacesMessage.SEVERITY_INFO, "Project not found.",
                "No project exists with that ID.");
        readProject = new Project();
    }

    /**
     * Update and show the find project dialog.
     */
    private void showReadDialog() {

        PrimeFaces.current().ajax().update(Utilities.findComponent("search"));
        PrimeFaces.current().executeScript("PF('search').show()");
        readProject.setId(null);
    }

    /**
     * Update a project.
     *
     * @param i index of a project in {@linkplain #projects}.
     */
    public void update(int i) {

        Project project = projects.get(i);

        try {
            PROJECT_DAO.update(updateProject.getId(), project);
            projects = PROJECT_DAO.readAll();
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Project not updated.",
                    "An error occurred while attempted to update the project.");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateProject = new Project();
    }

    /**
     * Confirm project update was canceled.
     *
     * @param rowEditEvent Ajax behavior event
     */
    @SuppressWarnings("unused")
    public void updateCancel(RowEditEvent<Object> rowEditEvent) {

        Utilities.addMessage(FacesMessage.SEVERITY_WARN, "Update canceled.", null);
    }

    /**
     * Delete a project.
     */
    public void delete() {

        try {
            PROJECT_DAO.delete(updateProject);
            projects = PROJECT_DAO.readAll();
            Utilities.addMessage(FacesMessage.SEVERITY_INFO, "Project deleted.", null);
        } catch (SQLException e) {
            Utilities.addMessage(FacesMessage.SEVERITY_ERROR, "Project not deleted.",
                    "An error occurred while attempting to delete the project");
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        updateProject = new Project();
    }

    /**
     * Set the value of tempProject, update the content of the delete confirmation dialog, and show the dialog.
     *
     * @param project the value of tempProject
     */
    public void showDeleteDialog(Project project) {

        updateProject = project;
        PrimeFaces.current().ajax().update(Utilities.findComponent("confirm"));
        PrimeFaces.current().executeScript("PF('delete').show()");
    }

    /**
     * Return the content of the delete confirmation dialog.
     *
     * @return the content of the delete confirmation dialog
     */
    public String deleteConfirm() {

        // Get the number of rows in other tables that will be deleted via cascade if this tuple is deleted.
        int spjRows = 0;
        if (updateProject.getId() != null) {
            try {
                spjRows = spjBean.getSPJ_DAO().projectCount(updateProject.getId());
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        String project = "ID = <b>" + updateProject.getId() + "</b>&nbsp;&nbsp;|&nbsp;&nbsp;Name = <b>" +
                updateProject.getName() + "</b>";
        String warning =  // warning message indicating the number of records that will be removed via cascade
                "<div class='confirm-warning'><span class='confirm-warning-text'><b>Warning: </b>" + spjRows +
                        " records in SPJ will be removed if you continue.</span></div>";
        String confirm = "<div class='confirm-text'>Are you sure you would like to delete the project?</div>";

        // Do not include warning if no records will be removed from other tables.
        if (spjRows == 0)
            return project + confirm;

        return project + confirm + warning;
    }

    /**
     * Cancel delete project.
     */
    public void deleteCancel() {

        updateProject = new Project();
    }
}