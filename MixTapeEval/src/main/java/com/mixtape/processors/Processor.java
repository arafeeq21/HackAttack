package com.mixtape.processors;

import com.mixtape.datamodel.MixTape;
import com.mixtape.datamodel.MixTapeDto;
import com.mixtape.exception.ItemNotPresentException;
import com.mixtape.exception.ItemExistsException;
import com.mixtape.actions.Action;
import com.mixtape.actions.ApplyActions;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@RequiredArgsConstructor
public class Processor {
  
  private final MixTapeDto mixTapeDto;

  /**
   * @param applyActions List of actions to be performed from change file.
   * @return Returns the modified MixTape object
   * @throws ItemNotPresentException when item needs to be modified is not present in the mixTape
   * @throws ItemExistsException when an item is already present in the mix tape object.
   */
  public MixTape process(ApplyActions applyActions)
      throws ItemNotPresentException, ItemExistsException {
    for (Action action : applyActions.getActions()) {
      action.execute(mixTapeDto);
    }
    return appendMixTape();
  }

  /**
   * This method is used for writing the changes to a new MixTape object
   *
   * @return Returns the updates object of MixTape
   */
  public MixTape appendMixTape() {
    MixTape mixTape = new MixTape();
    mixTape.setUsers(new ArrayList<>(this.mixTapeDto.getUsers().values()));
    mixTape.setSongs(new ArrayList<>(this.mixTapeDto.getSongs().values()));
    mixTape.setPlaylists(
        new ArrayList<>(this.mixTapeDto.getPlaylists().values()));
    return mixTape;
  }
}
