package ru.developsdm.amproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Daniil Savelyev in 2017.
 */

@Data
public class BannerItem implements Serializable {

    @SerializedName("img")
    String imageUrl;
    @SerializedName("header")
    String title;
    String description;

}
