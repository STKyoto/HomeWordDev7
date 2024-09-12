package org.example.Dto;

import java.math.BigDecimal;

public class ProjectPrices {
    private int projectId;
    private BigDecimal projectCost;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(BigDecimal projectCost) {
        this.projectCost = projectCost;
    }
}
