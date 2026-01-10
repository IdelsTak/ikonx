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
import com.github.idelstak.ikonx.mvu.state.search.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.mvu.state.view.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;
import java.util.stream.*;

public final class Update {

    private final int minSearchLength;

    public Update() {
        minSearchLength = 2;
    }

    public ViewState apply(ViewState state, Action action) {
        return switch (action) {
            case Action.SearchChanged a ->
                search(state, a);
            case Action.SearchCleared _ ->
                clearSearch(state);
            case Action.FilterPacksRequested _ ->
                filterPacksRequested(state);
            case Action.FilterPacksSucceeded _ ->
                filterPacksSucceeded(state);
            case Action.FilterPacksFailed a ->
                filterPacksFailed(state, a);
            case Action.PackToggled a ->
                togglePack(state, a);
            case Action.SelectAllPacksToggled _ ->
                toggleAllPacks(state);
            case Action.PackStyleToggled a ->
                toggleStyle(state, a);
            case Action.SelectAllPackStylesToggled _ ->
                toggleAllStyles(state);
            case Action.FavoriteIkonToggled a ->
                toggleFavorite(state, a);
            case Action.ViewIkonDetailsRequested a ->
                showIkonDetails(state, a);
            case Action.HideIkonDetailsRequested _ ->
                hideIkonDetails(state);
            case Action.ViewIkonDetailsFailed a ->
                viewIkonDetailsFailed(state, a);
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
            case Action.ViewModeToggled _ ->
                toggleViewMode(state);
        };
    }

