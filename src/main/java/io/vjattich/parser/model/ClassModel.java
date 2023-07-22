package io.vjattich.parser.model;

import java.util.List;
import java.util.Objects;

public class ClassModel {

    public String name;
    public List<FieldModel> fields;

    public ClassModel setName(String name) {
        this.name = name;
        return this;
    }

    public ClassModel setFields(List<FieldModel> fields) {
        this.fields = fields;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClassModel that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(fields, that.fields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fields);
    }

}
