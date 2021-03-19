# MixTapeEval
This is a code exercise provided by Highspot for evaluation

## Objective
We need to ingest an input file *Mixtape.json* which contains the list of users, songs and playlists. Then, ingest another input file *changes.json* which contains changes that we need to apply to the *Mixtape.json* object.

## Requirements
* Java version > 1.7
* Gradle bootstrap >=6.5
* OS: Mac or Win

### Structure of the changes.json file
The Mixtape.json file provided, therefore can be parsed to generate the underlying models.

* *changes.json* contains the list of changes aka actions we need to apply 
on songs, playlists against known items in the *mixtape.json*
```
{
  "operations": [{
    "operation_type": "add_song",
    "song_id": "10",
    "playlist_id": "2"
  },
    {
      "operation_type": "create_playlist",
      "playlist": {
        "id": "6",
        "user_id": "3",
        "song_ids": [
          "6",
          "7",
          "13"
        ]
      }
    },
    {
      "operation_type": "remove_playlist",
      "playlist_id": "1"
    }
  ]
}
```
There are exactly three operations, that are reuired to manipulate input mixtape, namely:
* **create_playlist** : Creates a new playlist with valid songs
* **add_song** : Add a valid song to an existing playlist
* **remove_playlist**: Remove an existing playlist 

### Execute Code
**Clone solution repo**

```
git clone https://github.com/arafeeq21/HackAttack/MixTapeEval.git
```
**cd into the project folder**
```
cd MixTapeEval
```
**Bootstrap project using gradle framework**
```
./gradlew build
```
**Execute the command to run the SyncService against mixtape Json, change file; to generate the output Json**
```
./gradlew run --args="--input-file=<path of the input file> --change-file=<path of the changes file> --output-file=<path for the output JSon file>
```
*Usage*
```
./gradlew run --args="--input-file=src/main/resources/mixtape.json --change-file=src/main/resources/changes.json --output-file=output.json"
```
This will also execute without *args* as the input files are configured as default arguments for input/change files.

### Scale v/s Performance 
As the input files can get inordinately large, they expose a potential memory constraint, in that, render the service to terminate upon reaching allowed process memory limits. It can also adversly impact other applications , services running concurrently on the server. However, within allowed limits we have heavily optimized our business objects to be accessed directly from an in-memory cache.

Therefore, to handle large input, we need to process the input by chunking data into memory and capture the last successful watermark. Furthermore, we can parallelize processing each chunk across multiple threads. 

Similarly, we can extend this idea of handling large file chunks by distributing processing across multiple servers (horizontal scaling) as well. To achieve that, we need to persist the file chunks in a database (distributed cache) mapping each chunk to sorted range. 

Then, we can distribute processing every file chunk from changes, and compute an hash for every chunk and route it to a server. The processing server can index the mixTape file to generate the desired output.
 





