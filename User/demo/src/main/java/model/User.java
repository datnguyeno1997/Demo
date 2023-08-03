package model;

import lombok.Builder;
import lombok.Data;
import model.enums.EGender;

import java.util.Date;

@Data
@Builder
public class User {
    private Long id;

    private String name;

    private String phone;

    private String email;

    private String avatar;

    private EGender gender;

    private Date dob;

    private String coverPicture;

    private Role role;

    public User(Long id, String name, String phone, String email, String avatar, EGender gender, Date dob, String coverPicture, Role role) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.avatar = avatar;
        this.gender = gender;
        this.dob = dob;
        this.coverPicture = coverPicture;
        this.role = role;
    }
    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public EGender getGender() {
        return gender;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public String getDob() {
        if(dob == null) return "";
        return dob.toString();
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}