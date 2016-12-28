package com.personalweb.website.form;


public class Advertisement {

    String subject;

    String studyLanguage;

    String hourlyFee;

    String contact;

    String description;

    Long popularity;

    PageUser poster;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudyLanguage() {
        return studyLanguage;
    }

    public void setStudyLanguage(String studyLanguage) {
        this.studyLanguage = studyLanguage;
    }

    public String getHourlyFee() {
        return hourlyFee;
    }

    public void setHourlyFee(String hourlyFee) {
        this.hourlyFee = hourlyFee;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPopularity() {
        return popularity;
    }

    public void setPopularity(Long popularity) {
        this.popularity = popularity;
    }

    public PageUser getPoster() {
        return poster;
    }

    public void setPoster(PageUser poster) {
        this.poster = poster;
    }
}
