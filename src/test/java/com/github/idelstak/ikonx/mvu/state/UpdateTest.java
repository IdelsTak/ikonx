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
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.kordamp.ikonli.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

final class UpdateTest {

    @Test
    void shortSearchDoesNotFilterIcons() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        var all = state.displayedIkons();
        var next = update.apply(state, new Action.SearchChanged("α"));

        assertThat(next.displayedIkons(), is(all));
    }

    @Test
    void validSearchFilters() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        var next = update.apply(state, new Action.SearchChanged("arrow"));

        assertThat(
          next.displayedIkons()
            .stream()
            .map(PackIkon::description)
            .toList(),
          everyItem(containsStringIgnoringCase("arrow"))
        );
    }

    @Test
    void validSearchShowsCountMessage() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        var next = update.apply(state, new Action.SearchChanged("arrow"));

        assertThat(
          next.statusMessage(),
          is(next.displayedIkons().size() + " icons found")
        );
    }

    @Test
    void searchRespectsSelectedPacks() {
        var update = new Update();
        var pack = Pack.values()[0];

        var state = ViewState.initial().select(Set.of(pack));

        var next = update.apply(state, new Action.SearchChanged("arrow"));

        assertThat(
          next.displayedIkons()
            .stream()
            .map(PackIkon::pack)
            .collect(Collectors.toSet()),
          is(Set.of(pack))
        );
    }

    @Test
    void clearSearchResetsQueryState() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SearchChanged("αβ"));

        var next = update.apply(state, new Action.SearchCleared());

        assertThat(next.query(), instanceOf(IkonQuery.Clear.class));
    }

    @Test
    void clearSearchRestoresUnfilteredIcons() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        var full = state.displayedIkons();

        state = update.apply(state, new Action.SearchChanged("arrow"));
        var next = update.apply(state, new Action.SearchCleared());

        assertThat(next.displayedIkons(), is(full));
    }

    @Test
    void clearSearchSignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        var next = update.apply(state, new Action.SearchCleared());

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void clearSearchShowsCount() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        var next = update.apply(state, new Action.SearchCleared());

        assertThat(
          next.statusMessage(),
          is(next.displayedIkons().size() + " icons found")
        );
    }

    @Test
    void toggleAddsPack() {
        var update = new Update();
        var state = ViewState.initial();
        var pack = Pack.values()[0];

        var next = update.apply(
          state,
          new Action.PackToggled(pack)
        );

        assertThat(next.selectedPacks(), hasItem(pack));
    }

    @Test
    void toggleRemovesPack() {
        var update = new Update();
        var pack = Pack.values()[0];

        var state = ViewState.initial().select(Set.of(pack));

        var next = update.apply(
          state,
          new Action.PackToggled(pack)
        );

        assertThat(next.selectedPacks(), not(hasItem(pack)));
    }

    @Test
    void selectAllSelectsEveryPack() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.SelectAllPacksToggled()
        );

        assertThat(next.selectedPacks(), hasSize(Pack.values().length));
    }

    @Test
    void selectAllCollapsesToSingleFirstPackWhenAllAlreadySelected() {
        var update = new Update();
        var state = ViewState.initial().select(Set.of(Pack.values()));

        var next = update.apply(
          state,
          new Action.SelectAllPacksToggled()
        );

        var ordered = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Enum::name))
          .toList();
        assertThat(next.selectedPacks(), is(Set.of(ordered.getFirst())));
    }

    @Test
    void togglingPackRecomputesStylesFromSelectedPacks() {
        var update = new Update();
        var pack = Pack.values()[0];
        var state = ViewState.initial();

        state = update.apply(state, new Action.SelectAllPackStylesToggled());
        var next = update.apply(state, new Action.PackToggled(pack));

        assertThat(
          next.selectedStyles().stream().anyMatch(Style.All.class::isInstance),
          is(false)
        );
    }

    @Test
    void removingLastPackDisplaysNoIcons() {
        var update = new Update();
        var pack = Pack.values()[0];
        var state = ViewState.initial().select(Set.of(pack));

        var next = update.apply(state, new Action.PackToggled(pack));

        assertThat(next.displayedIkons(), is(empty()));
    }

    @Test
    void toggleAllPacksDerivesStylesFromAllPacks() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.SelectAllPacksToggled());

        assertThat(
          next.selectedStyles(),
          not(empty())
        );
    }

    @Test
    void toggleAllPacksRespectsSearchText() {
        var update = new Update();
        var state = ViewState.initial();
        var query = "arrow";

        state = update.apply(state, new Action.SearchChanged(query));
        var next = update.apply(state, new Action.SelectAllPacksToggled());

        assertThat(
          next.displayedIkons()
            .stream()
            .map(PackIkon::description)
            .toList(),
          everyItem(containsStringIgnoringCase(query))
        );
    }

    @Test
    void emptySelectionDisplaysNoIcons() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.SearchChanged("αβ")
        );

        assertThat(next.displayedIkons(), is(empty()));
    }

    @Test
    void copyUpdatesMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(state,
          new Action.CopyIkonRequested(ikon)
        );

        assertThat(
          next.statusMessage(),
          is("Copying '%s' to clipboard".formatted(ikon.description()))
        );
    }

    @Test
    void copySucceededSignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(
          state,
          new Action.CopyIkonSucceeded(ikon)
        );

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void copySucceededAddsToRecentIcons() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(
          state,
          new Action.CopyIkonSucceeded(ikon)
        );

        assertThat(next.recentIkons(), hasItem(ikon));
    }

    @Test
    void keepsRecentsWhenIconAlreadyPresent() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var action = new Action.CopyIkonSucceeded(ikon);
        state = update.apply(state, action);
        var next = update.apply(state, action);

        assertThat(
          state.recentIkons(),
          equalTo(next.recentIkons())
        );
    }

    @Test
    void copySucceededShowsMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(
          state,
          new Action.CopyIkonSucceeded(ikon)
        );

        assertThat(
          next.statusMessage(),
          is("Copied '" + ikon.description() + "' to clipboard")
        );
    }

    @Test
    void copyFailureSignalsError() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();
        var ex = new RuntimeException("naïve boom");

        var next = update.apply(
          state,
          new Action.CopyIkonFailed(ikon, ex)
        );

        assertThat(next.status(), instanceOf(ActivityState.Error.class));
    }

    @Test
    void copyFailureShowsErrorMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();
        var ex = new RuntimeException("résumé clipboard failed");

        var next = update.apply(
          state,
          new Action.CopyIkonFailed(ikon, ex)
        );

        assertThat(next.statusMessage(), containsString("résumé clipboard failed"));
    }

    @Test
    void appVersionRequestSignalsLoading() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.AppVersionRequested());

        assertThat(next.status(), instanceOf(ActivityState.Loading.class));
    }

    @Test
    void appVersionResolvedSignalsSuccessAndSetsVersion() {
        var update = new Update();
        var state = ViewState.initial();
        var next = update.apply(
          state,
          new Action.AppVersionResolved("1.2.3", "12.4.0")
        );

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
        assertThat(next.version(), instanceOf(AppVersion.Ready.class));
        var v = (AppVersion.Ready) next.version();
        assertThat(v.appValue(), is("1.2.3"));
        assertThat(v.ikonliValue(), is("12.4.0"));
    }

    @Test
    void appVersionFailedSignalsErrorAndSetsFailedVersion() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new IllegalStateException("cliché cannot read version");

        var next = update.apply(state, new Action.AppVersionFailed(ex));

        assertThat(next.status(), instanceOf(ActivityState.Error.class));
        assertThat(next.version(), instanceOf(AppVersion.Failed.class));
        var v = (AppVersion.Failed) next.version();
        assertThat(v.reason(), containsString("cliché cannot read version"));
    }

    @Test
    void stageIconsRequestSignalsLoading() {
        var update = new Update();
        var state = ViewState.initial()
          .signal(new ActivityState.Success());

        var next = update.apply(state, new Action.StageIconsRequested());

        assertThat(next.status(), instanceOf(ActivityState.Loading.class));
    }

    @Test
    void stageIconsResolvedSignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.StageIconsResolved(List.of())
        );

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void stageIconsResolvedSetsReadyStageIcons() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.StageIconsResolved(List.of())
        );

        assertThat(next.stageIcons(), instanceOf(StageIcons.Ready.class));
    }

    @Test
    void stageIconsFailedSignalsError() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("über stage icon failure");

        var next = update.apply(
          state,
          new Action.StageIconsFailed(ex)
        );

        assertThat(next.status(), instanceOf(ActivityState.Error.class));
    }

    @Test
    void stageIconsFailedSetsFailedStageIcons() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("façade icon load error");

        var next = update.apply(
          state,
          new Action.StageIconsFailed(ex)
        );

        assertThat(next.stageIcons(), instanceOf(StageIcons.Failed.class));
    }

    @Test
    void stageIconsFailedShowsErrorMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("crème brûlée icons broken");

        var next = update.apply(
          state,
          new Action.StageIconsFailed(ex)
        );

        assertThat(
          next.statusMessage(),
          containsString("crème brûlée icons broken")
        );
    }

    @Test
    void toggleViewModeFromGridSwitchesToList() {
        var update = new Update();
        var state = ViewState.initial().mode(new ViewMode.Grid());

        var next = update.apply(state, new Action.ViewModeToggled());

        assertThat(next.viewMode(), instanceOf(ViewMode.List.class));
    }

    @Test
    void toggleViewModeFromListSwitchesToGrid() {
        var update = new Update();
        var state = ViewState.initial().mode(new ViewMode.List());

        var next = update.apply(state, new Action.ViewModeToggled());

        assertThat(next.viewMode(), instanceOf(ViewMode.Grid.class));
    }

    @Test
    void toggleViewModeAlwaysSignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial().mode(new ViewMode.List());

        var next = update.apply(state, new Action.ViewModeToggled());

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void singleStyleToggleSelectsOnlyThatStyle() {
        var update = new Update();
        var style = new Style.Square();
        var state = ViewState.initial();

        state = update.apply(state, new Action.SelectAllPacksToggled());
        var next = update.apply(state, new Action.PackStyleToggled(style));

        var styles = next.displayedIkons().stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .toList();

        assertThat(styles, everyItem(isA(Style.Square.class)));
    }

    @Test
    void multipleStylesProduceUnionOfIcons() {
        var update = new Update();
        var square = new Style.Square();
        var filled = new Style.Filled();

        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());
        state = update.apply(state, new Action.PackStyleToggled(square));
        var next = update.apply(state, new Action.PackStyleToggled(filled));

        var styles = next.displayedIkons().stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .map(Object::getClass)
          .collect(Collectors.toSet());

        assertThat(styles, containsInAnyOrder(
          Style.Square.class,
          Style.Filled.class
        ));
    }

    @Test
    void removingLastStyleRestoresAllStyles() {
        var update = new Update();
        var square = new Style.Square();

        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());
        state = update.apply(state, new Action.PackStyleToggled(square));
        var next = update.apply(state, new Action.PackStyleToggled(square));

        assertThat(next.selectedStyles(), hasSize(1));
        assertThat(next.selectedStyles().iterator().next(), isA(Style.All.class));
    }

    @Test
    void togglingOffStyleRemovesItsIcons() {
        var update = new Update();
        var square = new Style.Square();
        var bold = new Style.Bold();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.SelectAllPacksToggled());
        next = update.apply(next, new Action.PackStyleToggled(square));
        next = update.apply(next, new Action.PackStyleToggled(bold));
        next = update.apply(next, new Action.PackStyleToggled(square));

        var displayedIcons = next.displayedIkons()
          .stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .collect(Collectors.toSet());

        assertThat(displayedIcons, everyItem(not(isA(Style.Square.class))));
    }

    @Test
    void toggleAllStylesExpandsAllIntoConcreteStyles() {
        var update = new Update();
        var state = ViewState.initial();
        var next = update.apply(state, new Action.SelectAllPackStylesToggled());

        assertThat(
          next.selectedStyles().stream().anyMatch(Style.All.class::isInstance),
          is(false)
        );
    }

    @Test
    void selectingEveryConcreteStyleCollapsesToAll() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPacksToggled());

        for (var style : orderedStyles(state.ikonCatalog())) {
            state = update.apply(state, new Action.PackStyleToggled(style));
        }

        assertThat(state.selectedStyles(), allOf(hasSize(1), everyItem(isA(Style.All.class))));
    }

    @Test
    void togglingConcreteStyleRemovesAll() {
        var update = new Update();
        var state = ViewState.initial();
        state = update.apply(state, new Action.SelectAllPackStylesToggled());

        var next = update.apply(state, new Action.PackStyleToggled(new Style.Bold()));

        assertThat(
          next.selectedStyles().stream().anyMatch(Style.All.class::isInstance),
          is(false)
        );
    }

    @Test
    void toggleAllStylesOnlyIncludesStylesFromSelectedPacks() {
        var update = new Update();
        var packs = Set.of(Pack.values()[0]);
        var state = ViewState.initial().select(packs);

        var next = update.apply(state, new Action.SelectAllPackStylesToggled());

        var styles = next.displayedIkons()
          .stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .collect(Collectors.toSet());

        assertThat(styles, not(empty()));
    }

    @Test
    void styleToggleRespectsSearchText() {
        var update = new Update();
        var outlined = new Style.Outlined();
        var regular = new Style.Regular();
        var state = ViewState.initial();
        state = update.apply(state, new Action.PackToggled(Pack.values()[Pack.values().length - 6]));
        state = update.apply(state, new Action.PackStyleToggled(outlined));

        var searchText = "arrow"; // some description fragment that exists in the icons
        state = update.apply(state, new Action.PackStyleToggled(regular));
        state = update.apply(state, new Action.SearchChanged(searchText));
        var filteredIconNames = state.displayedIkons()
          .stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::ikon)
          .map(Ikon::getDescription)
          .toList();

        assertThat(filteredIconNames, everyItem(containsStringIgnoringCase(searchText)));
    }

    @Test
    void favoriteToggleAddsIkon() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(
          state,
          new Action.FavoriteIkonToggled(ikon)
        );

        assertThat(next.favoriteIkons(), hasItem(ikon));
    }

    @Test
    void favoriteToggleRemovesIkon() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();
        state = update.apply(state, new Action.FavoriteIkonToggled(ikon));

        var next = update.apply(
          state,
          new Action.FavoriteIkonToggled(ikon)
        );

        assertThat(next.favoriteIkons(), not(hasItem(ikon)));
    }

    @Test
    void favoriteToggleSignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(
          state,
          new Action.FavoriteIkonToggled(ikon)
        );

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void favoriteToggleShowsAddedMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();
        var desc = ikon.description();

        var next = update.apply(
          state,
          new Action.FavoriteIkonToggled(ikon)
        );

        assertThat(
          next.statusMessage(),
          is(desc + " added to favorites")
        );
    }

    @Test
    void favoriteToggleShowsRemovedMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();
        state = update.apply(state, new Action.FavoriteIkonToggled(ikon));
        var desc = ikon.description();

        var next = update.apply(
          state,
          new Action.FavoriteIkonToggled(ikon)
        );

        assertThat(
          next.statusMessage(),
          is(desc + " removed from favorites")
        );
    }

    @Test
    void viewRequestedSignalsLoading() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();

        var next = update.apply(
          state,
          new Action.ViewIkonDetailsRequested(ikon)
        );

        assertThat(next.status(), instanceOf(ActivityState.Loading.class));
    }

    @Test
    void viewRequestedShowsMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ikon = state.displayedIkons().getFirst();
        var desc = ikon.description();

        var next = update.apply(
          state,
          new Action.ViewIkonDetailsRequested(ikon)
        );

        assertThat(
          next.statusMessage(),
          is("View '" + desc + "' details")
        );
    }

    @Test
    void viewSucceededSignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.HideIkonDetailsRequested()
        );

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void viewFailedSignalsError() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("über view failure");

        var next = update.apply(
          state,
          new Action.ViewIkonDetailsFailed(ex)
        );

        assertThat(next.status(), instanceOf(ActivityState.Error.class));
    }

    @Test
    void viewFailedShowsErrorMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("façade view exploded");

        var next = update.apply(
          state,
          new Action.ViewIkonDetailsFailed(ex)
        );

        assertThat(
          next.statusMessage(),
          containsString("façade view exploded")
        );
    }

    private Set<Style> orderedStyles(IkonCatalog catalog) {
        return catalog
          .orderedStyles()
          .stream()
          .filter(style -> !(style instanceof Style.All))
          .collect(Collectors.toSet());
    }
}
