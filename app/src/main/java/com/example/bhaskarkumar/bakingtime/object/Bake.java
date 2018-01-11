package com.example.bhaskarkumar.bakingtime.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Bake implements Parcelable{
    private int id;
    private String name;
    private List<Steps> steps;
    private List<Ingredients> ingredients;

    public Bake(int id, String name, List<Steps> steps, List<Ingredients> ingredients) {
        this.id = id;
        this.name = name;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    protected Bake(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Bake> CREATOR = new Creator<Bake>() {
        @Override
        public Bake createFromParcel(Parcel in) {
            return new Bake(in);
        }

        @Override
        public Bake[] newArray(int size) {
            return new Bake[size];
        }
    };

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
    }
}
