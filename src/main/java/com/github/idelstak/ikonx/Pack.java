package com.github.idelstak.ikonx;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Arrays;
import java.util.Objects;

public enum Pack {
    BOOTSTRAP("Bootstrap", BootstrapIcons.values()),
    MATERIAL("Material", Material.values()),
    MATERIAL_DESIGN("Material Design", MaterialDesign.values());

    private final String description;
    private final Ikon[] ikons;

    Pack(String description, Ikon[] ikons) {
        this.description = description;
        this.ikons = copy(ikons);
    }

    public static Pack from(String text){
        return Arrays.stream(values())
                .filter(pack -> Objects.equals(pack.description, text))
                .findFirst()
                .orElseThrow();
    }

    public String getDescription() {
        return description;
    }

    public Ikon[] getIkons() {
        return copy(ikons);
    }

    private static Ikon[] copy(Ikon[] ikons) {
        return Arrays.copyOf(ikons, ikons.length);
    }

    @Override
    public String toString() {
        return description;
    }
}
