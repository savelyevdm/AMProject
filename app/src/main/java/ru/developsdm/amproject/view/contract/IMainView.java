package ru.developsdm.amproject.view.contract;

import ru.developsdm.amproject.model.Catalogue;

/**
 * Contract for {@link ru.developsdm.amproject.view.MainActivity}
 *
 * Created by Daniil Savelyev in 2017.
 */

public interface IMainView extends IMainViewHost {

    void showCatalogueView(Catalogue catalogue);

    void showMockView(String title, String tag);

}
