package com.mixtape.actions;

import lombok.Data;
import java.util.List;

/*
 * Class representing actions to apply from the change file
 * */
@Data
public class ApplyActions {
  private List<Action> actions;
}
