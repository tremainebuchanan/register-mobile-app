package com.tremainebuchanan.register.data;

import java.util.Date;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class Session {
    private String mSessionId;
    private String mSessionName;
    private String mCreated;
    private String mStudentsCount;
    private String mSubjectId;
    private String students;
    private String mPeriod;
    private Date mSessionStart;
    private Date mSessionEnd;

    public Session(String mSessionId, String mSessionName, String mStudentsCount, String mSubjectId, String students, Date mSessionStart, Date mSessionEnd) {
        this.mSessionId = mSessionId;
        this.mSessionName = mSessionName;
        this.mStudentsCount = mStudentsCount;
        this.mSubjectId = mSubjectId;
        this.students = students;
        this.mSessionStart = mSessionStart;
        this.mSessionEnd = mSessionEnd;
    }

    public Session(String mSessionId, String mSessionName, String mStudentsCount, String mSubjectId, String students, String mPeriod) {
        this.mSessionId = mSessionId;
        this.mSessionName = mSessionName;
        this.mStudentsCount = mStudentsCount;
        this.mSubjectId = mSubjectId;
        this.students = students;
        this.mPeriod = mPeriod;
    }

    public String getPeriod() {
        return mPeriod;
    }

    public void setPeriod(String mPeriod) {
        this.mPeriod = mPeriod;
    }

    public Date getSessionStart() {
        return mSessionStart;
    }

    public void setSessionStart(Date mSessionStart) {
        this.mSessionStart = mSessionStart;
    }

    public Date getSessionEnd() {
        return mSessionEnd;
    }

    public void setSessionEnd(Date mSessionEnd) {
        this.mSessionEnd = mSessionEnd;
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

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }
}
