/*
 * The MIT License
 * Copyright © 2026 Hiram K. <https://github.com/IdelsTak>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)),
    FONT_AWESOME_6(
      "Font Awesome 6",
      Stream.of(
        Arrays.stream(org.kordamp.ikonli.fontawesome6.FontAwesomeBrands.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome6.FontAwesomeRegular.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome6.FontAwesomeSolid.values())
      )
        .flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)),
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
        .map(ikon -> ikon.name().toLowerCase().endsWith("_outline")
                         ? new StyledIkon(ikon, new Style.Outlined())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    BOOTSTRAP(
      "Bootstrap",
      Arrays.stream(BootstrapIcons.values())
        .map(ikon -> ikon.name().toLowerCase().endsWith("_fill")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    MATERIAL(
      "Material",
      Arrays.stream(Material.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_fill")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : ikon.name().toLowerCase().endsWith("_outline")
                             ? new StyledIkon(ikon, new Style.Outlined()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    MATERIAL_DESIGN(
      "Material Design",
      Arrays.stream(MaterialDesign.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_fill")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : ikon.name().toLowerCase().endsWith("_outline")
                             ? new StyledIkon(ikon, new Style.Outlined()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    FONT_AWESOME(
      "Font Awesome",
      Arrays.stream(FontAwesome.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    FONT_AWESOME_5(
      "Font Awesome 5",
      Stream.of(
        Arrays.stream(org.kordamp.ikonli.fontawesome5.FontAwesomeBrands.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome5.FontAwesomeRegular.values()),
        Arrays.stream(org.kordamp.ikonli.fontawesome5.FontAwesomeSolid.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    EVA_ICONS(
      "Eva Icons",
      Arrays.stream(Evaicons.values())
        .map(ikon -> ikon.name().toLowerCase().endsWith("_outline")
                         ? new StyledIkon(ikon, new Style.Outlined())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    ION_ICONS_4(
      "Ion Icons 4",
      Stream.of(
        Arrays.stream(Ionicons4IOS.values()),
        Arrays.stream(Ionicons4Logo.values()),
        Arrays.stream(Ionicons4Material.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    LINE_AWESOME(
      "Line Awesome",
      Stream.of(
        Arrays.stream(LineAwesomeBrands.values()),
        Arrays.stream(LineAwesomeRegular.values()),
        Arrays.stream(LineAwesomeSolid.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    WEATHER_ICONS(
      "Weather Icons",
      Arrays.stream(WeatherIcons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    DEVI_ICONS(
      "Devi Icons",
      Arrays.stream(Devicons.values())
        .map(ikon -> ikon.name().toLowerCase().endsWith("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
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
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    DASH_ICONS(
      "Dash Icons",
      Arrays.stream(Dashicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    ELUSIVE(
      "Elusive",
      Arrays.stream(Elusive.values())
        .map(ikon -> ikon.name().toLowerCase().endsWith("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    THEMIFY(
      "Themify",
      Arrays.stream(Themify.values())
        .map(ikon -> ikon.name().toLowerCase().endsWith("_full")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : ikon.name().toLowerCase().endsWith("_alt")
                             ? new StyledIkon(ikon, new Style.Alternate()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    OCTICONS(
      "Octicons",
      Arrays.stream(Octicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_fill")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    WEB_SYMBOLS(
      "Web Symbols",
      Arrays.stream(Websymbols.values())
        .map(ikon -> ikon.name().toLowerCase().endsWith("fill")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : ikon.name().toLowerCase().endsWith("outline")
                             ? new StyledIkon(ikon, new Style.Outlined()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    CARBON_ICONS(
      "Carbon Icons",
      Arrays.stream(CarbonIcons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : ikon.name().toLowerCase().contains("_filled")
                             ? new StyledIkon(ikon, new Style.Filled()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    PRESTA_SHOP_ICONS(
      "Presta Shop Icons",
      Arrays.stream(PrestaShopIcons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    UNICONS(
      "Unicons",
      Stream.of(
        Arrays.stream(UniconsLine.values()),
        Arrays.stream(UniconsMonochrome.values()),
        Arrays.stream(UniconsSolid.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    LINECONS(
      "Linecons",
      Arrays.stream(Linecons.values())
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    CAPTAIN_ICON(
      "Captain Icon",
      Arrays.stream(Captainicon.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    JAM(
      "Jam",
      Arrays.stream(Jam.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_f")
                         ? new StyledIkon(ikon, new Style.Filled())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    ZOND_ICONS(
      "Zond Icons",
      Arrays.stream(Zondicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_outline")
                         ? new StyledIkon(ikon, new Style.Outlined())
                         : ikon.name().toLowerCase().contains("_solid")
                             ? new StyledIkon(ikon, new Style.Solid()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    FEATHER(
      "Feather",
      Arrays.stream(Feather.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    OPENICONIC(
      "Openiconic",
      Arrays.stream(Openiconic.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    CODICONS(
      "Codicons",
      Arrays.stream(Codicons.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    OCI_ICONS(
      "Oci Icons",
      Arrays.stream(Ociicons.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    REMIX_ICON(
      "Remix Icon",
      Stream.of(
        Arrays.stream(RemixiconAL.values()),
        Arrays.stream(RemixiconMZ.values())
      ).flatMap(stream -> stream)
        .map(ikon -> ikon.name().toLowerCase().contains("_line")
                         ? new StyledIkon(ikon, new Style.Line())
                         : ikon.name().toLowerCase().contains("_fill")
                             ? new StyledIkon(ikon, new Style.Filled()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    ION_ICONS(
      "Ion Icons",
      Arrays.stream(Ionicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : ikon.name().toLowerCase().contains("_outline")
                             ? new StyledIkon(ikon, new Style.Outlined()) : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    ANT_DESIGN_ICONS(
      "Ant Design Icons",
      Stream.of(
        Arrays.stream(AntDesignIconsFilled.values()),
        Arrays.stream(AntDesignIconsOutlined.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    SUBWAY(
      "Subway",
      Arrays.stream(Subway.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    SIMPLE_LINE_ICONS(
      "Simple Line Icons",
      Arrays.stream(SimpleLineIcons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    FILE_ICONS(
      "File Icons",
      Arrays.stream(FileIcons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    MAKI(
      "Maki",
      Arrays.stream(Maki.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    MAKI_2(
      "Maki 2",
      Arrays.stream(Maki2.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    WHHG(
      "Whhg",
      Stream.of(
        Arrays.stream(WhhgAL.values()),
        Arrays.stream(WhhgMZ.values())
      ).flatMap(stream -> stream)
        .map(ikon -> ikon.name().toLowerCase().contains("alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    SIMPLE_ICONS(
      "Simple Icons",
      Arrays.stream(SimpleIcons.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    FOUNDATION(
      "Foundation",
      Arrays.stream(Foundation.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    MICRONS(
      "Microns",
      Arrays.stream(Microns.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    LIGATURE_SYMBOLS(
      "Ligature Symbols",
      Arrays.stream(LigatureSymbols.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    BPMN(
      "Bpmn",
      Arrays.stream(Bpmn.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    TYPICONS(
      "Typicons",
      Arrays.stream(Typicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("outline")
                         ? new StyledIkon(ikon, new Style.Outlined())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    HAWCONS(
      "Hawcons",
      Stream.of(
        Arrays.stream(HawconsFilled.values()),
        Arrays.stream(HawconsStroke.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    MAP_ICONS(
      "Map Icons",
      Arrays.stream(Mapicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("_alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    METRIZE_ICONS(
      "Metrize Icons",
      Arrays.stream(MetrizeIcons.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    CORE_UI(
      "Core UI",
      Stream.of(
        Arrays.stream(CoreUiBrands.values()),
        Arrays.stream(CoreUiFree.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    RUNESTRO_ICONS(
      "Runestro Icons",
      Arrays.stream(Runestroicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    PAYMENT_FONT(
      "Payment Font",
      Arrays.stream(PaymentFont.values())
        .map(ikon -> ikon.name().toLowerCase().contains("alt")
                         ? new StyledIkon(ikon, new Style.Alternate())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    ),
    FLUENT_UI(
      "Fluent UI",
      Stream.of(
        Arrays.stream(FluentUiFilledAL.values()),
        Arrays.stream(FluentUiFilledMZ.values()),
        Arrays.stream(FluentUiRegularAL.values()),
        Arrays.stream(FluentUiRegularMZ.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    WIN_10(
      "Win 10",
      Arrays.stream(Win10.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    FONTELICO(
      "Fontelico",
      Arrays.stream(Fontelico.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    BOX_ICONS(
      "Box Icons",
      Stream.of(
        Arrays.stream(BoxiconsLogos.values()),
        Arrays.stream(BoxiconsRegular.values()),
        Arrays.stream(BoxiconsSolid.values())
      ).flatMap(stream -> stream)
        .map(ikon -> new StyledIkon(ikon, styleFromName(ikon.getClass().getSimpleName())))
        .toArray(StyledIkon[]::new)
    ),
    ENTYPO(
      "Entypo",
      Arrays.stream(Entypo.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    ICOMOON(
      "Icomoon",
      Arrays.stream(Icomoon.values())
        .map(Pack::regular)
        .toArray(StyledIkon[]::new)
    ),
    MED_ICONS(
      "Med Icons",
      Arrays.stream(Medicons.values())
        .map(ikon -> ikon.name().toLowerCase().contains("square")
                         ? new StyledIkon(ikon, new Style.Square())
                         : regular(ikon))
        .toArray(StyledIkon[]::new)
    );
    private final String description;
    private final StyledIkon[] ikons;

    Pack(String description, StyledIkon[] ikons) {
        this.description = description;
        this.ikons = copy(ikons);
    }

    public StyledIkon[] ikons() {
        return copy(ikons);
    }

    @Override
    public String toString() {
        return description;
    }

    private StyledIkon[] copy(StyledIkon[] ikons) {
        return Arrays.copyOf(ikons, ikons.length);
    }

    private static StyledIkon regular(Ikon ikon) {
        return new StyledIkon(ikon, new Style.Regular());
    }

    private static Style styleFromName(String name) {
        if (name.toLowerCase().contains("bold") && !name.toLowerCase().contains("extrabold")) {
            return new Style.Bold();
        }
        if (name.toLowerCase().contains("extrabold")) {
            return new Style.ExtraBold();
        }
        if (name.toLowerCase().contains("solid")) {
            return new Style.Solid();
        }
        if (name.toLowerCase().contains("logo")) {
            return new Style.Logo();
        }
        if (name.toLowerCase().contains("brand")) {
            return new Style.Brand();
        }
        if (name.toLowerCase().contains("filled")) {
            return new Style.Filled();
        }
        if (name.toLowerCase().contains("stroke")) {
            return new Style.Stroke();
        }
        if (name.toLowerCase().contains("line") && !name.toLowerCase().contains("outline")) {
            return new Style.Line();
        }
        if (name.toLowerCase().contains("outline")) {
            return new Style.Outlined();
        }
        if (name.toLowerCase().contains("monochrome")) {
            return new Style.Monochrome();
        }
        if (name.toLowerCase().contains("round")) {
            return new Style.Round();
        }
        if (name.toLowerCase().contains("sharp")) {
            return new Style.Sharp();
        }
        return new Style.Regular();
    }
}
