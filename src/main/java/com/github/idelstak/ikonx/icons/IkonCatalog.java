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

public final class IkonCatalog {

    private final Map<Pack, List<PackIkon>> packs;
    private final Map<Style, List<PackIkon>> styles;
    private final List<PackIkon> all;
    private final List<Pack> orderedPacks;
    private final List<Style> orderedStyles;

    public IkonCatalog(Pack[] packs) {
        var allIcons = Arrays.stream(packs)
          .flatMap(pack -> Arrays.stream(pack.ikons())
            .map(ikon -> new PackIkon(pack, ikon)))
          .toList();
        this.all = allIcons;
        this.packs = allIcons.stream()
          .collect(
            Collectors.groupingBy(
              PackIkon::pack,
              () -> new EnumMap<>(Pack.class),
              Collectors.toUnmodifiableList()
            )
          );
        this.styles = allIcons.stream()
          .collect(
            Collectors.groupingBy(
              ikon -> ikon.styledIkon().style(),
              Collectors.toUnmodifiableList()
            )
          );
        this.styles.put(new Style.All(), all);
        this.orderedPacks = this.packs.keySet().stream()
          .sorted(Comparator.comparing(Enum::name))
          .toList();
        this.orderedStyles = this.styles.keySet().stream()
          .sorted(Comparator.comparing(Style::displayName))
          .toList();
    }

    public List<PackIkon> byPack(Pack pack) {
        return packs.getOrDefault(pack, List.of());
    }

    public List<PackIkon> byStyle(Style style) {
        return styles.getOrDefault(style, List.of());
    }

    public List<PackIkon> all() {
        return List.copyOf(all);
    }

    public List<Pack> orderedPacks() {
        return List.copyOf(orderedPacks);
    }

    public List<Style> orderedStyles() {
        return List.copyOf(orderedStyles);
    }
}
