package com.techno.muwebles.muwebleshub;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by FArias on 10/27/2016.
 */

public class Product {
    @SerializedName("item_id")
    public String item_id;

    @SerializedName("name")
    public String name;

    @SerializedName("type")
    public String type;

    @SerializedName("price")
    public String price;

    @SerializedName("description")
    public String description;

    @SerializedName("email")
    public String email;

    @SerializedName("image")
    public String image;

    @SerializedName("path")
    public String path;

}
