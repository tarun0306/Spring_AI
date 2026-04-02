package com.tarun.ai_withjava.model;

public class CodeReviewerModel {

    private String code ;
    private String language;
    private String businessReq;

    public CodeReviewerModel(String code, String language, String businessReq) {
        this.code = code;
        this.language = language;
        this.businessReq = businessReq;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBusinessReq() {
        return businessReq;
    }

    public void setBusinessReq(String businessReq) {
        this.businessReq = businessReq;
    }
}
