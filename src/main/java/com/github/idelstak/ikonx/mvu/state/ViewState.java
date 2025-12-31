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

    public static ViewState initial() {
        return new ViewState(
          "", // Search text is empty
          Set.of(), // No packs are selected initially
          List.of(), // No icons are displayed
          new Idle(), // Initial status
          "0 icons" // Status message
        );
    }
}
