package io.vjattich.cleaner;

public class StringClassCleaner implements Cleaner {

    private final String stringClazz;

    public StringClassCleaner(String stringClazz) {
        this.stringClazz = stringClazz;
    }

    @Override
    public String clean() {
        return stringClazz.replaceAll("package .*;", "");
    }
}
