package ru.developsdm.amproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * Catalogue object.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

@Data
public class Catalogue implements Serializable {

    List<BannerItem> banners;
    @SerializedName("tematicSets")
    List<NewsItem> themeSets;
    List<NewsItem> news;

}
