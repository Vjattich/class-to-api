package io.vjattich.parser.model;

import java.util.Objects;

public class FieldModel {

    public String name;
    public String type;
    public FieldModel subType;

    public FieldModel setName(String name) {
        this.name = name;
        return this;
    }

    public FieldModel setType(String type) {
        this.type = type;
        return this;
    }

    public FieldModel setSubType(FieldModel subType) {
        this.subType = subType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldModel that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
