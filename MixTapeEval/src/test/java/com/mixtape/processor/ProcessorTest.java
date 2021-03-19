package com.mixtape.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixtape.models.MixTape;
import com.mixtape.models.MixTapeDto;
import com.mixtape.models.Playlist;
import com.mixtape.models.Song;
import com.mixtape.models.User;
import com.mixtape.exception.ItemExistsException;
import com.mixtape.exception.ItemNotPresentException;
import com.mixtape.actions.ApplyActions;
import com.mixtape.utilities.Utilities;
import com.mixtape.processors.Processor;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;

public class ProcessorTest {
  
  @Mock MixTape mixTape;
  @Mock MixTapeDto mixTapeDto;
  @Mock ApplyActions applyActions;
  @Mock Processor processor;

  private MixTape readMixTapeJson() {
    ObjectMapper objectMapper = new ObjectMapper();
    MixTape mixtape = new MixTape();
    User user1 = new User(1, "User1");
    User user2 = new User(2, "User2");
    Song song1 = new Song(1, "song1", "name1");
    Song song2 = new Song(2, "song2", "name2");
    Song song3 = new Song(3, "song3", "name3");
    Playlist playlist1 = new Playlist(1, 1, Arrays.asList(1, 3));
    Playlist playlist2 = new Playlist(2, 2, Arrays.asList(1, 2));
    mixtape.setUsers(Arrays.asList(user1, user2));
    mixtape.setSongs(Arrays.asList(song1, song2, song3));
    mixtape.setPlaylists(Arrays.asList(playlist1, playlist2));
    return mixtape;
  }

  private ApplyActions readChangesFileJson(String fileName)
      throws URISyntaxException, IOException {

    ClassLoader classLoader = getClass().getClassLoader();
    URI resource = Objects.requireNonNull(classLoader.getResource(fileName)).toURI();
    String contents = new String(Files.readAllBytes(Paths.get(resource)));
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(contents, ApplyActions.class);
  }

  @Before
  public void setup() {
    mixTape = readMixTapeJson();
    mixTapeDto = Utilities.CreateMixTapeDto(mixTape);
  }

  @Test
  public void CreatePlaylistSuccess()
      throws ItemExistsException, ItemNotPresentException, IOException {
    processor = new Processor(mixTapeDto);
    applyActions = readChangesFileJson("createPlaylist.json");
    mixTape = processor.process(applyActions);
    assertEquals(mixTape.getPlaylists().size(), 3);
  }

 @Test
 public void RemovePlaylistSuccess()
         throws ItemExistsException, ItemNotPresentException, IOException {
   processor = new Processor(mixTapeDto);
   applyActions = readChangesFileJson("removePlaylist.json");
   mixTape = processor.process(applyActions);
   assertEquals(mixTape.getPlaylists().size(), 1);
 }

  @Test(expected = ItemExistsException.class)
  public void processFailures()
          throws ItemExistsException, ItemNotPresentException, IOException {
    processor = new Processor(mixTapeDto);
    applyActions = readChangesFileJson("createPlaylistBroken.json");
    mixTape = processor.process(applyActions);
	
	applyActions = readChangesFileJson("removePlaylistBroken.json");
    mixTape = processor.process(applyActions);
  }
  
  @Test(expected = ItemExistsException.class)
  public void AddSongFailure()
      throws ItemExistsException, ItemNotPresentException, IOException {
    processor = new Processor(mixTapeDto);
    applyActions = readChangesFileJson("addSong.json");
    mixTape = processor.process(applyActions);    
  }

}