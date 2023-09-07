package com.github.idelstak.ikonx;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.devicons.Devicons;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.hawconsstroke.HawconsStroke;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;
import org.kordamp.ikonli.ionicons4.Ionicons4Logo;
import org.kordamp.ikonli.ionicons4.Ionicons4Material;
import org.kordamp.ikonli.lineawesome.LineAwesomeBrands;
import org.kordamp.ikonli.lineawesome.LineAwesomeRegular;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.material2.*;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.prestashopicons.PrestaShopIcons;
import org.kordamp.ikonli.themify.Themify;
import org.kordamp.ikonli.weathericons.WeatherIcons;
import org.kordamp.ikonli.websymbols.Websymbols;

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
    ).toArray(Ikon[]::new)),
    EVA_ICONS("Eva Icons", Evaicons.values()),
    ION_ICONS("Ion Icons 4", Stream.concat(
            Stream.concat(
                    Arrays.stream(Ionicons4IOS.values()).map(ios -> (Ikon) ios),
                    Arrays.stream(Ionicons4Logo.values()).map(logo -> (Ikon) logo)
            ),
            Arrays.stream(Ionicons4Material.values()).map(material -> (Ikon) material)
    ).toArray(Ikon[]::new)),
    LINE_AWESOME("Line Awesome", Stream.concat(
            Stream.concat(
                    Arrays.stream(LineAwesomeBrands.values()).map(brands -> (Ikon) brands),
                    Arrays.stream(LineAwesomeRegular.values()).map(regular -> (Ikon) regular)
            ),
            Arrays.stream(LineAwesomeSolid.values()).map(solid -> (Ikon) solid)
    ).toArray(Ikon[]::new)),
    WEATHER_ICONS("Weather Icons", WeatherIcons.values()),
    DEVI_ICONS("Devi Icons", Devicons.values()),
    MATERIAL_2("Material 2", Stream.concat(
            Stream.concat(
                    Stream.concat(
                            Arrays.stream(Material2AL.values()).map(al -> (Ikon) al),
                            Arrays.stream(Material2MZ.values()).map(mz -> (Ikon) mz)
                    ),
                    Stream.concat(
                            Arrays.stream(Material2OutlinedAL.values()).map(outlinedAL -> (Ikon) outlinedAL),
                            Arrays.stream(Material2OutlinedMZ.values()).map(outlinedMZ -> (Ikon) outlinedMZ)
                    )
            ),
            Stream.concat(
                    Stream.concat(
                            Arrays.stream(Material2RoundAL.values()).map(roundAL -> (Ikon) roundAL),
                            Arrays.stream(Material2RoundMZ.values()).map(roundMZ -> (Ikon) roundMZ)
                    ),
                    Stream.concat(
                            Arrays.stream(Material2SharpAL.values()).map(sharpAL -> (Ikon) sharpAL),
                            Arrays.stream(Material2SharpMZ.values()).map(sharpMZ -> (Ikon) sharpMZ)
                    )
            )
    ).toArray(Ikon[]::new)),
    DASH_ICONS("Dash Icons", Dashicons.values()),
    ELUSIVE("Elusive", Elusive.values()),
    HAWCONS_STROKE("Hawcons Stroke", HawconsStroke.values()),
    THEMIFY("Themify", Themify.values()),
    OCTICONS("Octicons", Octicons.values()),
    WEB_SYMBOLS("Web Symbols", Websymbols.values()),
    CARBON_ICONS("Carbon Icons", CarbonIcons.values()),
    PRESTA_SHOP_ICONS("Presta Shop Icons", PrestaShopIcons.values());

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
