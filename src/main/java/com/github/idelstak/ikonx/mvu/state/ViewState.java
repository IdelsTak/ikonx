package com.github.idelstak.ikonx.mvu.state;

import com.github.idelstak.ikonx.icons.*;
import com.github.idelstak.ikonx.mvu.state.ActivityState.Idle;
import com.github.idelstak.ikonx.mvu.state.icons.*;
import com.github.idelstak.ikonx.mvu.state.version.*;
import com.github.idelstak.ikonx.view.grid.*;
import java.util.*;

public record ViewState(
  AppVersion version,
  StageIcons stageIcons,
  String searchText,
  Set<Pack> selectedPacks,
  Set<Style> selectedStyles,
  List<PackIkon> displayedIcons,
  List<PackIkon> favoriteIcons,
  List<PackIkon> recentIcons,
  ViewMode viewMode,
  ActivityState status,
  String statusMessage
  ) {

    public ViewState {
        selectedPacks = Set.copyOf(selectedPacks);
        selectedStyles = Set.copyOf(selectedStyles);
        displayedIcons = List.copyOf(displayedIcons);
        favoriteIcons = List.copyOf(favoriteIcons);
        recentIcons = List.copyOf(recentIcons);
    }

    public ViewState version(AppVersion version) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState stageIcons(StageIcons icons) {
        return new ViewState(version, icons, searchText, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState search(String text) {
        return new ViewState(version, stageIcons, text, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState select(Set<Pack> packs) {
        return new ViewState(version, stageIcons, searchText, packs, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState styles(Set<Style> styles) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, styles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState display(List<PackIkon> icons) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          icons, favoriteIcons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState favorites(List<PackIkon> icons) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIcons, icons, recentIcons, viewMode, status, statusMessage);
    }

    public ViewState recent(List<PackIkon> icons) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, icons, viewMode, status, statusMessage);
    }

    public ViewState mode(ViewMode mode) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, mode, status, statusMessage);
    }

    public ViewState signal(ActivityState state) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, state, statusMessage);
    }

    public ViewState message(String text) {
        return new ViewState(version, stageIcons, searchText, selectedPacks, selectedStyles,
          displayedIcons, favoriteIcons, recentIcons, viewMode, status, text);
    }

    public static ViewState initial() {
        var firstPack = Arrays.stream(Pack.values())
          .sorted(Comparator.comparing(Enum::name))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("No icon packs found"));

        var icons = Arrays.stream(firstPack.getIkons())
          .map(ikon -> new PackIkon(firstPack, ikon))
          .toList();

        return new ViewState(
          new AppVersion.Unknown(),
          new StageIcons.Unknown(),
          "",
          Set.of(firstPack),
          Set.of(), // selectedStyles empty initially
          icons,
          List.of(), // favoriteIcons empty
          List.of(), // recentIcons empty
          new ViewMode.Grid(),
          new Idle(),
          String.format("%d icons found", icons.size())
        );
    }
}
