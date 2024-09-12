package org.example.Dto;

public class MaxProjectClient {
    private long CLIENT_ID;
    private String NAME;
    private int project_count;
    public long getCLIENT_ID() {
        return CLIENT_ID;
    }

    public void setCLIENT_ID(long CLIENT_ID) {
        this.CLIENT_ID = CLIENT_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getProjectcount() {
        return project_count;
    }

    public void setProjectcount(int project_count) {
        this.project_count = project_count;
    }
}
