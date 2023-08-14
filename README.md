# SBAB SL Assignment

## Requirements
1. JDK 17.0.4.1
2. Maven 3.8.6
## Building the application
From the root directory of the project, in a terminal, run the commands below to build and run the jar:
```bash
mvn clean install
cd ./target
java -jar .\sbab-challenge-0.0.1-SNAPSHOT.jar
```

Alternatively, you can load the project into IntelliJ and run the main method in the class SbabChallengeApplication

## Testing
Test coverage at 99% excluding models and configuration

A mock client was used within the test cases.

## External Dependencies
This project uses 2 datapoints from Trafiklab’s SL API:
1. Stops API: This endpoint is used to fetch all Bus stops
2. Journeys API: This endpoint contains the mapping of lines to stops. It shows a list of all lines and all the stops they reach.

## How it works
1. Fetch journey data
2. Create map of line->count(stops)
3. Sort map and take top 10 elements
4. Fetch Stop data
5. Get stop names by filtering stop data on journey.stopid=stop.id
6. Load map and list of names into tuple and return tuple

## Notes
1. Not all stops on the line with the highest number of stops have a corresponding Stop object on the Stops API.
2. I am working with the assumption that there aren't any duplicate stops on a line.