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

## Testing
Test coverage at 85% excluding models and configuration
A mock client was used within the test cases.

## External Dependencies
This project uses 3 datapoints from Trafiklab’s SL API:
1. Lines API: This endpoint is used to fetch all Bus lines  
2. Stops API: This endpoint is used to fetch all Bus stops
3. Journeys API: This endpoint contains the mapping of lines to stops. It shows a list of all lines and all the stops they reach.

## How it works
1. Fetch line data
2. Fetch journey data
3. Create map of line->count(stops)
4. Sort map and take top 10 elements
5. Fetch Stop data
6. Get stop names by filtering stop data on journey.stopid=stop.id
7. Load map and list of names into tuple and return tuple