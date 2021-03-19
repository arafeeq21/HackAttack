package com.mixtape.models;

import lombok.Data;
import java.util.List;

/*
 * Model class for the original input file
 * 
 */
@Data
public class MixTape {
  List<User> users;
  List<Song> songs;
  List<Playlist> playlists;
}
