/*
 * MIT License
 *
 * Copyright (c) 2023 Hiram K
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.idelstak.ikonx;

import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsFilled;
import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.bootstrapicons.BootstrapIcons;
import org.kordamp.ikonli.boxicons.BoxiconsLogos;
import org.kordamp.ikonli.boxicons.BoxiconsRegular;
import org.kordamp.ikonli.boxicons.BoxiconsSolid;
import org.kordamp.ikonli.bpmn.Bpmn;
import org.kordamp.ikonli.captainicon.Captainicon;
import org.kordamp.ikonli.carbonicons.CarbonIcons;
import org.kordamp.ikonli.codicons.Codicons;
import org.kordamp.ikonli.coreui.CoreUiBrands;
import org.kordamp.ikonli.coreui.CoreUiFree;
import org.kordamp.ikonli.dashicons.Dashicons;
import org.kordamp.ikonli.devicons.Devicons;
import org.kordamp.ikonli.elusive.Elusive;
import org.kordamp.ikonli.entypo.Entypo;
import org.kordamp.ikonli.evaicons.Evaicons;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.fileicons.FileIcons;
import org.kordamp.ikonli.fluentui.FluentUiFilledAL;
import org.kordamp.ikonli.fluentui.FluentUiFilledMZ;
import org.kordamp.ikonli.fluentui.FluentUiRegularAL;
import org.kordamp.ikonli.fluentui.FluentUiRegularMZ;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.kordamp.ikonli.fontawesome5.FontAwesomeBrands;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.fontelico.Fontelico;
import org.kordamp.ikonli.foundation.Foundation;
import org.kordamp.ikonli.hawcons.HawconsFilled;
import org.kordamp.ikonli.hawcons.HawconsStroke;
import org.kordamp.ikonli.icomoon.Icomoon;
import org.kordamp.ikonli.ionicons.Ionicons;
import org.kordamp.ikonli.ionicons4.Ionicons4IOS;
import org.kordamp.ikonli.ionicons4.Ionicons4Logo;
import org.kordamp.ikonli.ionicons4.Ionicons4Material;
import org.kordamp.ikonli.jam.Jam;
import org.kordamp.ikonli.ligaturesymbols.LigatureSymbols;
import org.kordamp.ikonli.lineawesome.LineAwesomeBrands;
import org.kordamp.ikonli.lineawesome.LineAwesomeRegular;
import org.kordamp.ikonli.lineawesome.LineAwesomeSolid;
import org.kordamp.ikonli.linecons.Linecons;
import org.kordamp.ikonli.maki.Maki;
import org.kordamp.ikonli.maki2.Maki2;
import org.kordamp.ikonli.mapicons.Mapicons;
import org.kordamp.ikonli.material.Material;
import org.kordamp.ikonli.material2.*;
import org.kordamp.ikonli.materialdesign.MaterialDesign;
import org.kordamp.ikonli.medicons.Medicons;
import org.kordamp.ikonli.metrizeicons.MetrizeIcons;
import org.kordamp.ikonli.microns.Microns;
import org.kordamp.ikonli.ociicons.Ociicons;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.openiconic.Openiconic;
import org.kordamp.ikonli.paymentfont.PaymentFont;
import org.kordamp.ikonli.prestashopicons.PrestaShopIcons;
import org.kordamp.ikonli.remixicon.RemixiconAL;
import org.kordamp.ikonli.remixicon.RemixiconMZ;
import org.kordamp.ikonli.runestroicons.Runestroicons;
import org.kordamp.ikonli.simpleicons.SimpleIcons;
import org.kordamp.ikonli.simplelineicons.SimpleLineIcons;
import org.kordamp.ikonli.subway.Subway;
import org.kordamp.ikonli.themify.Themify;
import org.kordamp.ikonli.typicons.Typicons;
import org.kordamp.ikonli.unicons.UniconsLine;
import org.kordamp.ikonli.unicons.UniconsMonochrome;
import org.kordamp.ikonli.unicons.UniconsSolid;
import org.kordamp.ikonli.weathericons.WeatherIcons;
import org.kordamp.ikonli.websymbols.Websymbols;
import org.kordamp.ikonli.whhg.WhhgAL;
import org.kordamp.ikonli.whhg.WhhgMZ;
import org.kordamp.ikonli.win10.Win10;
import org.kordamp.ikonli.zondicons.Zondicons;

import java.util.Arrays;
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
    ION_ICONS_4("Ion Icons 4", Stream.concat(
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
    THEMIFY("Themify", Themify.values()),
    OCTICONS("Octicons", Octicons.values()),
    WEB_SYMBOLS("Web Symbols", Websymbols.values()),
    CARBON_ICONS("Carbon Icons", CarbonIcons.values()),
    PRESTA_SHOP_ICONS("Presta Shop Icons", PrestaShopIcons.values()),
    UNICONS("Unicons", Stream.concat(
            Stream.concat(
                    Arrays.stream(UniconsLine.values()).map(line -> (Ikon) line),
                    Arrays.stream(UniconsMonochrome.values()).map(monochrome -> (Ikon) monochrome)
            ),
            Arrays.stream(UniconsSolid.values()).map(solid -> (Ikon) solid)
    ).toArray(Ikon[]::new)),
    LINECONS("Linecons", Linecons.values()),
    CAPTAIN_ICON("Captain Icon", Captainicon.values()),
    JAM("Jam", Jam.values()),
    ZOND_ICONS("Zond Icons", Zondicons.values()),
    FEATHER("Feather", Feather.values()),
    OPENICONIC("Openiconic", Openiconic.values()),
    CODICONS("Codicons", Codicons.values()),
    OCI_ICONS("Oci Icons", Ociicons.values()),
    REMIX_ICON("Remix Icon", Stream.concat(
            Arrays.stream(RemixiconAL.values()).map(al -> (Ikon) al),
            Arrays.stream(RemixiconMZ.values()).map(mz -> (Ikon) mz)

    ).toArray(Ikon[]::new)),
    ION_ICONS("Ion Icons", Ionicons.values()),
    ANT_DESIGN_ICONS("Ant Design Icons", Stream.concat(
            Arrays.stream(AntDesignIconsFilled.values()).map(filled -> (Ikon) filled),
            Arrays.stream(AntDesignIconsOutlined.values()).map(outlined -> (Ikon) outlined)

    ).toArray(Ikon[]::new)),
    SUBWAY("Subway", Subway.values()),
    SIMPLE_LINE_ICONS("Simple Line Icons", SimpleLineIcons.values()),
    FILE_ICONS("File Icons", FileIcons.values()),
    MAKI("Maki", Maki.values()),
    MAKI_2("Maki 2", Maki2.values()),
    WHHG("Whhg", Stream.concat(
            Arrays.stream(WhhgAL.values()).map(al -> (Ikon) al),
            Arrays.stream(WhhgMZ.values()).map(mz -> (Ikon) mz)

    ).toArray(Ikon[]::new)),
    SIMPLE_ICONS("Simple Icons", SimpleIcons.values()),
    FOUNDATION("Foundation", Foundation.values()),
    MICRONS("Microns", Microns.values()),
    LIGATURE_SYMBOLS("Ligature Symbols", LigatureSymbols.values()),
    BPMN("Bpmn", Bpmn.values()),
    TYPICONS("Typicons", Typicons.values()),
    HAWCONS("Hawcons", Stream.concat(
            Arrays.stream(HawconsFilled.values()).map(filled -> (Ikon) filled),
            Arrays.stream(HawconsStroke.values()).map(stroke -> (Ikon) stroke)

    ).toArray(Ikon[]::new)),
    MAP_ICONS("Map Icons", Mapicons.values()),
    METRIZE_ICONS("Metrize Icons", MetrizeIcons.values()),
    CORE_UI("Core UI", Stream.concat(
            Arrays.stream(CoreUiBrands.values()).map(brands -> (Ikon) brands),
            Arrays.stream(CoreUiFree.values()).map(free -> (Ikon) free)

    ).toArray(Ikon[]::new)),
    RUNESTRO_ICONS("Runestro Icons", Runestroicons.values()),
    PAYMENT_FONT("Payment Font", PaymentFont.values()),
    FLUENT_UI("Fluent UI", Stream.concat(
            Stream.concat(
                    Arrays.stream(FluentUiFilledAL.values()).map(filledAL -> (Ikon) filledAL),
                    Arrays.stream(FluentUiFilledMZ.values()).map(filledMZ -> (Ikon) filledMZ)
            ),
            Stream.concat(
                    Arrays.stream(FluentUiRegularAL.values()).map(regularAL -> (Ikon) regularAL),
                    Arrays.stream(FluentUiRegularMZ.values()).map(regularMZ -> (Ikon) regularMZ)
            )
    ).toArray(Ikon[]::new)),
    WIN_10("Win 10", Win10.values()),
    FONTELICO("Fontelico", Fontelico.values()),
    BOX_ICONS("Box Icons", Stream.concat(
            Stream.concat(
                    Arrays.stream(BoxiconsLogos.values()).map(logos -> (Ikon) logos),
                    Arrays.stream(BoxiconsRegular.values()).map(regular -> (Ikon) regular)
            ),
            Arrays.stream(BoxiconsSolid.values()).map(solid -> (Ikon) solid)
    ).toArray(Ikon[]::new)),
    ENTYPO("Entypo", Entypo.values()),
    ICOMOON("Icomoon", Icomoon.values()),
    MED_ICONS("Med Icons", Medicons.values());

    private final String description;
    private final Ikon[] ikons;

    Pack(String description, Ikon[] ikons) {
        this.description = description;
        this.ikons = copy(ikons);
    }

    public Ikon[] getIkons() {
        return copy(ikons);
    }

    @Override
    public String toString() {
        return description;
    }

    private static Ikon[] copy(Ikon[] ikons) {
        return Arrays.copyOf(ikons, ikons.length);
    }
    }
