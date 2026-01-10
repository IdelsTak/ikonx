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
package com.github.idelstak.ikonx.mvu.state;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.state.icons.*;
import com.github.idelstak.ikonx.mvu.state.search.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.mvu.state.view.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;

public record ViewState(
  AppVersion version,
  StageIcons stageIcons,
  IkonQuery query,
  IkonCatalog ikonCatalog,
  Set<Pack> selectedPacks,
  Set<Style> selectedStyles,
  List<PackIkon> displayedIkons,
  Set<PackIkon> favoriteIkons,
  Set<PackIkon> recentIkons,
  ViewMode viewMode,
  PacksFilter filter,
  IkonDetailsDisplay detailsDisplay,
  ActivityState status,
  String statusMessage
  ) {

    public ViewState {
        selectedPacks = Set.copyOf(selectedPacks);
        selectedStyles = Set.copyOf(selectedStyles);
        displayedIkons = List.copyOf(displayedIkons);
        favoriteIkons = Set.copyOf(favoriteIkons);
        recentIkons = Set.copyOf(recentIkons);
    }

    ViewState version(AppVersion version) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState stageIcons(StageIcons ikons) {
        return new ViewState(version, ikons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState search(IkonQuery query) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState select(Set<Pack> packs) {
        return new ViewState(version, stageIcons, query, ikonCatalog, packs, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState styles(Set<Style> styles) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, styles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState display(List<PackIkon> ikons) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          ikons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState favorites(Set<PackIkon> ikons) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, ikons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState recent(Set<PackIkon> ikons) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, ikons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState mode(ViewMode mode) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, mode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState filter(PacksFilter filter) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, statusMessage);
    }

    ViewState show(IkonDetailsDisplay show) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, show, status, statusMessage);
    }
    
    ViewState signal(ActivityState state) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, state, statusMessage);
    }

    ViewState message(String text) {
        return new ViewState(version, stageIcons, query, ikonCatalog, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, filter, detailsDisplay, status, text);
    }

    

    public static ViewState initial() {
        var firstPack = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Enum::name))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("No icon packs found"));

        var ikons = Arrays.stream(firstPack.ikons())
          .map(ikon -> new PackIkon(firstPack, ikon))
          .toList();

        return new ViewState(
          new AppVersion.Unknown(),
          new StageIcons.Unknown(),
          new IkonQuery.Clear(),
          new IkonCatalog(Pack.values()),
          Set.of(firstPack),
          Set.of(new Style.All()),
          ikons,
          Set.of(),
          Set.of(),
          new ViewMode.Grid(),
          new PacksFilter.Hidden(),
          new IkonDetailsDisplay.HideRequested(),
          new ActivityState.Idle(),
          String.format("%d icons found", ikons.size())
        );
    }
}
