package ru.developsdm.amproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

/**
 * REST API root data object.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

@Data
public class DataRoot implements Serializable {
    @SerializedName("catalog")
    Catalogue catalogue;
}
