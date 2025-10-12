package ru.otus.appcontainer;

import java.lang.reflect.Method;
import java.util.Comparator;
import ru.otus.appcontainer.api.AppComponent;

public class AppComponentComparatorByOrder implements Comparator<Method> {

    @Override
    public int compare(Method o1, Method o2) {
        return Integer.compare(
                o1.getAnnotation(AppComponent.class).order(),
                o2.getAnnotation(AppComponent.class).order());
    }
}
