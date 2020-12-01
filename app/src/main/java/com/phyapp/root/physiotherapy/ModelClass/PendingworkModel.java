package com.phyapp.root.physiotherapy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PendingworkModel {


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
        @SerializedName("patient_name")
        @Expose
        private String patientName;

        @SerializedName("service")
        @Expose
        private String service;

          @SerializedName("service_cat")
        @Expose
        private String serviceCat;


        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("time")
        @Expose
        private String time;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("message")
        @Expose
        private String message;

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

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public String getService() {
            return service;
        }

        public void setService(String service) {
            this.service = service;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getServiceCat() {
            return serviceCat;
        }

        public void setServiceCat(String serviceCat) {
            this.serviceCat = serviceCat;
        }
    }
}