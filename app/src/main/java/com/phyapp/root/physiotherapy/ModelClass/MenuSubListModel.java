package com.phyapp.root.physiotherapy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MenuSubListModel {

    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    @SerializedName("error")
    @Expose
    private Integer error;

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

        @SerializedName("title")
        @Expose
        private String title;

        @SerializedName("iid")
        @Expose
        private String iid;



        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getIid() {
            return iid;
        }

        public void setIid(String iid) {
            this.iid = iid;
        }

    }
}
