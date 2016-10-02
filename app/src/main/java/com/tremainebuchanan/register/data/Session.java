package com.tremainebuchanan.register.data;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class Session {
    private String mSessionId;
    private String mSessionName;
    private String mCreated;
    private Number mNumberofStudents;

    public Session(String mSessionId, String mSessionName, String mCreated, Number mNumberofStudents) {
        this.mSessionId = mSessionId;
        this.mSessionName = mSessionName;
        this.mCreated = mCreated;
        this.mNumberofStudents = mNumberofStudents;
    }

    public Number getNumberofStudents(){ return mNumberofStudents; }

    public String getSessionId() {
        return mSessionId;
    }

    public void setSessionId(String mSessionId) {
        this.mSessionId = mSessionId;
    }

    public String getSessionName() {
        return mSessionName;
    }

    public void setSessionName(String mSessionName) {
        this.mSessionName = mSessionName;
    }

    public String getCreated() {
        return mCreated;
    }

    public void setCreated(String mCreated) {
        this.mCreated = mCreated;
    }
}
