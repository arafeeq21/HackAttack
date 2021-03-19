package com.mixtape.actions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mixtape.datamodel.MixTapeDto;
import com.mixtape.exception.ItemExistsException;
import com.mixtape.exception.ItemNotPresentException;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "operation_type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = CreatePlaylist.class, name = "create_playlist"),
  @JsonSubTypes.Type(value = AddSong.class, name = "add_song"),
  @JsonSubTypes.Type(value = RemovePlaylist.class, name = "remove_playlist")
})

@Data
public abstract class Action {
  public abstract void apply(MixTapeDto mixTapeDto)
      throws ItemExistsException, ItemNotPresentException;
}
