package com.asdvconstruction.portal.model;

/**
 * A construction project.
 *
 * @author Michael C. Herrera
 */
public class Project {

    private Integer id;
    private String name;
    private String location;

    /**
     * Constructs a new Project object.
     */
    public Project() {}

    /**
     * Constructs a Project from a Project.
     *
     * @param project a Project
     */
    public Project(Project project) {

        this(project.id, project.name, project.location);
    }

    /**
     * Constructs a Project.
     *
     * @param id       project id
     * @param name     project name
     * @param location project location
     */
    public Project(Integer id, String name, String location) {

        this.id = id; this.name = name; this.location = location;
    }

    /**
     * Get the value of id.
     *
     * @return the value of id
     */
    public Integer getId() {return id;}

    /**
     * Set the value of id.
     *
     * @param id new value of id
     */
    public void setId(Integer id) {this.id = id;}

    /**
     * Get the value of name.
     *
     * @return the value of name
     */
    public String getName() {return name;}

    /**
     * Set the value of name.
     *
     * @param name new value of name
     */
    public void setName(String name) {this.name = name;}

    /**
     * Get the value of location.
     *
     * @return the value of location
     */
    public String getLocation() {return location;}

    /**
     * Set the value of location.
     *
     * @param location new value of location
     */
    public void setLocation(String location) {this.location = location;}

    /**
     * Return a String representation of the Project.
     *
     * @return a String representation of the Project
     */
    @Override
    public String toString() {

        return "Project{" + "id=" + id + ", name='" + name + '\'' + ", location='" + location + '\'' + '}';
    }
}
