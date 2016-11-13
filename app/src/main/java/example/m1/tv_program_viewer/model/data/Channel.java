package example.m1.tv_program_viewer.model.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by M1 on 09.11.2016.
 */

public class Channel implements Parcelable {

    @SerializedName("id")
    private int channelId;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("picture")
    private String picture;
    @SerializedName("category_id")
    private int categoryId;

    private boolean isFavorite;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.channelId);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.picture);
        dest.writeInt(this.categoryId);
    }

    public Channel() {
    }

    protected Channel(Parcel in) {
        this.channelId = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.picture = in.readString();
        this.categoryId = in.readInt();
    }

    public static final Creator<Channel> CREATOR = new Creator<Channel>() {
        @Override
        public Channel createFromParcel(Parcel source) {
            return new Channel(source);
        }

        @Override
        public Channel[] newArray(int size) {
            return new Channel[size];
        }
    };
}
