package ru.developsdm.amproject.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Per-fragment scope.
 * <p>
 * Created by Daniil Savelyev in 2017.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
