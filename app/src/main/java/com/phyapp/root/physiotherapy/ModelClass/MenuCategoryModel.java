package com.phyapp.root.physiotherapy.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MenuCategoryModel {


    @SerializedName("success")
    @Expose
    private Integer success;

    @SerializedName("error")
    @Expose
    private Integer error;

    @SerializedName("src")
    @Expose
    private ArrayList<Src> src = null;

    @SerializedName("error_msg")
    @Expose
    private String errorMsg;


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

        @SerializedName("sid")
        @Expose
        private Integer sid;
        @SerializedName("title")
        @Expose
        private String title;

        public Integer getSid() {
            return sid;
        }

        public void setSid(Integer sid) {
            this.sid = sid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
