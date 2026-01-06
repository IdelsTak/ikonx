/*
 * MIT License
 *
 * Copyright (c) 2026 Hiram K
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
package com.github.idelstak.ikonx.icons;

import java.util.*;
import java.util.stream.*;
import org.kordamp.ikonli.*;
import org.kordamp.ikonli.antdesignicons.*;
import org.kordamp.ikonli.bootstrapicons.*;
import org.kordamp.ikonli.boxicons.*;
import org.kordamp.ikonli.bpmn.*;
import org.kordamp.ikonli.bytedance.*;
import org.kordamp.ikonli.captainicon.*;
import org.kordamp.ikonli.carbonicons.*;
import org.kordamp.ikonli.codicons.*;
import org.kordamp.ikonli.coreui.*;
import org.kordamp.ikonli.dashicons.*;
import org.kordamp.ikonli.devicons.*;
import org.kordamp.ikonli.elusive.*;
import org.kordamp.ikonli.entypo.*;
import org.kordamp.ikonli.evaicons.*;
import org.kordamp.ikonli.feather.*;
import org.kordamp.ikonli.fileicons.*;
import org.kordamp.ikonli.fluentui.*;
import org.kordamp.ikonli.fontawesome.*;
import org.kordamp.ikonli.fontelico.*;
import org.kordamp.ikonli.foundation.*;
import org.kordamp.ikonli.hawcons.*;
import org.kordamp.ikonli.icomoon.*;
import org.kordamp.ikonli.ionicons.*;
import org.kordamp.ikonli.ionicons4.*;
import org.kordamp.ikonli.jam.*;
import org.kordamp.ikonli.ligaturesymbols.*;
import org.kordamp.ikonli.lineawesome.*;
import org.kordamp.ikonli.linecons.*;
import org.kordamp.ikonli.maki.*;
import org.kordamp.ikonli.maki2.*;
import org.kordamp.ikonli.mapicons.*;
import org.kordamp.ikonli.material.*;
import org.kordamp.ikonli.material2.*;
import org.kordamp.ikonli.materialdesign.*;
import org.kordamp.ikonli.materialdesign2.*;
import org.kordamp.ikonli.medicons.*;
import org.kordamp.ikonli.metrizeicons.*;
import org.kordamp.ikonli.microns.*;
import org.kordamp.ikonli.ociicons.*;
import org.kordamp.ikonli.octicons.*;
import org.kordamp.ikonli.openiconic.*;
import org.kordamp.ikonli.paymentfont.*;
import org.kordamp.ikonli.prestashopicons.*;
import org.kordamp.ikonli.remixicon.*;
import org.kordamp.ikonli.runestroicons.*;
import org.kordamp.ikonli.simpleicons.*;
import org.kordamp.ikonli.simplelineicons.*;
import org.kordamp.ikonli.subway.*;
import org.kordamp.ikonli.themify.*;
import org.kordamp.ikonli.typicons.*;
import org.kordamp.ikonli.unicons.*;
import org.kordamp.ikonli.weathericons.*;
import org.kordamp.ikonli.websymbols.*;
import org.kordamp.ikonli.whhg.*;
import org.kordamp.ikonli.win10.*;
import org.kordamp.ikonli.zondicons.*;

public enum Pack {
    BYTE_DANCE(
      "Byte Dance",
      Stream.of(
        Arrays.stream(BytedanceIconsRegularAL.values()),
        Arrays.stream(BytedanceIconsRegularMZ.values()),
        Arrays.stream(BytedanceIconsBoldAL.values()),
        Arrays.stream(BytedanceIconsBoldMZ.values()),
        Arrays.stream(BytedanceIconsExtraBoldAL.values()),
        Arrays.stream(BytedanceIconsExtraBoldMZ.values())
      )
        .flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)),
    FONT_AWESOME_6(
      "Font Awesome 6",
      Stream.of(
        Arrays.stream(org.kordamp.ikonli.fontawesome6.FontAwesomeBrands.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome6.FontAwesomeRegular.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.values())
      )
        .flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)),
    MATERIAL_DESIGN_2(
      "Material Design 2",
      Stream.of(
        Arrays.stream(MaterialDesignA.values()),
        Arrays.stream(MaterialDesignB.values()),
        Arrays.stream(MaterialDesignC.values()),
        Arrays.stream(MaterialDesignD.values()),
        Arrays.stream(MaterialDesignE.values()),
        Arrays.stream(MaterialDesignF.values()),
        Arrays.stream(MaterialDesignG.values()),
        Arrays.stream(MaterialDesignH.values()),
        Arrays.stream(MaterialDesignI.values()),
        Arrays.stream(MaterialDesignJ.values()),
        Arrays.stream(MaterialDesignK.values()),
        Arrays.stream(MaterialDesignL.values()),
        Arrays.stream(MaterialDesignM.values()),
        Arrays.stream(MaterialDesignN.values()),
        Arrays.stream(MaterialDesignO.values()),
        Arrays.stream(MaterialDesignP.values()),
        Arrays.stream(MaterialDesignQ.values()),
        Arrays.stream(MaterialDesignR.values()),
        Arrays.stream(MaterialDesignS.values()),
        Arrays.stream(MaterialDesignT.values()),
        Arrays.stream(MaterialDesignU.values()),
        Arrays.stream(MaterialDesignV.values()),
        Arrays.stream(MaterialDesignW.values()),
        Arrays.stream(MaterialDesignX.values()),
        Arrays.stream(MaterialDesignY.values()),
        Arrays.stream(MaterialDesignZ.values())
      )
        .flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)),
    BOOTSTRAP("Bootstrap", BootstrapIcons.values()),
    MATERIAL("Material", Material.values()),
    MATERIAL_DESIGN("Material Design", MaterialDesign.values()),
    FONT_AWESOME("Font Awesome", FontAwesome.values()),
    FONT_AWESOME_5(
      "Font Awesome 5",
      Stream.of(
        Arrays.stream(org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome5.FontAwesomeRegular.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    EVA_ICONS("Eva Icons", Evaicons.values()),
    ION_ICONS_4(
      "Ion Icons 4",
      Stream.of(
        Arrays.stream(Ionicons4IOS.values()),
        Arrays.stream(Ionicons4Logo.values()),
        Arrays.stream(Ionicons4Material.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    LINE_AWESOME(
      "Line Awesome",
      Stream.of(
        Arrays.stream(LineAwesomeBrands.values()),
        Arrays.stream(LineAwesomeRegular.values()),
        Arrays.stream(LineAwesomeSolid.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    WEATHER_ICONS("Weather Icons", WeatherIcons.values()),
    DEVI_ICONS("Devi Icons", Devicons.values()),
    MATERIAL_2(
      "Material 2",
      Stream.of(
        Arrays.stream(Material2AL.values()),
        Arrays.stream(Material2MZ.values()),
        Arrays.stream(Material2OutlinedAL.values()),
        Arrays.stream(Material2OutlinedMZ.values()),
        Arrays.stream(Material2RoundAL.values()),
        Arrays.stream(Material2RoundMZ.values()),
        Arrays.stream(Material2SharpAL.values()),
        Arrays.stream(Material2SharpMZ.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    DASH_ICONS("Dash Icons", Dashicons.values()),
    ELUSIVE("Elusive", Elusive.values()),
    THEMIFY("Themify", Themify.values()),
    OCTICONS("Octicons", Octicons.values()),
    WEB_SYMBOLS("Web Symbols", Websymbols.values()),
    CARBON_ICONS("Carbon Icons", CarbonIcons.values()),
    PRESTA_SHOP_ICONS("Presta Shop Icons", PrestaShopIcons.values()),
    UNICONS(
      "Unicons",
      Stream.of(
        Arrays.stream(UniconsLine.values()),
        Arrays.stream(UniconsMonochrome.values()),
        Arrays.stream(UniconsSolid.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    LINECONS("Linecons", Linecons.values()),
    CAPTAIN_ICON("Captain Icon", Captainicon.values()),
    JAM("Jam", Jam.values()),
    ZOND_ICONS("Zond Icons", Zondicons.values()),
    FEATHER("Feather", Feather.values()),
    OPENICONIC("Openiconic", Openiconic.values()),
    CODICONS("Codicons", Codicons.values()),
    OCI_ICONS("Oci Icons", Ociicons.values()),
    REMIX_ICON(
      "Remix Icon",
      Stream.of(
        Arrays.stream(RemixiconAL.values()),
        Arrays.stream(RemixiconMZ.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    ION_ICONS("Ion Icons", Ionicons.values()),
    ANT_DESIGN_ICONS(
      "Ant Design Icons",
      Stream.of(
        Arrays.stream(AntDesignIconsFilled.values()),
        Arrays.stream(AntDesignIconsOutlined.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    SUBWAY("Subway", Subway.values()),
    SIMPLE_LINE_ICONS("Simple Line Icons", SimpleLineIcons.values()),
    FILE_ICONS("File Icons", FileIcons.values()),
    MAKI("Maki", Maki.values()),
    MAKI_2("Maki 2", Maki2.values()),
    WHHG(
      "Whhg",
      Stream.of(
        Arrays.stream(WhhgAL.values()),
        Arrays.stream(WhhgMZ.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    SIMPLE_ICONS("Simple Icons", SimpleIcons.values()),
    FOUNDATION("Foundation", Foundation.values()),
    MICRONS("Microns", Microns.values()),
    LIGATURE_SYMBOLS("Ligature Symbols", LigatureSymbols.values()),
    BPMN("Bpmn", Bpmn.values()),
    TYPICONS("Typicons", Typicons.values()),
    HAWCONS(
      "Hawcons",
      Stream.of(
        Arrays.stream(HawconsFilled.values()),
        Arrays.stream(HawconsStroke.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    MAP_ICONS("Map Icons", Mapicons.values()),
    METRIZE_ICONS("Metrize Icons", MetrizeIcons.values()),
    CORE_UI(
      "Core UI",
      Stream.of(
        Arrays.stream(CoreUiBrands.values()),
        Arrays.stream(CoreUiFree.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    RUNESTRO_ICONS("Runestro Icons", Runestroicons.values()),
    PAYMENT_FONT("Payment Font", PaymentFont.values()),
    FLUENT_UI(
      "Fluent UI",
      Stream.of(
        Arrays.stream(FluentUiFilledAL.values()),
        Arrays.stream(FluentUiFilledMZ.values()),
        Arrays.stream(FluentUiRegularAL.values()),
        Arrays.stream(FluentUiRegularMZ.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
    WIN_10("Win 10", Win10.values()),
    FONTELICO("Fontelico", Fontelico.values()),
    BOX_ICONS(
      "Box Icons",
      Stream.of(
        Arrays.stream(BoxiconsLogos.values()),
        Arrays.stream(BoxiconsRegular.values()),
        Arrays.stream(BoxiconsSolid.values())
      ).flatMap(stream -> stream)
        .map(icon -> (Ikon) icon)
        .toArray(Ikon[]::new)
    ),
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
