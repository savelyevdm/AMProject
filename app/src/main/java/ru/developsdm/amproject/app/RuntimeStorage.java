package ru.developsdm.amproject.app;

import lombok.Data;
import ru.developsdm.amproject.model.Catalogue;

/**
 * Application data object to store app-scoped variables.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */

@Data
public class RuntimeStorage {

    /**
     * Catalogue data from REST API.
     */
    Catalogue catalogue;
}
