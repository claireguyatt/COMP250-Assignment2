import java.util.Arrays;
import java.util.Random;

public class TrainLine {

	private TrainStation leftTerminus;
	private TrainStation rightTerminus;
	private String lineName;
	private boolean goingRight;
	public TrainStation[] lineMap;
	public static Random rand;

	public TrainLine(TrainStation leftTerminus, TrainStation rightTerminus, String name, boolean goingRight) {
		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		this.lineMap = this.getLineArray();
	}

	public TrainLine(TrainStation[] stationList, String name, boolean goingRight)
	/*
	 * Constructor for TrainStation input: stationList - An array of TrainStation
	 * containing the stations to be placed in the line name - Name of the line
	 * goingRight - boolean indicating the direction of travel
	 */
	{
		TrainStation leftT = stationList[0];
		TrainStation rightT = stationList[stationList.length - 1];

		stationList[0].setRight(stationList[stationList.length - 1]);
		stationList[stationList.length - 1].setLeft(stationList[0]);

		this.leftTerminus = stationList[0];
		this.rightTerminus = stationList[stationList.length - 1];
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;

		for (int i = 1; i < stationList.length - 1; i++) {
			this.addStation(stationList[i]);
		}

		this.lineMap = this.getLineArray();
	}

	public TrainLine(String[] stationNames, String name,
			boolean goingRight) {/*
									 * Constructor for TrainStation. input: stationNames - An array of String
									 * containing the name of the stations to be placed in the line name - Name of
									 * the line goingRight - boolean indicating the direction of travel
									 */
		TrainStation leftTerminus = new TrainStation(stationNames[0]);
		TrainStation rightTerminus = new TrainStation(stationNames[stationNames.length - 1]);

		leftTerminus.setRight(rightTerminus);
		rightTerminus.setLeft(leftTerminus);

		this.leftTerminus = leftTerminus;
		this.rightTerminus = rightTerminus;
		this.leftTerminus.setLeftTerminal();
		this.rightTerminus.setRightTerminal();
		this.leftTerminus.setTrainLine(this);
		this.rightTerminus.setTrainLine(this);
		this.lineName = name;
		this.goingRight = goingRight;
		for (int i = 1; i < stationNames.length - 1; i++) {
			this.addStation(new TrainStation(stationNames[i]));
		}

		this.lineMap = this.getLineArray();

	}

	// adds a station at the last position before the right terminus
	public void addStation(TrainStation stationToAdd) {
		TrainStation rTer = this.rightTerminus;
		TrainStation beforeTer = rTer.getLeft();
		rTer.setLeft(stationToAdd);
		stationToAdd.setRight(rTer);
		beforeTer.setRight(stationToAdd);
		stationToAdd.setLeft(beforeTer);

		stationToAdd.setTrainLine(this);

		this.lineMap = this.getLineArray();
	}

	public String getName() {
		return this.lineName;
	}

	// method that returns the number of TrainStations in the TrainLine
	public int getSize() {

		int counter = 1;
		TrainStation current = this.leftTerminus;
		while (current != this.rightTerminus) {
			counter++;
			current = current.getRight();
		}
		return counter;
	}

	public void reverseDirection() {
		this.goingRight = !this.goingRight;
	}

	// method that makes a Train travel to the next station it should go to
	public TrainStation travelOneStation(TrainStation current, TrainStation previous) {

		// throw exception if the TrainStation is not in the TrainLine
		findStation(current.getName());

		// if a line transfer is possible (that doesn't go back where it came from), switch lines
		if (current.hasConnection && !current.getTransferStation().equals(previous)) {
			return current.getTransferStation();
		}
		// reverse the direction of travel field of the TrainLine if the Train has reached a terminus
		if ((current.equals(this.leftTerminus) && !this.goingRight) || (current.equals(this.rightTerminus) && this.goingRight)) {
			this.reverseDirection();
		}
		// if no transfer is possible, go to the next station
		return getNext(current);
	}

	// method that retrieves the next TrainStation after the input TrainStation
	public TrainStation getNext(TrainStation station) {

		// throw exception if the TrainStation is not in the TrainLine
		findStation(station.getName());

		// return the station to the right/left of the input station in the line depending on the direction of the train
		if (this.goingRight) {
			return station.getRight();
		} else return station.getLeft();
	}

	// method that finds the TrainStation in the line who's name matches the input name
	public TrainStation findStation(String name) {

		// iterate through line to finding matching TS
		TrainStation current = this.leftTerminus;
		for (int i = 0; i < this.getSize(); i++) {
			if (current.getName().equals(name)) {
				return current;
			}
			current = current.getRight();
		}
		// throw exception if name is not found in TrainLine
		throw new StationNotFoundException(name);
	}

	// method that sorts the TrainLine's TrainStations into alphabetical order
	public void sortLine() {

		// sort using bubble sort
		int count = 0;
		boolean b = true;
		while (b == true) {
			b = false;
			for (int i=0; i<this.lineMap.length-1-count; i++) {
				int j=0;
				while ((lineMap[i].getName()).charAt(j) == (lineMap[i+1].getName()).charAt(j)) {
					j++;
					// check for if the Trains have the same name, but one is shorter (e.g. Hogwarts & Hog)
					if ((lineMap[i+1].getName()).length() == j) {
						swap(lineMap[i], lineMap[i+1]);
						b = true;
						j--;
					} else if (lineMap[i].getName().length() == j) {
						b = true;
						j--;
						break;
					}
				}
				if ((lineMap[i].getName()).charAt(j) > (lineMap[i+1].getName()).charAt(j)) {
					swap(lineMap[i], lineMap[i+1]);
					b = true;
				}
			}
			count++;
		}
	}

