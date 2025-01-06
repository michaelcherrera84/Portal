package com.asdvconstruction.portal.controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.model.dashboard.DashboardModel;
import org.primefaces.model.dashboard.DefaultDashboardModel;
import org.primefaces.model.dashboard.DefaultDashboardWidget;

import java.io.Serializable;

/**
 * @author Michael C. Herrera
 */
@Named (value = "portalBean")
@ViewScoped
public class PortalBean implements Serializable {

    private DashboardModel dashboardModel;

    @PostConstruct
    public void init() {
        dashboardModel = new DefaultDashboardModel();
        dashboardModel.addWidget(new DefaultDashboardWidget("database_management", "flex"));
        dashboardModel.addWidget(new DefaultDashboardWidget("panel2", "flex"));
        dashboardModel.addWidget(new DefaultDashboardWidget("panel3", "flex"));
    }

    /**
     * Get the value of dashboardModel.
     *
     * @return the value of dashboardModel
     */
    public DashboardModel getDashboardModel() {return dashboardModel;}
}
