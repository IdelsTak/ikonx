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
import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.icons.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;

public final class Update {

    private final int minSearchLength;
    private final Ikons ikons;

    public Update() {
        ikons = new Ikons(Pack.values());
        minSearchLength = 2;
    }

    public ViewState apply(ViewState state, Action action) {
        return switch (action) {
            case Action.SearchChanged a ->
                search(state, a);
            case Action.FilterPacksRequested _ ->
                filterPacksRequested(state);
            case Action.FilterPacksSucceeded _ ->
                filterPacksSucceeded(state);
            case Action.FilterPacksFailed a ->
                filterPacksFailed(state, a);
            case Action.PackToggled a ->
                togglePack(state, a);
            case Action.SelectAllPacksToggled a ->
                toggleAllPacks(state, a);
            case Action.PackStyleToggled a ->
                toggleStyle(state, a);
            case Action.SelectAllPackStylesToggled a ->
                toggleAllStyles(state, a);
            case Action.FavoriteIkonToggled a ->
                toggleFavorite(state, a);
            case Action.ViewIkonRequested a ->
                viewRequested(state, a);
            case Action.ViewIkonSucceeded a ->
                viewSucceeded(state, a);
            case Action.ViewIkonFailed a ->
                viewFailed(state, a);
            case Action.CopyIkonRequested a ->
                copyRequested(state, a);
            case Action.CopyIkonSucceeded a ->
                copySucceeded(state, a);
            case Action.CopyIkonFailed a ->
                copyFailed(state, a);
            case Action.AppVersionRequested _ ->
                versionRequested(state);
            case Action.AppVersionResolved a ->
                versionResolved(state, a);
            case Action.AppVersionFailed a ->
                versionFailed(state, a);
            case Action.StageIconsRequested _ ->
                stageIconsRequested(state);
            case Action.StageIconsResolved a ->
                stageIconsResolved(state, a);
            case Action.StageIconsFailed a ->
                stageIconsFailed(state, a);
            case Action.ViewModeToggled a ->
                toggleViewMode(state, a);
        };
    }

