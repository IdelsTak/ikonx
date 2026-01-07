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
import com.github.idelstak.ikonx.mvu.state.ActivityState.Idle;
import com.github.idelstak.ikonx.mvu.state.icons.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;

public record ViewState(
  AppVersion version,
  StageIcons stageIcons,
  String searchText,
  Set<Pack> selectedPacks,
  Set<Style> selectedStyles,
  List<PackIkon> displayedIkons,
  List<PackIkon> favoriteIkons,
  List<PackIkon> recentIkons,
  ViewMode viewMode,
  ActivityState status,
  String statusMessage
  ) {

    public ViewState {
        selectedPacks = Set.copyOf(selectedPacks);
        selectedStyles = Set.copyOf(selectedStyles);
        displayedIkons = List.copyOf(displayedIkons);
        favoriteIkons = List.copyOf(favoriteIkons);
        recentIkons = List.copyOf(recentIkons);
    }

    ViewState version(AppVersion version) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState stageIcons(StageIcons ikons) {
        return new ViewState(version, ikons, searchText, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState search(String text) {
        return new ViewState(version, stageIcons, text, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState select(Set<Pack> packs) {
        return new ViewState(version, stageIcons, searchText, packs, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState styles(Set<Style> styles) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, styles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState display(List<PackIkon> ikons) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          ikons, favoriteIkons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState favorites(List<PackIkon> ikons) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIkons, ikons, recentIkons, viewMode, status, statusMessage);
    }

    ViewState recent(List<PackIkon> ikons) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, ikons, viewMode, status, statusMessage);
    }

    ViewState mode(ViewMode mode) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, mode, status, statusMessage);
    }

    ViewState signal(ActivityState state) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, state, statusMessage);
    }

    ViewState message(String text) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIkons, favoriteIkons, recentIkons, viewMode, status, text);
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
          "",
          Set.of(firstPack),
          Set.of(), // selectedStyles empty initially
          ikons,
          List.of(), // favoriteIkons empty
          List.of(), // recentIkons empty
          new ViewMode.Grid(),
          new Idle(),
          String.format("%d icons found", ikons.size())
        );
    }
}
