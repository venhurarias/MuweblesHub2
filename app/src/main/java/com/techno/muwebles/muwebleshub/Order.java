package com.techno.muwebles.muwebleshub;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by FArias on 10/27/2016.
 */

public class Order {
    @SerializedName("id_sale")
    public String id_sale;

    @SerializedName("item_id")
    public String item_id;

    @SerializedName("contact")
    public String contact;

    @SerializedName("email")
    public String email;

    @SerializedName("address")
    public String address;

    @SerializedName("first")
    public String first;

    @SerializedName("last")
    public String last;

    @SerializedName("status")
    public String status;

    @SerializedName("number")
    public String number;

    @SerializedName("pricetotal")
    public String pricetotal;



}