    private ViewState search(ViewState state, Action.SearchChanged action) {
        var icons = filterIconsByPack(state.selectedPacks(), action.query());
        return state
          .search(action.query())
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState filterPacksRequested(ViewState state) {
        return state
          .signal(new ActivityState.Loading())
          .message("Filtering icons");
    }

    private ViewState filterPacksSucceeded(ViewState state) {
        return state
          .signal(new ActivityState.Success())
          .message("Filtered icons. %d shown.".formatted(state.displayedIkons().size()));
    }

    private ViewState filterPacksFailed(ViewState state, Action.FilterPacksFailed a) {
        return state
          .signal(new ActivityState.Error())
          .message("Failed to filter icons: ".formatted(a.error().getMessage()));
    }

    private ViewState togglePack(ViewState state, Action.PackToggled action) {
        var packs = new HashSet<>(state.selectedPacks());
        var pack = action.pack();
        boolean changed;

        if (action.isSelected()) {
            changed = packs.add(pack);
        } else {
            changed = packs.remove(pack);
        }

        if (!changed) {
            return state;
        }

        var icons = filterIconsByPack(packs, state.searchText());
        return state
          .select(packs)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggleAllPacks(ViewState state, Action.SelectAllPacksToggled action) {
        Set<Pack> packs;

        if (action.isSelected()) {
            packs = Set.copyOf(ikons.orderedPacks());
        } else {
            packs = Set.of(ikons.orderedPacks().getFirst());
        }

        var icons = filterIconsByPack(packs, state.searchText());
        return state
          .select(packs)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggleStyle(ViewState state, Action.PackStyleToggled action) {
        Set<Style> styles = new HashSet<>(state.selectedStyles());
        var style = action.style();
        boolean changed;

        if (action.isSelected() && !(action.style() instanceof Style.All)) {
            changed = styles.add(style);
        } else {
            changed = styles.remove(style);
        }

        if (!changed) {
            return state;
        }

        if (styles.isEmpty() || styles.size() == ikons.orderedStyles().size()) {
            styles = Set.of(new Style.All());
        }

        var icons = filterIconsByStyle(styles, state.searchText());
        return state
          .styles(styles)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggleAllStyles(ViewState state, Action.SelectAllPackStylesToggled action) {
        Set<Style> styles;

        if (action.isSelected()) {
            styles = Set.of(new Style.All());
        } else {
            styles = Set.copyOf(state.selectedStyles());
        }

        var icons = filterIconsByStyle(styles, state.searchText());
        return state
          .styles(styles)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggleFavorite(ViewState state, Action.FavoriteIkonToggled action) {
        var favorites = new HashSet<>(state.favoriteIkons());
        var ikon = action.ikon();
        boolean addToFavorites = action.isSelected();
        boolean changed;

        if (addToFavorites) {
            changed = favorites.add(ikon);
        } else {
            changed = favorites.remove(ikon);
        }

        if (!changed) {
            return state;
        }

        var desc = ikon.description();
        return state
          .favorites(List.copyOf(favorites))
          .signal(new ActivityState.Success())
          .message("%s %s favorites".formatted(desc, addToFavorites ? "added to" : "removed from"));
    }

    private List<PackIkon> filterIconsByStyle(Set<Style> selectedStyles, String searchText) {
        var icons = selectedStyles.stream().anyMatch(Style.All.class::isInstance)
                  ? ikons.all()
                  : selectedStyles.stream().flatMap(style -> ikons.byStyle(style).stream()).toList();

        if (isInvalidSearch(searchText)) {
            return icons;
        }

        return applySearchFilter(searchText, icons);
    }

    private boolean isInvalidSearch(String searchText) {
        return searchText == null || searchText.isBlank() || searchText.length() < minSearchLength;
    }

    private List<PackIkon> applySearchFilter(String searchText, List<PackIkon> icons) {
        var lower = searchText.toLowerCase(Locale.ROOT);
        return icons.stream()
          .filter(pi ->
          {
              return pi.description().toLowerCase(Locale.ROOT).contains(lower);
          })
          .toList();
    }

    private List<PackIkon> filterIconsByPack(Set<Pack> selectedPacks, String searchText) {
        if (selectedPacks.isEmpty()) {
            return List.of();
        }

        var icons = selectedPacks.stream()
          .flatMap(pack -> ikons.byPack(pack).stream())
          .toList();

        if (isInvalidSearch(searchText)) {
            return icons;
        }

        return applySearchFilter(searchText, icons);
    }

    private ViewState copyRequested(ViewState state, Action.CopyIkonRequested action) {
        return state
          .signal(new ActivityState.Loading())
          .message("Copying '" + action.ikon().description() + "' to clipboard");
    }

    private ViewState copySucceeded(ViewState state, Action.CopyIkonSucceeded action) {
        var recents = new HashSet<>(state.recentIkons());
        var ikon = action.ikon();
        var changed = recents.add(ikon);

        if (!changed) {
            return state
              .signal(new ActivityState.Success())
              .message("Copied '" + action.ikon().description() + "' to clipboard");
        }

        return state
          .recent(List.copyOf(recents))
          .signal(new ActivityState.Success())
          .message("Copied '" + action.ikon().description() + "' to clipboard");
    }

    private ViewState copyFailed(ViewState state, Action.CopyIkonFailed action) {
        return state
          .signal(new ActivityState.Error())
          .message("Failed to copy '"
            + action.ikon().description()
            + "' to clipboard: "
            + action.error().getMessage());
    }

    private ViewState viewRequested(ViewState state, Action.ViewIkonRequested action) {
        return state
          .signal(new ActivityState.Loading())
          .message("View '" + action.ikon().description() + "' details");
    }

    private ViewState viewSucceeded(ViewState state, Action.ViewIkonSucceeded action) {
        return state
          .signal(new ActivityState.Success())
          .message("Viewed '" + action.ikon().description() + "' details");
    }

    private ViewState viewFailed(ViewState state, Action.ViewIkonFailed action) {
        return state
          .signal(new ActivityState.Error())
          .message("Failed to view '"
            + action.ikon().description()
            + "' details: " + action.error().getMessage());
    }

    private ViewState versionRequested(ViewState state) {
        return state.signal(new ActivityState.Loading());
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

    private ViewState stageIconsRequested(ViewState state) {
        return state.signal(new ActivityState.Loading());
    }

    private ViewState stageIconsResolved(ViewState state, Action.StageIconsResolved action) {
        return state
          .signal(new ActivityState.Success())
          .stageIcons(new StageIcons.Ready(action.icons()));
    }

    private ViewState stageIconsFailed(ViewState state, Action.StageIconsFailed action) {
        var message = action.error().getMessage();
        return state
          .signal(new ActivityState.Error())
          .stageIcons(new StageIcons.Failed(message))
          .message(message);
    }

    private ViewState toggleViewMode(ViewState state, Action.ViewModeToggled action) {
        var oldMode = state.viewMode();
        var newMode = computeNewMode(oldMode, action);
        return state
          .signal(new ActivityState.Success())
          .mode(newMode)
          .message("Switched icon browser view to " + newMode.displayName().toLowerCase(Locale.ROOT));
    }

    private ViewMode computeNewMode(ViewMode oldMode, Action.ViewModeToggled action) {
        if (action.isSelected()) {
            return action.mode();
        }
        if (oldMode.equals(action.mode())) {
            return oldMode instanceof ViewMode.Grid ? new ViewMode.List() : new ViewMode.Grid();
        }
        return oldMode;
    }
}
