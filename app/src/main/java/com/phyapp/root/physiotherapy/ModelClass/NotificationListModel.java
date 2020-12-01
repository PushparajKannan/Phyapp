package com.phyapp.root.physiotherapy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationListModel {


    @SerializedName("success")
    @Expose
    private Integer success;


    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("error_msg")
    @Expose
    private String errorMsg;


    @SerializedName("src")
    @Expose
    private ArrayList<Src> src = null;


    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public ArrayList<Src> getSrc() {
        return src;
    }

    public void setSrc(ArrayList<Src> src) {
        this.src = src;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public class Src {

        @SerializedName("aid")
        @Expose
        private Integer aid;
        @SerializedName("phid")
        @Expose
        private Integer phid;
        @SerializedName("mid")
        @Expose
        private String mid;
        @SerializedName("sid")
        @Expose
        private String sid;
        @SerializedName("iid")
        @Expose
        private String iid;
        @SerializedName("patient mobile")
        @Expose
        private String patientMobile;
        @SerializedName("admin_mobile")
        @Expose
        private String adminMobile;
        @SerializedName("appointment date")
        @Expose
        private String appointmentDate;
        @SerializedName("appointment time")
        @Expose
        private String appointmentTime;
        @SerializedName("assigned physiotherapist")
        @Expose
        private String assignedPhysiotherapist;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("charge visit")
        @Expose
        private String chargeVisit;

        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }

        public Integer getPhid() {
            return phid;
        }

        public void setPhid(Integer phid) {
            this.phid = phid;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public String getIid() {
            return iid;
        }

        public void setIid(String iid) {
            this.iid = iid;
        }

        public String getPatientMobile() {
            return patientMobile;
        }

        public void setPatientMobile(String patientMobile) {
            this.patientMobile = patientMobile;
        }

        public String getAdminMobile() {
            return adminMobile;
        }

        public void setAdminMobile(String adminMobile) {
            this.adminMobile = adminMobile;
        }

        public String getAppointmentDate() {
            return appointmentDate;
        }

        public void setAppointmentDate(String appointmentDate) {
            this.appointmentDate = appointmentDate;
        }

        public String getAppointmentTime() {
            return appointmentTime;
        }

        public void setAppointmentTime(String appointmentTime) {
            this.appointmentTime = appointmentTime;
        }

        public String getAssignedPhysiotherapist() {
            return assignedPhysiotherapist;
        }

        public void setAssignedPhysiotherapist(String assignedPhysiotherapist) {
            this.assignedPhysiotherapist = assignedPhysiotherapist;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getChargeVisit() {
            return chargeVisit;
        }

        public void setChargeVisit(String chargeVisit) {
            this.chargeVisit = chargeVisit;
        }

    }
}
