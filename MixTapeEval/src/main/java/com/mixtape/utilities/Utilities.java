package com.mixtape.Utilities;

import com.mixtape.models.MixTape;
import com.mixtape.models.MixTapeDto;
import com.mixtape.models.Playlist;
import com.mixtape.models.Song;
import com.mixtape.models.User;

import java.util.function.Function;
import java.util.stream.Collectors;

/*
* Utilities class.
* */

public class Utilities {
  private Utilities() {}

  /**
   * Create in-memory MixTapeDto 
   * @param mixTape read from the ingest file that contains map of users, songs and playlist
   * @return MixTapeDto for in-memory access of users, songs and playlist.
   */
  public static MixTapeDto CreateMixTapeDto(MixTape mixTape) {
    MixTapeDto mixTapeDto = new MixTapeDto();

    mixTapeDto.setusers(
        mixTape.getUsers().stream().collect(Collectors.toMap(User::getId, Function.identity())));

    mixTapeDto.setsongs(
        mixTape.getSongs().stream().collect(Collectors.toMap(Song::getId, Function.identity())));

    mixTapeDto.setplaylists(
        mixTape.getPlaylists().stream()
            .collect(Collectors.toMap(Playlist::getId, Function.identity())));

    return mixTapeDto;
  }
}
