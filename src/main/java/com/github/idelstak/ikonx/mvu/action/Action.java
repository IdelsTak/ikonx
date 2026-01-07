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
package com.github.idelstak.ikonx.mvu.action;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;
import javafx.scene.image.*;

public sealed interface Action {

    record SearchChanged(String query) implements Action {

    }

    record PackToggled(Pack pack, boolean isSelected) implements Action {

    }

    record SelectPacksAllToggled(boolean isSelected) implements Action {

    }

    record PackStyleToggled(Style style, boolean isSelected) implements Action {

    }

    record FavoriteIkonToggled(PackIkon ikon, boolean isSelected) implements Action {

    }

    record CopyIconRequested(String iconCode) implements Action {

    }

    record CopyIconSucceeded(String iconCode) implements Action {

    }

    record CopyIconFailed(String iconCode, Throwable error) implements Action {

    }

    record AppVersionRequested() implements Action {
    }

    record AppVersionResolved(String appVersion, String ikonliVersion) implements Action {

    }

    record AppVersionFailed(Throwable error) implements Action {

    }

    record StageIconsRequested() implements Action {
    }

    record StageIconsResolved(List<Image> icons) implements Action {

    }

    record StageIconsFailed(Throwable error) implements Action {

    }

    record ViewModeToggled(ViewMode mode, boolean isSelected) implements Action {

    }
}
