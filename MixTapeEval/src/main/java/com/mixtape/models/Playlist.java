package com.mixtape.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
/*
 * Model for playlist
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {
  private int Id;

  @JsonProperty("user_id")
  private int UserId;

  @JsonProperty("song_ids")
  private List<Integer> songIds;
}
