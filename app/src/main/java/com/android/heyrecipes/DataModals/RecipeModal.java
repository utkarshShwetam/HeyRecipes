package com.android.heyrecipes.DataModals;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "recipes")
public class RecipeModal implements Parcelable {

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "publisher")
    private String publisher;

    @ColumnInfo(name = "ingredients")
    private String [] ingredients;

    @PrimaryKey
    @NonNull
    private String recipe_id;

    @ColumnInfo(name = "image_url")
    private String image_url;

    @ColumnInfo(name = "social_rank")
    private float social_rank;

    @ColumnInfo(name = "timestamp")
    private int timestamp;

    @Ignore
    public RecipeModal() {

    }

    public RecipeModal(String title, String publisher, String[] ingredients, String recipe_id, String image_url, float social_rank,int timestamp) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.recipe_id = recipe_id;
        this.image_url = image_url;
        this.social_rank = social_rank;
        this.timestamp=timestamp;
    }

    protected RecipeModal(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        recipe_id = in.readString();
        image_url = in.readString();
        social_rank = in.readFloat();
        timestamp=in.readInt();
    }

    public static final Creator<RecipeModal> CREATOR = new Creator<RecipeModal>() {
        @Override
        public RecipeModal createFromParcel(Parcel in) {
            return new RecipeModal(in);
        }

        @Override
        public RecipeModal[] newArray(int size) {
            return new RecipeModal[size];
        }
    };

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    @Override
    public String  toString() {
        return "RecipeModal{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipe_id='" + recipe_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeStringArray(ingredients);
        dest.writeString(recipe_id);
        dest.writeString(image_url);
        dest.writeFloat(social_rank);
        dest.writeInt(timestamp);
    }
}
