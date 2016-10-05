package com.tremainebuchanan.register.data;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class Session {
    private String mSessionId;
    private String mSessionName;
    private String mCreated;
    private String mStudentsCount;
    private String mSubjectId;

    public Session(String mSessionId, String mSessionName, String mStudentsCount, String mSubjectId) {
        this.mSessionId = mSessionId;
        this.mSessionName = mSessionName;
        //this.mCreated = mCreated;
        this.mStudentsCount = mStudentsCount;
        this.mSubjectId = mSubjectId;

    }

    public String getSubjectId() {
        return mSubjectId;
    }

    public void setSubjectId(String mSubjectId) {
        this.mSubjectId = mSubjectId;
    }

    public String getStudentCount(){ return mStudentsCount; }

    public void setStudentCount(String mStudentsCount){ this.mStudentsCount = mStudentsCount; }

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
