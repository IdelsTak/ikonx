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

import com.github.idelstak.ikonx.mvu.action.*;
import com.github.idelstak.ikonx.mvu.state.*;
import com.github.idelstak.ikonx.view.*;
import io.reactivex.rxjava3.core.*;
import org.pdfsam.rxjavafx.schedulers.*;

final class EffectFlow {

    private final AppMeta appMeta;
    private final LocalClipboard clipboard;

    EffectFlow(LocalClipboard clipboard, AppMeta appMeta) {
        this.clipboard = clipboard;
        this.appMeta = appMeta;
    }

    Observable<Action> apply(Observable<Action> actions) {
        return Observable.merge(
          stageIconsEffects(actions),
          clipboardEffects(actions),
          versionEffects(actions)
        );
    }

    private Observable<Action> stageIconsEffects(Observable<Action> actions) {
        return actions
          .filter(a -> a instanceof Action.StageIconsRequested)
          .flatMap(a ->
            Observable
              .fromCallable(() -> {
                  var icons = appMeta.icons();
                  if (icons.isEmpty()) {
                      throw new IllegalStateException("Application stage icons are missing from AppMeta");
                  }
                  return (Action) new Action.StageIconsResolved(icons);
              })
              .subscribeOn(JavaFxScheduler.platform())
              .onErrorReturn(Action.StageIconsFailed::new)
          );
    }

    private Observable<Action> versionEffects(Observable<Action> actions) {
        return actions
          .filter(a -> a instanceof Action.AppVersionRequested)
          .flatMap(a ->
            Observable
              .fromCallable(() -> {
                  var appVersion = appMeta
                    .appVersion()
                    .orElseThrow(() ->
                      new IllegalStateException(
                        "Application version is missing from AppMeta and the UI cannot display build information"
                      )
                    );
                  var ikonliVersion = appMeta
                    .ikonliVersion()
                    .orElseThrow(() ->
                      new IllegalStateException(
                        "Ikonli version is missing from AppMeta and icon metadata cannot be rendered correctly"
                      )
                    );
                  return (Action) new Action.AppVersionResolved(appVersion, ikonliVersion);
              })
              .subscribeOn(JavaFxScheduler.platform())
              .onErrorReturn(Action.AppVersionFailed::new)
          );
    }

    private Observable<Action> clipboardEffects(Observable<Action> actions) {
        return actions
          .filter(a -> a instanceof Action.CopyIconRequested)
          .flatMap(a -> {
              var request = (Action.CopyIconRequested) a;
              return Observable
                .fromCallable(() -> {
                    clipboard.copy(request.iconCode());
                    return (Action) new Action.CopyIconSucceeded(request.iconCode());
                })
                .subscribeOn(JavaFxScheduler.platform())
                .onErrorReturn(e -> new Action.CopyIconFailed(request.iconCode(), e));
          });
    }
}
