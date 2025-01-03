package com.asdvconstruction.portal.model;

/**
 * A part, supplied by a Supplier to a project.
 *
 * @author Michael C. Herrera
 */
public class Part {

    private Integer id;
    private String name;
    private String category;
    private String location;

    /**
     * Constructs a new Part object.
     */
    public Part() {}

    /**
     * Constructs a Part from a Part.
     *
     * @param part a Part
     */
    public Part(Part part) {

        this(part.id, part.name, part.category, part.location);
    }

    /**
     * Constructs a Part.
     *
     * @param id       part id
     * @param name     part name
     * @param category part category
     * @param location part location
     */
    public Part(Integer id, String name, String category, String location) {

        this.id = id; this.name = name; this.category = category; this.location = location;
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
     * Get the value of category.
     *
     * @return the value of category
     */
    public String getCategory() {return category;}

    /**
     * Set the value of category.
     *
     * @param category new value of category
     */
    public void setCategory(String category) {this.category = category;}

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
     * Return a String representation of the Part.
     *
     * @return a String representation of the Part
     */
    @Override
    public String toString() {

        return "Part{" + "id=" + id + ", name='" + name + '\'' + ", category='" + category + '\'' + ", location='" +
                location + '\'' + '}';
    }
}
