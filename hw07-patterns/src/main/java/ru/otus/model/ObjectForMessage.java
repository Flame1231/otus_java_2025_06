package ru.otus.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ObjectForMessage {
    private List<String> data;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ObjectForMessage{ data={");
        data.forEach(item -> builder.append(item).append(", "));
        builder.append("}}");
        return builder.toString();
    }
}
