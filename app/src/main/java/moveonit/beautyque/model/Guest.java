package moveonit.beautyque.model;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.annotations.SerializedName;

public class Guest {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("surname")
    private String surname;
    @SerializedName("city")
    private String city;
    @SerializedName("address")
    private String address;
    @SerializedName("birthday")
    private String birthday;
    @SerializedName("gender")
    private String gender;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("deleted_at")
    private Object deletedAt;
    @SerializedName("province")
    private String province;

    @SerializedName("id")
    public Integer getId() {
        return id;
    }

    @SerializedName("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("name")
    public String getName() {
        return name;
    }

    @SerializedName("name")
    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("surname")
    public String getSurname() {
        return surname;
    }

    @SerializedName("surname")
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @SerializedName("city")
    public String getCity() {
        return city;
    }

    @SerializedName("city")
    public void setCity(String city) {
        this.city = city;
    }

    @SerializedName("address")
    public String getAddress() {
        return address;
    }

    @SerializedName("address")
    public void setAddress(String address) {
        this.address = address;
    }

    @SerializedName("birthday")
    public String getBirthday() {
        return birthday;
    }

    @SerializedName("birthday")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @SerializedName("gender")
    public String getGender() {
        return gender;
    }

    @SerializedName("gender")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @SerializedName("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @SerializedName("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @SerializedName("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @SerializedName("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @SerializedName("deleted_at")
    public Object getDeletedAt() {
        return deletedAt;
    }

    @SerializedName("deleted_at")
    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    @SerializedName("province")
    public String getProvince() {
        return province;
    }

    @SerializedName("province")
    public void setProvince(String province) {
        this.province = province;
    }

}