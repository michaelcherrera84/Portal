package com.asdvconstruction.portal.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A supplier that supplies parts to a project.
 *
 * @author Michael C. Herrera
 */
public class Supplier {

    private Integer id;
    private String name;
    private LocalDate date;
    private Integer count;
    private String location;

    /**
     * Constructs a new Supplier object.
     */
    public Supplier() {}

    /**
     * Constructs a Supplier from a Supplier.
     *
     * @param supplier a Supplier
     */
    public Supplier(Supplier supplier) {

        this(supplier.id, supplier.name, supplier.date, supplier.count, supplier.location);
    }

    /**
     * Constructs a Supplier.
     *
     * @param id       supplier id
     * @param name     supplier name
     * @param date     supplier date
     * @param count    supplier count
     * @param location supplier location
     */
    public Supplier(Integer id, String name, LocalDate date, Integer count, String location) {

        this.id = id; this.name = name; this.date = date; this.count = count; this.location = location;
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
     * Get the value of date.
     *
     * @return the value of date
     */
    public LocalDate getDate() {return date;}

    /**
     * Set the value of date.
     *
     * @param date new value of date
     */
    public void setDate(LocalDate date) {this.date = date;}

    /**
     * Get the value of count.
     *
     * @return the value of count
     */
    public Integer getCount() {return count;}

    /**
     * Set the value of count.
     *
     * @param count new value of count
     */
    public void setCount(Integer count) {this.count = count;}

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

    public String convertDate() {

        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    /**
     * Return a String representation of the Supplier.
     *
     * @return a String representation of the Supplier
     */
    @Override
    public String toString() {

        return "Supplier{" + "id=" + id + ", name='" + name + '\'' + ", date=" + date + ", count=" + count +
                ", location='" + location + '\'' + '}';
    }
}
