public class TrainRide {

	public static void main(String[] args) {
		System.out.println("Welcome to the Confusing Railroad!");

		// Constructs a train network
		TrainNetwork tNet = generateTrainNetwork();

		// Prints the train network plan
		tNet.printPlan();

		// Travels from Little Whinging to Hogwarts.
		 tNet.travel("1.Little Whinging", "Scarlet", "3.Snowy Owl", "Purple");
		tNet.printPlan();

		// Resets the network to its initial position
		System.out.println("Resetting the network");
		tNet.undance();
		tNet.printPlan();

		 /*

		TrainNetwork VIARail = generateTrainNetwork();
		VIARail.printPlan();
		//VIARail.travel("", "fromHome", "Aldershot", "fromHome");
		VIARail.travel("Oshawa", "fromTO", "Aldershot", "fromHome");
		VIARail.printPlan();
		VIARail.undance();
		VIARail.printPlan();

		 */


	}

	// Calls constructors and methods to implement the network shown in the handout map.
	public static TrainNetwork generateTrainNetwork() {

		// creating line 1
		TrainStation s1 = new TrainStation("1.Little Whinging");
		TrainStation s5 = new TrainStation("5.St Mungo's");

		s1.setRight(s5);
		s5.setLeft(s1);

		TrainLine l1 = new TrainLine(s1, s5, "Scarlet", true);

		TrainStation s2 = new TrainStation("2.Wizard Hat");
		l1.addStation(s2);
		TrainStation s3 = new TrainStation("3.Hogsmeade");
		l1.addStation(s3);
		TrainStation s4 = new TrainStation("4.Diagon Alley- 1/3");
		l1.addStation(s4);

		// creating line 2
		TrainStation t1 = new TrainStation("1.Gringotts");
		TrainStation t5 = new TrainStation("5.Leaky Cauldron");

		t1.setRight(t5);
		t5.setLeft(t1);

		TrainLine l2 = new TrainLine(t1, t5, "Grey", true);

		TrainStation t2 = new TrainStation("2.Diagon Alley - 2/3\"");
		l2.addStation(t2);
		TrainStation t3 = new TrainStation("3.Ollivanders");
		l2.addStation(t3);
		TrainStation t4 = new TrainStation("4.King's Cross - 3/5");
		l2.addStation(t4);

		s1.setConnection(l2, t2);
		t2.setConnection(l1, s1);

		// creating line 3
		TrainStation u1 = new TrainStation("1.King's Cross - 4/5");
		TrainStation u5 = new TrainStation("5.Hogwarts");

		u1.setRight(u5);
		u5.setLeft(u1);

		TrainLine l3 = new TrainLine(u1, u5, "Purple", true);

		TrainStation u2 = new TrainStation("2.Ministry of Magic");
		l3.addStation(u2);
		TrainStation u3 = new TrainStation("3.Snowy Owl");
		l3.addStation(u3);
		TrainStation u4 = new TrainStation("4.Godric's Hollow");
		l3.addStation(u4);

		u1.setConnection(l2, t4);
		t4.setConnection(l3, u1);

		TrainNetwork tNet = new TrainNetwork(1);
		TrainLine[] lines = {l1, l2, l3};
		tNet.addLines(lines);

		return tNet;


		// new tester Network

		/*
		TrainStation Aldershot = new TrainStation("Aldershot");
		TrainStation Toronto = new TrainStation("Toronto");

		Aldershot.setRight(Toronto);
		Toronto.setLeft(Aldershot);

		TrainStation Grimsby = new TrainStation("Grimsby");

		TrainLine fromHome = new TrainLine(Aldershot, Toronto, "fromHome", true);
		//fromHome.addStation(Grimsby);

		TrainStation Toronto2 = new TrainStation("Toronto 2");
		TrainStation Oshawa = new TrainStation("Oshawa");
		TrainStation Belleville = new TrainStation("Belleville");
		TrainStation Kingston = new TrainStation("Kingston");
		TrainStation Dorval = new TrainStation("Dorval");
		TrainStation K = new TrainStation("K");
		TrainStation Montreal = new TrainStation("Montreal");

		Toronto2.setRight(Montreal);
		Montreal.setLeft(Toronto2);

		TrainLine fromTO = new TrainLine(Toronto2, Montreal, "fromTO", true);
		fromTO.addStation(Oshawa);
		fromTO.addStation(Belleville);
		fromTO.addStation(Kingston);
		fromTO.addStation(Dorval);
		fromTO.addStation(K);


		Toronto.setConnection(fromTO, Toronto2);
		Toronto2.setConnection(fromHome, Toronto);

		TrainLine[] VIAStations = {fromHome, fromTO};
		TrainNetwork VIARail = new TrainNetwork(2);
		VIARail.addLines(VIAStations);

		return VIARail;

		 */
	}
}