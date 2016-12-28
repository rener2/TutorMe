package com.personalweb.website.form;

import com.personalweb.website.utils.Parser;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


public class PageUser {

    @NotEmpty(message="{error.emailRequired}")
    @Email(message="{error.emailRequired}")
    String email;

    @Size(min = 2, max = 20, message="The length must be between {min} and {max}")
    String firstName;

    @Size(min = 2, max = 20, message="The length must be between {min} and {max}")
    String lastName;

    @NotEmpty(message="{error.passwordRequired}")
    @Size(min = 6, max = 25, message="The length must be between {min} and {max}")
    String password;

    @Size(min = 3, max = 20, message="{error.usernameRequired}")
    String username;

    Long userID;

    byte [] profilePic;

    String education;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date birthdate;

    String speciality;

    String phone;

    String imagesrc;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        if (birthdate == null) return null;
        Integer year = Integer.valueOf(birthdate.toString().substring(0, 4));
        Integer month= Integer.valueOf(birthdate.toString().substring(5, 7));;
        Integer day = Integer.valueOf(birthdate.toString().substring(8, 10));;
        Calendar now = Calendar.getInstance();
        Integer currentYear = now.get(Calendar.YEAR);
        Integer currentMonth = now.get(Calendar.MONTH);
        Integer currentDay = now.get(Calendar.DAY_OF_MONTH);
        if (currentMonth > month) return currentYear - year;
        else if (currentMonth.equals(month) && currentDay > day) return currentYear - year;
        else return currentYear -year-1;
    }

    public String getFullName() {
        if (firstName == null && lastName == null) return null;
        else if (!firstName.isEmpty() && !lastName.isEmpty()) return firstName + ' ' +  lastName;
        else if (lastName.isEmpty()) return firstName;
        return lastName;
    }

    public String getImagesrc() throws IOException{
        if (profilePic == null) return "";
        byte[]   bytesEncoded = Base64.encodeBase64(profilePic);
        String src = "data:image/png;base64," + new String(bytesEncoded);
        return src;
    }

    public void setImagesrc(String imagesrc) {
        this.imagesrc = imagesrc;
    }
}

