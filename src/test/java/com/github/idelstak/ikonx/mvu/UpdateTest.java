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
import java.util.*;
import org.junit.jupiter.api.*;
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
          new Action.SelectAllToggled(true)
        );

        assertThat(next.selectedPacks(), hasSize(Pack.values().length));
    }

    @Test
    void selectAllLeavesFirstPackSelected() {
        var update = new Update();
        var state = ViewState.initial().select(Set.of(Pack.values()));

        var next = update.apply(
          state,
          new Action.SelectAllToggled(false)
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
}
