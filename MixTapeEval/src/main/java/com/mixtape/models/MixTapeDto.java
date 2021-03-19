package com.mixtape.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/*
* In-memory map of all users, songs and playlist. 
*/

@Data
@NoArgsConstructor
public class MixTapeDto {
  private static MixTapeDto mixTapeDto;
  private Map<Integer, User> users;
  private Map<Integer, Song> songs;
  private Map<Integer, Playlist> playlists;
}
