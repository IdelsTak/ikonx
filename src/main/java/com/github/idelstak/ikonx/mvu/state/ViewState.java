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
package com.github.idelstak.ikonx.mvu.state;

import com.github.idelstak.ikonx.*;
import com.github.idelstak.ikonx.mvu.state.ActivityState.Idle;
import java.util.*;

public record ViewState(
  String searchText,
  Set<Pack> selectedPacks,
  List<PackIkon> displayedIcons,
  ActivityState status,
  String statusMessage) {

    public ViewState {
        selectedPacks = Set.copyOf(selectedPacks);
        displayedIcons = List.copyOf(displayedIcons);
    }

    public ViewState search(String text) {
        return new ViewState(text, selectedPacks, displayedIcons, status, statusMessage);
    }

    public ViewState select(Set<Pack> packs) {
        return new ViewState(searchText, packs, displayedIcons, status, statusMessage);
    }

    public ViewState display(List<PackIkon> icons) {
        return new ViewState(searchText, selectedPacks, icons, status, statusMessage);
    }

    public ViewState signal(ActivityState state) {
        return new ViewState(searchText, selectedPacks, displayedIcons, state, statusMessage);
    }

    public ViewState message(String text) {
        return new ViewState(searchText, selectedPacks, displayedIcons, status, text);
    }

    public static ViewState initial() {
        var first = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Enum::name))
          .findFirst()
          .orElseThrow();

        var icons = Arrays.stream(first.getIkons())
          .map(ikon -> new PackIkon(first, ikon))
          .toList();

        return new ViewState(
          "",
          Set.of(first),
          icons,
          new Idle(),
          String.format("%d icons found", icons.size())
        );
    }
}