	// method that returns a TS array of all the trains in the line in order left-right
	public TrainStation[] getLineArray() {

		// initialize TS array with found size ^ & populate it with TSs in the line
		TrainStation[] lineArray = new TrainStation[this.getSize()];
		lineArray[0] = this.leftTerminus;
		for (int i = 0; i < this.getSize()-1; i++) {
				lineArray[i + 1] = lineArray[i].getRight();
		}
		// return the populated TS array
		return lineArray;
	}

	private TrainStation[] shuffleArray(TrainStation[] array) {
		Random rand = new Random();
		rand.setSeed(11);

		for (int i = 0; i < array.length; i++) {
			int randomIndexToSwap = rand.nextInt(array.length);
			TrainStation temp = array[randomIndexToSwap];
			array[randomIndexToSwap] = array[i];
			array[i] = temp;
		}
		this.lineMap = array;
		return array;
	}

	// method that shuffles the TrainStations in lineMap, then sets order of TSs to match the order in lineMap
	public void shuffleLine() {

		// you are given a shuffled array of trainStations to start with
		TrainStation[] lineArray = this.getLineArray();
		TrainStation[] shuffledArray = shuffleArray(lineArray);

		this.leftTerminus = this.lineMap[0];
		TrainStation current = this.leftTerminus;
		current.setNonTerminal();
		current.setLeftTerminal();
		current.setLeft(null);
		
		for (int i=1; i<this.lineMap.length; i++) {
			current.setRight(lineMap[i]);
			lineMap[i].setLeft(current);
			lineMap[i].setNonTerminal();
			current = current.getRight();
		}
		this.rightTerminus = current;
		current.setNonTerminal();
		current.setRightTerminal();
		current.setRight(null);
	}

	// helper swap method fro sorting TrainLines
	public void swap(TrainStation a, TrainStation b) {

		// check to make sure they're next to each other (for bubble sort)
		if (a.getRight().equals(b)) {
			// for 2 stations in the middle of the line (no terminals)
			if (!a.isLeftTerminal() && !b.isRightTerminal()) {
				TrainStation previous = a.getLeft();
				TrainStation next = b.getRight();
				previous.setRight(b);
				b.setLeft(previous);
				b.setRight(a);
				a.setLeft(b);
				next.setLeft(a);
				a.setRight(next);
			}
			// if both input TrainStations are terminals
			else if (a.isLeftTerminal() && b.isRightTerminal()) {
				a.setLeft(b);
				a.setRight(null);
				a.setRightTerminal();
				this.rightTerminus = a;
				b.setRight(a);
				b.setLeft(null);
				b.setLeftTerminal();
				this.leftTerminus = b;
			}
			// for a swap including a left terminal
			else if (a.isLeftTerminal()) {
				TrainStation next = b.getRight();
				b.setLeft(null);
				b.setRight(a);
				b.setLeftTerminal();
				this.leftTerminus = b;
				next.setLeft(a);
				a.setRight(next);
				a.setLeft(b);
				a.setNonTerminal();
			}
			// for a swap including a right terminal
			else if (b.isRightTerminal()) {
				TrainStation previous = a.getLeft();
				b.setRight(a);
				b.setLeft(previous);
				b.setNonTerminal();
				a.setLeft(b);
				a.setRight(null);
				a.setRightTerminal();
				this.rightTerminus = a;
				previous.setRight(b);
			}
			// update line array to match TrainLine
			TrainStation[] lineArray = new TrainStation[this.getSize()];
			lineArray[0] = this.leftTerminus;
			for (int i = 0; i < this.getSize() - 1; i++) {
				lineArray[i + 1] = lineArray[i].getRight();
			}
			this.lineMap = lineArray;
		}
	}

	public String toString() {
		TrainStation[] lineArr = this.getLineArray();
		String[] nameArr = new String[lineArr.length];
		for (int i = 0; i < lineArr.length; i++) {
			nameArr[i] = lineArr[i].getName();
		}
		return Arrays.deepToString(nameArr);
	}

	public boolean equals(TrainLine line2) {

		// check for equality of each station
		TrainStation current = this.leftTerminus;
		TrainStation curr2 = line2.leftTerminus;

		try {
			while (current != null) {
				if (!current.equals(curr2))
					return false;
				else {
					current = current.getRight();
					curr2 = curr2.getRight();
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public TrainStation getLeftTerminus() {
		return this.leftTerminus;
	}

	public TrainStation getRightTerminus() {
		return this.rightTerminus;
	}
}

//Exception for when searching a line for a station and not finding any station of the right name.
class StationNotFoundException extends RuntimeException {
	String name;

	public StationNotFoundException(String n) {
		name = n;
	}

	public String toString() {
		return "StationNotFoundException[" + name + "]";
	}
}
