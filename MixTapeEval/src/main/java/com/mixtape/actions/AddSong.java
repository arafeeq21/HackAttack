package com.mixtape.actions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mixtape.datamodel.MixTapeDto;
import com.mixtape.datamodel.Playlist;
import com.mixtape.datamodel.Song;
import com.mixtape.exception.ItemExistsException;
import com.mixtape.exception.ItemNotPresentException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
   * Action class to perform adding a song to the playlist
*/
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("add_song")
public class AddSong extends Action {
  
  @JsonProperty("song_id")
  private int songId;

  @JsonProperty("playlist_id")
  private int playlistId;

  /**
   * @param mixTapeDto: In-memory map of users, songs and playlists
   * @throws ItemNotPresentException is song is not present
   * @throws ItemNotPresentException when playlist is not present
   * @throws ItemExistsException when playlist already contains a song 
   */
  @Override
  public void apply(MixTapeDto mixTapeDto)
      throws ItemExistsException, ItemNotPresentException {
    
	Song song = mixTapeDto.getSongs().get(songId);
    
	// Check if it exists in the map
    if (song == null) {
      throw new ItemNotPresentException(
          String.format("%s : Song not present in tape", getClass()));
    }

    // Check if playlist exists in the map
    Playlist playlist = mixTapeDto.getPlaylists().get(playlistId);
    if (playlist == null)
      throw new ItemNotPresentException(
          String.format("%s : Playlist not present in tape", getClass()));

    // check if playlist does already contain the song
    if (playlist.getSongIds().contains(songId))
      throw new ItemExistsException(
          String.format("%s : Song already present in playlist in tape", getClass()));

    playlist.getSongIds().add(songId);
  }
}
