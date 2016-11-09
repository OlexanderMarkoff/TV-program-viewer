package example.m1.tv_program_viewer.model.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M1 on 09.11.2016.
 */

public class Category {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("picture")
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
