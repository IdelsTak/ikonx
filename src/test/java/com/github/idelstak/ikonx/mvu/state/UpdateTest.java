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
import java.util.stream.*;
import org.junit.jupiter.api.*;
import org.kordamp.ikonli.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

final class UpdateTest {

    @Test
    void searchUpdatesText() {
        var update = new Update();
        var state = ViewState.initial();
        var next = update.apply(state, new Action.SearchChanged("αβ"));

        assertThat(next.searchText(), is("αβ"));
    }

    @Test
    void searchResetsStatus() {
        var update = new Update();
        var state = ViewState.initial()
          .signal(new ActivityState.Success());

        var next = update.apply(state, new Action.SearchChanged("αβ"));

        assertThat(next.status(), instanceOf(ActivityState.Idle.class));
    }

    @Test
    void toggleAddsPack() {
        var update = new Update();
        var state = ViewState.initial();
        var pack = Pack.values()[0];

        var next = update.apply(
          state,
          new Action.PackToggled(pack, true)
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
          new Action.PackToggled(pack, false)
        );

        assertThat(next.selectedPacks(), not(hasItem(pack)));
    }

    @Test
    void selectAllSelectsEveryPack() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.SelectPacksAllToggled(true)
        );

        assertThat(next.selectedPacks(), hasSize(Pack.values().length));
    }

    @Test
    void selectAllLeavesFirstPackSelected() {
        var update = new Update();
        var state = ViewState.initial().select(Set.of(Pack.values()));

        var next = update.apply(
          state,
          new Action.SelectPacksAllToggled(false)
        );

        var ordered = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Enum::name))
          .toList();
        assertThat(next.selectedPacks(), is(Set.of(ordered.getFirst())));
    }

    @Test
    void copySignalsSuccess() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.CopyIconSucceeded("λ")
        );

        assertThat(next.status(), instanceOf(ActivityState.Success.class));
    }

    @Test
    void copyUpdatesMessage() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(
          state,
          new Action.CopyIconRequested("λ")
        );

        assertThat(
          next.statusMessage(),
          is("Copying 'λ' to clipboard")
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

        assertThat(next.displayedIcons(), is(empty()));
    }

    @Test
    void copyFailureSignalsError() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("naïve boom");

        var next = update.apply(
          state,
          new Action.CopyIconFailed("Ω", ex)
        );

        assertThat(next.status(), instanceOf(ActivityState.Error.class));
    }

    @Test
    void copyFailureShowsErrorMessage() {
        var update = new Update();
        var state = ViewState.initial();
        var ex = new RuntimeException("résumé clipboard failed");

        var next = update.apply(
          state,
          new Action.CopyIconFailed("Ω", ex)
        );

        assertThat(next.statusMessage(), containsString("résumé clipboard failed"));
    }

    @Test
    void appVersionRequestSignalsIdle() {
        var update = new Update();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.AppVersionRequested());

        assertThat(next.status(), instanceOf(ActivityState.Idle.class));
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
    void stageIconsRequestSignalsIdle() {
        var update = new Update();
        var state = ViewState.initial()
          .signal(new ActivityState.Success());

        var next = update.apply(state, new Action.StageIconsRequested());

        assertThat(next.status(), instanceOf(ActivityState.Idle.class));
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
    void setsNewModeWhenToggleSelected() {
        var update = new Update();
        var state = ViewState.initial().mode(new ViewMode.List());
        var newMode = new ViewMode.Grid();

        var next = update.apply(state, new Action.ViewModeToggled(newMode, true));

        assertThat(next.viewMode(), equalTo(newMode));
    }

    @Test
    void keepsOldModeWhenToggleDeselectedAndOtherMode() {
        var update = new Update();
        var oldMode = new ViewMode.List();
        var state = ViewState.initial().mode(oldMode);
        var newMode = new ViewMode.Grid();

        var next = update.apply(state, new Action.ViewModeToggled(newMode, false));

        assertThat(next.viewMode(), equalTo(oldMode));
    }

    @Test
    void togglesModeWhenToggleDeselectedAndSameMode() {
        var update = new Update();
        var oldMode = new ViewMode.List();
        var state = ViewState.initial().mode(oldMode);

        var next = update.apply(state, new Action.ViewModeToggled(oldMode, false));

        assertThat(next.viewMode(), equalTo(new ViewMode.Grid()));
    }

    @Test
    void singleStyleToggleSelectsOnlyThatStyle() {
        var update = new Update();
        var style = new Style.Square();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.StyleToggled(style, true));

        var displayedStyles = next.displayedIcons().stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .collect(Collectors.toSet());

        assertThat(displayedStyles, everyItem(isA(Style.Square.class)));
    }

    @Test
    void multipleStylesToggleSelectsAllMatchingIcons() {
        var update = new Update();
        var style1 = new Style.Square();
        var style2 = new Style.Filled();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.SelectPacksAllToggled(true));
        next = update.apply(next, new Action.StyleToggled(style1, true));
        next = update.apply(next, new Action.StyleToggled(style2, true));

        var displayedStyles = next.displayedIcons().stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .map(Style::getClass)
          .collect(Collectors.toSet());

        assertThat(displayedStyles, containsInAnyOrder(style1.getClass(), style2.getClass()));
    }

    @Test
    void togglingOffStyleRemovesItsIcons() {
        var update = new Update();
        var square = new Style.Square();
        var bold = new Style.Bold();
        var state = ViewState.initial();

        var next = update.apply(state, new Action.SelectPacksAllToggled(true));
        next = update.apply(next, new Action.StyleToggled(square, true));
        next = update.apply(next, new Action.StyleToggled(bold, true));
        next = update.apply(next, new Action.StyleToggled(square, false));

        var displayedIcons = next.displayedIcons()
          .stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::style)
          .collect(Collectors.toSet());
        
        assertThat(displayedIcons, everyItem(not(isA(Style.Square.class)))); // no icons of that style remain
    }

    @Test
    void noSelectedStylesReturnsPreviouslyDisplayedIcons() {
        var update = new Update();
        var state = ViewState.initial();
        var displayedIcons = state.displayedIcons();

        var next = update.apply(state, new Action.StyleToggled(new Style.Logo(), false));
        var newDisplayedIconsCount = next.displayedIcons().size();

        assertThat(displayedIcons.size(), is(newDisplayedIconsCount));
    }

    @Test
    void styleToggleRespectsSearchText() {
        var update = new Update();
        var outlined = new Style.Outlined();
        var regular = new Style.Regular();
        var state = ViewState.initial();
        state = update.apply(state, new Action.PackToggled(Pack.values()[Pack.values().length - 6], true));
        state = update.apply(state, new Action.StyleToggled(outlined, true));

        var searchText = "arrow"; // some description fragment that exists in the icons
        state = update.apply(state, new Action.StyleToggled(regular, true));
        state = update.apply(state, new Action.SearchChanged(searchText));
        var filteredIconNames = state.displayedIcons()
          .stream()
          .map(PackIkon::styledIkon)
          .map(StyledIkon::ikon)
          .map(Ikon::getDescription)
          .toList();

        assertThat(filteredIconNames, everyItem(containsStringIgnoringCase(searchText)));
    }
}
