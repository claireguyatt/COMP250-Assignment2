public class TrainNetwork {
    final int swapFreq = 2;
    TrainLine[] networkLines;

    public TrainNetwork(int nLines) {
        this.networkLines = new TrainLine[nLines];
    }

    public void addLines(TrainLine[] lines) {
        this.networkLines = lines;
    }

    public TrainLine[] getLines() {
        return this.networkLines;
    }

    // method that shuffles all TrainLines in the TrainNetwork
    public void dance() {
        System.out.println("The tracks are moving!");
        for (int i = 0; i < this.networkLines.length; i++) {
            this.networkLines[i].shuffleLine();
        }
    }

    // method that sorts all TrainLines in the TrainNetwork
    public void undance() {
        for (int i = 0; i < this.networkLines.length; i++) {
            this.networkLines[i].sortLine();
        }
    }

    // method that simulates a TrainRide
    public int travel(String startStation, String startLine, String endStation, String endLine) {

        int hoursCount = 0;

        try {

            TrainLine curLine = this.getLineByName(startLine);
            TrainStation curStation = curLine.findStation(startStation); //use this variable to store the current station.

            System.out.println("Departing from " + startStation);

            TrainStation previous = null;

            // begin journey!
            while (!curStation.getName().equals(endStation) || !curLine.getName().equals(endLine)) {

                if (hoursCount == 168) {
                    System.out.println("Jumped off after spending a full week on the train. Might as well walk.");
                    return hoursCount;
                }
                //prints an update on your current location in the network.
                System.out.println("Traveling on line " + curLine.getName() + ":" + curLine.toString());
                System.out.println("Hour " + hoursCount + ". Current station: " + curStation.getName() + " on line " + curLine.getName());
                System.out.println("=============================================");

                // shuffle the tracks every 2 hours
                if (hoursCount % 2 == 0 && hoursCount != 0) {
                    this.dance();
                }
                // travel to next correct TrainStation, annd make note of previous TS
                TrainStation temp = curStation;
                curStation = curLine.travelOneStation(curStation, previous);
                curLine = curStation.getLine();
                previous = temp;
                hoursCount++;
            }

            System.out.println("Arrived at destination after " + hoursCount + " hours!");
            return hoursCount;

        }
        // catch possibly encountered Exceptions
        catch (StationNotFoundException S) {
            System.out.println("We couldn't find your train station, so we'll have to end your journey early after "
                    + hoursCount + " hours of travel. Try taking a flying car instead.");
            return hoursCount;
        }
        catch (LineNotFoundException L) {
            System.out.println("We couldn't find your train line, so we'll have to end your journey early after "
                    + hoursCount + " hours of travel. Try taking a flying car instead.");
            return hoursCount;
        }
    }

    // method that takes a TrainLine's name as input and returns TrainLine in the TrainNetwork with input name
    public TrainLine getLineByName(String lineName) {

        for (int i = 0; i < this.networkLines.length; i++) {
            if (this.networkLines[i].getName().equals(lineName)) {
                return this.networkLines[i];
            }
        }
        // throws exception if there is no TrainLine in the TrainNetwork with input name
        throw new LineNotFoundException(lineName);
    }

    //prints a plan of the network for you.
    public void printPlan() {
        System.out.println("CURRENT TRAIN NETWORK PLAN");
        System.out.println("----------------------------");
        for (int i = 0; i < this.networkLines.length; i++) {
            System.out.println(this.networkLines[i].getName() + ":" + this.networkLines[i].toString());
        }
        System.out.println("----------------------------");
    }
}

//exception when searching a network for a LineName and not finding any matching Line object.
class LineNotFoundException extends RuntimeException {
    String name;

    public LineNotFoundException(String n) {
        name = n;
    }

    public String toString() {
        return "LineNotFoundException[" + name + "]";
    }
}