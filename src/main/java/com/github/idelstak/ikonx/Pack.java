package com.github.idelstak.ikonx;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public enum Pack {
    BOOTSTRAP("Bootstrap", BootstrapIcons.values()),
    MATERIAL("Material", Material.values()),
    MATERIAL_DESIGN("Material Design", MaterialDesign.values()),
    FONT_AWESOME("Font Awesome", FontAwesome.values()),
    FONT_AWESOME_5("Font Awesome 5", Stream.concat(
            Stream.concat(
                    Arrays.stream(FontAwesomeBrands.values()).map(brands -> (Ikon) brands),
                    Arrays.stream(FontAwesomeRegular.values()).map(regular -> (Ikon) regular)
            ),
            Arrays.stream(FontAwesomeSolid.values()).map(solid -> (Ikon) solid)
    ).toArray(Ikon[]::new));

    private final String description;
    private final Ikon[] ikons;

    Pack(String description, Ikon[] ikons) {
        this.description = description;
        this.ikons = copy(ikons);
    }

    public static Pack from(String text) {
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
