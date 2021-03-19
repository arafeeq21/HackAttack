package com.mixtape.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mixtape.actions.ApplyActions;
import com.mixtape.exception.ItemExistsException;
import com.mixtape.exception.ItemNotPresentException;
import com.mixtape.models.MixTape;
import com.mixtape.models.MixTapeDto;
import com.mixtape.processors.Processor;
import com.mixtape.utilities.Utilities;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(
    name = "execute",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = "Ingest input file and changes file")
public class SyncService implements Runnable {
  @CommandLine.Option(
      names = "--input-json",
      description = "path of input mixtape json",
      defaultValue = "src/main/resources/mixtape.json")
  private File input;

  @CommandLine.Option(
      names = "--change-json",
      description = "path of input change json",
      defaultValue = "src/main/resources/changes.json")
  private File change;

  @CommandLine.Option(
      names = "--output-json",
      description = "path for generated output file.",
      defaultValue = "output.json")
  private File output;

  public static void main(String[] args) {
    SyncService syncService = new SyncService();
    int exitCode = new CommandLine(syncService).execute(args);
    System.exit(exitCode);
  }

  @Override
  public void run() {

    ObjectMapper objectMapper = new ObjectMapper();
    try {
	  
	  // Read the input; store data in memory for fast access
      MixTape mixTape = objectMapper.readValue(input, MixTape.class);
      System.out.println("Parsing Mixtape");      
	  MixTapeDto mixTapeDto = Utilities.CreateMixTapeDto(mixTape);
      
      // Read the changes and merge with in-memory snapshot 
 	  ApplyActions applyActions = objectMapper.readValue(change, ApplyActions.class);
      System.out.println("Parsing change actions");
      
	  Processor processor = new Processor(mixTapeDto);
      MixTape mergedMixTape = processor.process(applyActions);

	  // Generate merged output 
 	  System.out.println("Generating output file");
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(output, mergedMixTape);
      System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(mergedMixTape));

    } catch (IOException e) {
	e.printStackTrace();    
    } catch (ItemNotPresentException itemNotPresentException) {
      	itemNotPresentException.printStackTrace();
    } catch (ItemExistsException itemExistsException) {
        itemExistsException.printStackTrace();
    }
  }
}
