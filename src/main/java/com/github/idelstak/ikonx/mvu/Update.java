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
package com.github.idelstak.ikonx.mvu;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import java.util.*;

public final class Update {

    private final Map<Pack, List<PackIkon>> iconCache;
    private final List<Pack> orderedPacks;

    public Update() {
        iconCache = new EnumMap<>(Pack.class);
        for (var pack : Pack.values()) {
            iconCache.put(
              pack,
              Arrays.stream(pack.getIkons())
                .map(ikon -> new PackIkon(pack, ikon))
                .toList()
            );
        }

        orderedPacks = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Enum::name))
          .toList();
    }

    public ViewState apply(ViewState state, Action action) {
        return switch (action) {
            case Action.SearchChanged a ->
                search(state, a);
            case Action.PackToggled a ->
                toggle(state, a);
            case Action.SelectAllToggled a ->
                toggleAll(state, a);
            case Action.CopyIconRequested a ->
                copyRequested(state, a);
            case Action.CopyIconSucceeded a ->
                copySucceeded(state, a);
            case Action.CopyIconFailed a ->
                copyFailed(state, a);
            case Action.AppVersionRequested _ ->
                versionRequested(state);
            case Action.AppVersionResolved a ->
                versionResolved(state, a);
            case Action.AppVersionFailed a ->
                versionFailed(state, a);
        };
    }

    private ViewState search(ViewState state, Action.SearchChanged action) {
        var icons = filterIcons(state.selectedPacks(), action.query());
        return state
          .search(action.query())
          .display(icons)
          .signal(new ActivityState.Idle())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggle(ViewState state, Action.PackToggled action) {
        var packs = new HashSet<>(state.selectedPacks());
        var pack = action.pack();
        if (action.isSelected()) {
            packs.add(pack);
        } else {
            packs.remove(pack);
        }

        var icons = filterIcons(packs, state.searchText());
        return state
          .select(packs)
          .display(icons)
          .signal(new ActivityState.Idle())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggleAll(ViewState state, Action.SelectAllToggled action) {
        Set<Pack> packs;

        if (action.isSelected()) {
            packs = Set.copyOf(orderedPacks);
        } else {
            packs = Set.of(orderedPacks.getFirst());
        }

        var icons = filterIcons(packs, state.searchText());
        return state
          .select(packs)
          .display(icons)
          .signal(new ActivityState.Idle())
          .message(String.format("%d icons found", icons.size()));
    }

    private List<PackIkon> filterIcons(Set<Pack> selectedPacks, String searchText) {
        if (selectedPacks.isEmpty()) {
            return List.of();
        }

        var icons = selectedPacks.stream()
          .flatMap(pack -> iconCache.get(pack).stream())
          .toList();

        if (searchText == null || searchText.isBlank() || searchText.length() < 2) {
            return icons;
        }

        var lower = searchText.toLowerCase(Locale.ROOT);
        return icons.stream()
          .filter(ikon -> ikon.ikon().getDescription().toLowerCase(Locale.ROOT).contains(lower))
          .toList();
    }

    private ViewState copyRequested(ViewState state, Action.CopyIconRequested action) {
        return state
          .signal(new ActivityState.Idle())
          .message("Copying '" + action.iconCode() + "' to clipboard");
    }

    private ViewState copySucceeded(ViewState state, Action.CopyIconSucceeded action) {
        return state
          .signal(new ActivityState.Success())
          .message("Copied '" + action.iconCode() + "' to clipboard");
    }

    private ViewState copyFailed(ViewState state, Action.CopyIconFailed action) {
        return state
          .signal(new ActivityState.Error())
          .message("Failed to copy '" + action.iconCode() + "' to clipboard: " + action.error().getMessage());
    }

    private ViewState versionRequested(ViewState state) {
        return state.signal(new ActivityState.Idle());
    }

    private ViewState versionResolved(ViewState state, Action.AppVersionResolved action) {
        return state
          .signal(new ActivityState.Success())
          .version(new AppVersion.Ready(action.appVersion(), action.ikonliVersion()));
    }

    private ViewState versionFailed(ViewState state, Action.AppVersionFailed action) {
        var message = action.error().getMessage();
        return state
          .signal(new ActivityState.Error())
          .version(new AppVersion.Failed(message))
          .message(message);
    }
}
