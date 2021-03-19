package com.mixtape.actions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.mixtape.datamodel.MixTapeDto;
import com.mixtape.datamodel.Playlist;
import com.mixtape.datamodel.User;
import com.mixtape.exception.ItemExistsException;
import com.mixtape.exception.ItemNotPresentException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
* Action class to perform creation of a new user playlist with songs
*/
@Data
@EqualsAndHashCode(callSuper=false)
@JsonTypeName("create_playlist")
public class CreatePlaylist extends Action {
  
  @JsonProperty("playlist")
  Playlist playlist;

  /**
   * @param mixTapeDto: In-memory map of users, songs and playlists
   * @throws ItemNotPresentException when a user is not present
   * @throws ItemNotPresentException when playlist is not in the tape
   * @throws ItemExistsException when playlist already exists 
  */
  @Override
  public void apply(MixTapeDto mixTapeDto)
          throws ItemExistsException, ItemNotPresentException {
    
	final User user = mixTapeDto.getUsers().get(playlist.getUserId());

    //check if the playList is already present
    if(mixTapeDto.getPlaylists().containsKey(playlist.getId())){
      throw new ItemExistsException(String.format("%s: Playlist already exists", getClass()));
    }

    // check if the user id is provided and it is already present
    if (user == null) {
      throw new ItemNotPresentException(String.format("%s: UserId not provided", getClass()));
    }
    List<Integer> songs = playlist.getSongIds();
	
    // check if the  songs list is not empty
    if (songs.isEmpty()) {
      throw new ItemNotPresentException(String.format("%s: Provided list has no songs; skip add", getClass()));
    }

    // check if all song Ids are valid
    for (int songId : songs) {
      if (!mixTapeDto.getSongs().containsKey(songId)) {
        throw new EntityNotPresentException(
            String.format("%s : Song Id not present in the tape", getClass()));
      }
    }

    Playlist playlist = new Playlist();
    playlist.setId(playlist.getId());
    playlist.setSongIds(songs);
    playlist.setUserId(playlist.getUserId());
    mixTapeDto.getPlaylists().put(playlist.getId(), playlist);
  }
}
