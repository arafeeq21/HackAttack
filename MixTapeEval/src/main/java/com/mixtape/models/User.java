package com.mixtape.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * Model for user
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  private int Id;
  private String name;
}
