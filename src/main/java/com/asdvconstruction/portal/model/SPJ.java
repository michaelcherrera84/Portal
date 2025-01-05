package com.asdvconstruction.portal.model;

/**
 * Record containing a supplier, a part supplied, a project to which the part is supplied, and the quantity of parts
 * supplied.
 *
 * @author Michael C. Herrera
 */
public class SPJ {

    Integer sid;
    Integer pid;
    Integer jid;
    Integer qty;

    /**
     * Construct an SPJ object.
     */
    public SPJ() {}

    /**
     * Construct an SPJ from an SPJ.
     *
     * @param spj an SPJ
     */
    public SPJ(SPJ spj) {

        this.sid = spj.sid; this.pid = spj.pid; this.jid = spj.jid; this.qty = spj.qty;
    }

    /**
     * Construct an SPJ.
     *
     * @param sid a supplier ID
     * @param pid a part ID
     * @param jid a project ID
     * @param qty quantity of parts
     */
    public SPJ(Integer sid, Integer pid, Integer jid, Integer qty) {

        this.sid = sid; this.pid = pid; this.jid = jid; this.qty = qty;
    }

    /**
     * Get the value of sid.
     *
     * @return the value of sid
     */
    public Integer getSid() {return sid;}

    /**
     * Set the value of sid.
     *
     * @param sid new value of sid
     */
    public void setSid(Integer sid) {this.sid = sid;}

    /**
     * Get the value of pid.
     *
     * @return the value of pid
     */
    public Integer getPid() {return pid;}

    /**
     * Set the value of pid.
     *
     * @param pid new value of pid
     */
    public void setPid(Integer pid) {this.pid = pid;}

    /**
     * Get the value of jid.
     *
     * @return the value of jid
     */
    public Integer getJid() {return jid;}

    /**
     * Set the value of jid.
     *
     * @param jid new value of jid
     */
    public void setJid(Integer jid) {this.jid = jid;}

    /**
     * Get the value of qty.
     *
     * @return the value of qty
     */
    public Integer getQty() {return qty;}

    /**
     * Set the value of qty.
     *
     * @param qty new value of qty
     */
    public void setQty(Integer qty) {this.qty = qty;}

    /**
     * Return a String representation of the SPJ.
     *
     * @return a String representation of the SPJ
     */
    @Override
    public String toString() {

        return "SPJ{" + "sid=" + sid + ", pid=" + pid + ", jid=" + jid + ", qty=" + qty + '}';
    }
}
