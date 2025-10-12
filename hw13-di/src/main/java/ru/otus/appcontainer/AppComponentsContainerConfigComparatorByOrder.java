package ru.otus.appcontainer;

import java.util.Comparator;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

public class AppComponentsContainerConfigComparatorByOrder implements Comparator<Class<?>> {

    @Override
    public int compare(Class<?> o1, Class<?> o2) {
        return Integer.compare(
                o1.getAnnotation(AppComponentsContainerConfig.class).order(),
                o2.getAnnotation(AppComponentsContainerConfig.class).order());
    }
}
