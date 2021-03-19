package com.mixtape.actions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mixtape.datamodel.MixTapeDto;
import com.mixtape.datamodel.Playlist;
import com.mixtape.exception.ItemNotPresentException;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
* Action class to perform deletion of a user playlist with songs 
*/
 
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("remove_playlist")
public class RemovePlaylist extends Action {
  
  @JsonProperty("playlist_id")
  private int playListId;

  /**
	* @param mixTapeDto: In-memory map of users, songs and playlists
	* @throws ItemNotPresentException when playlist is not in the tape	
  */  
  @Override
  public void apply(MixTapeDto mixTapeDto)
      throws ItemNotPresentException {
    
	final Playlist playlist = mixTapeRepository.getPlaylistSearchMap().get(playListId);

    if (playlist == null)
      throw new ItemNotPresentException(
          String.format("%s: Playlist not present in the tape", getClass()));

    mixTapeDto.getPlaylists().remove(playlist.getId());
  }
}
