package com.mixtape.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Model for song
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Song {
  private int Id;
  private String artist;
  private String title;
}
