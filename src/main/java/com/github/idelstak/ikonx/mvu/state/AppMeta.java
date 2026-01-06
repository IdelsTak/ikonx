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

import java.util.*;
import javafx.scene.image.*;

public record AppMeta(
  Optional<String> appVersion,
  Optional<String> ikonliVersion,
  List<Image> icons) {

    public AppMeta {
        icons = List.copyOf(icons);
    }

    public AppMeta appVersion(String appVersion) {
        return new AppMeta(Optional.of(appVersion), ikonliVersion, icons);
    }

    public AppMeta withoutAppVersion() {
        return new AppMeta(Optional.empty(), ikonliVersion, icons);
    }

    public AppMeta ikonliVersion(String ikonliVersion) {
        return new AppMeta(appVersion, Optional.of(ikonliVersion), icons);
    }

    public AppMeta withoutIkonliVersion() {
        return new AppMeta(appVersion, Optional.empty(), icons);
    }

    public AppMeta icons(List<Image> icons) {
        return new AppMeta(appVersion, ikonliVersion, icons);
    }

    public static AppMeta empty() {
        return new AppMeta(
          Optional.empty(),
          Optional.empty(),
          List.of()
        );
    }
}