    private ViewState search(ViewState state, Action.SearchChanged action) {
        var query = action.query();
        var icons = filterIconsByPack(state.ikonCatalog(), state.selectedPacks(), query);
        return state
          .search(new IkonQuery.Searching(query))
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState clearSearch(ViewState state) {
        var query = "";
        var icons = filterIconsByPack(state.ikonCatalog(), state.selectedPacks(), query);
        return state
          .search(new IkonQuery.Clear())
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState filterPacksRequested(ViewState state) {
        var newFilter = state.filter() instanceof PacksFilter.Show
                      ? new PacksFilter.Hidden()
                      : new PacksFilter.Show();
        return state
          .filter(newFilter)
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
        var pack = action.pack();
        var packs = new HashSet<>(state.selectedPacks());

        if (!packs.remove(pack)) {
            packs.add(pack);
        }

        var catalog = state.ikonCatalog();

        var styles = packs.stream()
          .flatMap(p -> catalog.byPack(p).stream())
          .map(ikon -> ikon.styledIkon().style())
          .filter(style -> !(style instanceof Style.All))
          .collect(Collectors.toSet());

        if (styles.size() == catalog.orderedStyles().size() - 1) {
            styles = Set.of(new Style.All());
        }

        var searchText = switch (state.query()) {
            case IkonQuery.Searching s ->
                s.searchText();
            case IkonQuery.Clear _ ->
                "";
        };

        var icons = filterIconsByPack(state.ikonCatalog(), packs, searchText);
        return state
          .select(packs)
          .styles(styles)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(icons.size() + " icons found");
    }

    private ViewState toggleAllPacks(ViewState state) {
        Set<Pack> packs;
        var catalog = state.ikonCatalog();

        if (state.selectedPacks().size() != catalog.orderedPacks().size()) {
            packs = Set.copyOf(catalog.orderedPacks());
        } else {
            packs = Set.of(catalog.orderedPacks().getFirst());
        }

        var styles = packs.stream()
          .flatMap(p -> catalog.byPack(p).stream())
          .map(ikon -> ikon.styledIkon().style())
          .filter(style -> !(style instanceof Style.All))
          .collect(Collectors.toSet());

        if (styles.size() == catalog.orderedStyles().size() - 1) {
            styles = Set.of(new Style.All());
        }

        var searchText = switch (state.query()) {
            case IkonQuery.Searching s ->
                s.searchText();
            case IkonQuery.Clear _ ->
                "";
        };

        var icons = filterIconsByPack(state.ikonCatalog(), packs, searchText);
        return state
          .select(packs)
          .styles(styles)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(String.format("%d icons found", icons.size()));
    }

    private ViewState toggleStyle(ViewState state, Action.PackStyleToggled action) {
        var style = action.style();
        var styles = state.selectedStyles();
        var toggled = styles.contains(style)
                    ? styles.stream().filter(item -> !item.equals(style))
                    : Stream.concat(styles.stream(), Stream.of(style));

        var normalized = toggled
          .filter(item -> !(item instanceof Style.All))
          .collect(Collectors.toSet());

        var catalog = state.ikonCatalog();

        if (normalized.isEmpty() || normalized.size() == catalog.orderedStyles().size() - 1) {
            normalized = Set.of(new Style.All());
        }

        var searchText = switch (state.query()) {
            case IkonQuery.Searching s ->
                s.searchText();
            case IkonQuery.Clear _ ->
                "";
        };

        var icons = filterIconsByStyle(catalog, state.selectedPacks(), normalized, searchText);
        return state
          .styles(normalized)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(icons.size() + " icons found");
    }

    private ViewState toggleAllStyles(ViewState state) {
        var packs = state.selectedPacks();
        var styles = state.selectedStyles();
        var all = styles.stream().anyMatch(Style.All.class::isInstance);
        var catalog = state.ikonCatalog();

        var toggled = all
                    ? packs.stream()
            .flatMap(pack -> catalog.byPack(pack).stream())
            .map(ikon -> ikon.styledIkon().style())
            .collect(Collectors.toSet())
                    : Set.<Style>of(new Style.All());

        if (toggled.size() == catalog.orderedStyles().size() - 1) {
            toggled = Set.of(new Style.All());
        }

        var searchText = switch (state.query()) {
            case IkonQuery.Searching s ->
                s.searchText();
            case IkonQuery.Clear _ ->
                "";
        };

        var icons = filterIconsByStyle(catalog, packs, toggled, searchText);
        return state
          .styles(toggled)
          .display(icons)
          .signal(new ActivityState.Success())
          .message(icons.size() + " icons found");
    }

    private ViewState toggleFavorite(ViewState state, Action.FavoriteIkonToggled action) {
        var favorites = new HashSet<>(state.favoriteIkons());
        var ikon = action.ikon();
        boolean added = favorites.add(ikon);

        if (!added) {
            favorites.remove(ikon);
        }

        var desc = ikon.description();
        return state
          .favorites(favorites)
          .signal(new ActivityState.Success())
          .message("%s %s favorites".formatted(desc, added ? "added to" : "removed from"));
    }

    private List<PackIkon> filterIconsByStyle(
      IkonCatalog catalog,
      Set<Pack> selectedPacks,
      Set<Style> selectedStyles,
      String searchText) {
        List<PackIkon> icons = selectedStyles.stream().anyMatch(Style.All.class::isInstance)
                                 ? selectedPacks.stream()
            .flatMap(pack -> catalog.byPack(pack).stream())
            .toList()
                                 : selectedStyles.stream()
            .flatMap(style -> {
                return catalog.byStyle(style)
                  .stream()
                  .filter(packIkon -> selectedPacks.contains(packIkon.pack()));
            })
            .toList();

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

    private List<PackIkon> filterIconsByPack(IkonCatalog catalog, Set<Pack> selectedPacks, String searchText) {
        if (selectedPacks.isEmpty()) {
            return List.of();
        }

        var icons = selectedPacks.stream()
          .flatMap(pack -> catalog.byPack(pack).stream())
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
        var ikon = action.ikon();
        var recents = new HashSet<>(state.recentIkons());
        boolean changed = recents.add(ikon);

        var next = changed ? state.recent(recents) : state;

        return next
          .signal(new ActivityState.Success())
          .message("Copied '%s' to clipboard".formatted(ikon.description()));
    }

    private ViewState copyFailed(ViewState state, Action.CopyIkonFailed action) {
        return state
          .signal(new ActivityState.Error())
          .message("Failed to copy '"
            + action.ikon().description()
            + "' to clipboard: "
            + action.error().getMessage());
    }

    private ViewState showIkonDetails(ViewState state, Action.ViewIkonDetailsRequested action) {
        return state
          .show(new IkonDetailsDisplay.ShowRequested(action.ikon()))
          .signal(new ActivityState.Loading())
          .message("View '" + action.ikon().description() + "' details");
    }

    private ViewState hideIkonDetails(ViewState state) {
        return state
          .show(new IkonDetailsDisplay.HideRequested())
          .signal(new ActivityState.Success())
          .message("Viewed icon details");
    }

    private ViewState viewIkonDetailsFailed(ViewState state, Action.ViewIkonDetailsFailed action) {
        return state
          .show(new IkonDetailsDisplay.Failed(action.error()))
          .signal(new ActivityState.Error())
          .message("Failed to view icon details: " + action.error().getMessage());
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

    private ViewState toggleViewMode(ViewState state) {
        var oldMode = state.viewMode();
        var newMode = oldMode instanceof ViewMode.Grid ? new ViewMode.List() : new ViewMode.Grid();
        return state
          .signal(new ActivityState.Success())
          .mode(newMode)
          .message("Switched icon browser view to " + newMode.displayName().toLowerCase(Locale.ROOT));
    }
}
