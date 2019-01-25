package app;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class test {
	public static int objectCheck() {
		Scanner sc = new Scanner(System.in);
		boolean badInput = true;
		int objectNo = 0;
		do {
			System.out.println("Please input the number of encounters you want per world: ");
			try {
				objectNo = Integer.parseInt(sc.nextLine());
				badInput = false;
			} catch (Exception ex) {
				System.out.println("Please enter a valid number: ");
			}
		} while (badInput);
		if (objectNo < 1) {
			System.out.println("Needs at least one object, setting it to '1'.\n\n");
			objectNo = 1;
		} else if (objectNo > 10) {
			System.out.println("Too many objects, setting the value to 10. \n\n");
			objectNo = 10;
		}
		return objectNo;
	}

	public static ArrayList<ArrayList<Integer>> makeObjects(int objectNo) {
		Random rand = new Random();
		ArrayList<ArrayList<Integer>> coordList = new ArrayList<ArrayList<Integer>>();

		for (int i = 0; i <= objectNo; i++) { // creates a list of objects including your starting position
			coordList.add(new ArrayList<Integer>());
			coordList.get(i).add(rand.nextInt(5 + objectNo * 3));
			coordList.get(i).add(rand.nextInt(5 + objectNo * 3));

			for (int j = 0; j < i; j++) { // makes sure there are no duplicates
				while (coordList.get(i).equals(coordList.get(j))) {
					coordList.get(i).set(0, rand.nextInt(5 + objectNo * 3));
					coordList.get(i).set(1, rand.nextInt(5 + objectNo * 3));
				}
			}
		}
		return coordList;
	}

	public static void playGame(ArrayList<ArrayList<Integer>> coordList, int objectNo, int puppiesSaved) {
		int game = 1;
		int puppiesTotal = coordList.size() - 2;
		ArrayList<Integer> escapePos = coordList.get(0);
		System.out.println("You appear in a strange world, you see puppies in the distance. "
				+ "\nIt would be a good idea to find an exit and save the puppies as well! "
				+ "\nYou find a strange watch with a number on it, moving seems to change this number.");
		Scanner sc = new Scanner(System.in);

		gamestate: while (game == 1) {
			System.out.println("\nPlease input whether you would like to go north, south, east or west: ");
			String choice = sc.nextLine().toLowerCase();

			if (choice.equals("north") || choice.equals("n")) {
				System.out.println("You went north.");
				coordList.get(coordList.size() - 1).set(1, coordList.get(coordList.size() - 1).get(1) + 1);
			} else if (choice.equals("south") || choice.equals("s")) {
				System.out.println("You went south.");
				coordList.get(coordList.size() - 1).set(1, coordList.get(coordList.size() - 1).get(1) - 1);
			} else if (choice.equals("east") || choice.equals("e")) {
				System.out.println("You went east.");
				coordList.get(coordList.size() - 1).set(0, coordList.get(coordList.size() - 1).get(0) - 1);
			} else if (choice.equals("west") || choice.equals("w")) {
				System.out.println("You went west.");
				coordList.get(coordList.size() - 1).set(0, coordList.get(coordList.size() - 1).get(0) + 1);
			} else {
				System.out.println("Invalid input, try again: ");
			}

			ArrayList<Integer> playerPos = coordList.get(coordList.size() - 1);
			ArrayList<Double> distances = new ArrayList<Double>();

			for (int i = 0; i < coordList.size() - 1; i++) {
				distances.add((double) 0);
				distances.set(i,
						Math.sqrt(Math.pow((coordList.get(i).get(0) - coordList.get(coordList.size() - 1).get(0)), 2)
								+ Math.pow((coordList.get(i).get(1) - coordList.get(coordList.size() - 1).get(1)), 2)));
			}

			while (distances.size() > 1) {
				if (distances.get(0) < distances.get(1)) {
					distances.remove(1);
				} else {
					distances.remove(0);
				}
			}

			double distance = distances.get(0);
			System.out.println("Number on the watch is: " + "'" + distance + "'");

			if (distance == 0 && playerPos.equals(escapePos)) { // you hit the exit
				System.out.println("You find a device with two buttons on it."
						+ "\nThe back of it has a note that says:" + "\nPress the red button to leave the world."
						+ "\nPress the green button to go to another world and save more puppies."
						+ "\nWhich one do you press? red/green/neither");
				String choice2 = sc.nextLine().toLowerCase();
				while (choice2.equals("red") == false && choice2.equals("r") == false
						&& choice2.equals("green") == false && choice2.equals("g") == false && choice2.equals("neither")
						&& choice2.equals("n")) {
					System.out.println("Invalid input, try again: ");
				}

				if (choice2.equals("green") || choice2.equals("g")) { // you start a new world
					if (puppiesSaved > 1) {
						System.out.println(
								"You choose to press the green button and get instantly transported to another strange world. "
										+ "\nYou have saved" + puppiesSaved
										+ " puppies so far. Go save more puppies!\n");
					} else if (puppiesSaved == 1) {
						System.out.println(
								"You choose to press the green button and get instantly transported to another strange world. "
										+ "\nYou have saved a puppy." + "\nGo save more puppies!");
					}
					test.playGame(test.makeObjects(objectNo), objectNo, puppiesSaved);

				} else if (choice2.equals("red") || choice2.equals("r")) { // you quit the game
					if (puppiesSaved > 1) {
						System.out.println("You choose to press the red button and get instantly transported home."
								+ "\nYou have saved " + puppiesSaved + " puppies. Could've been better.");
					} else if (puppiesSaved == 1) {
						System.out.println("You choose to press the red button and get instantly transported home."
								+ "\nYou have saved a puppy. Could've been better.");
					} else {
						System.out.println("You choose to press the red button and get instantly transported home."
								+ "\nYou haven't saved a single puppy. You bastard.");
					}
					game = 0;
					break gamestate;

				} else if (choice2.equals("neither") || choice2.equals("n")) { // you ignore the buttons
					System.out.println(
							"You choose not to press the button and stay in this world. You can always come back.");
					if (puppiesSaved == 0) {
						System.out.println("Good choice, you won't save even a single puppy if you leave now!");
					} else if (puppiesTotal == 0) {
						System.out.println("But you don't have anything else to do in this world though!");
					} else {
						if (puppiesTotal > 1) {
							System.out.println("You can go and search for the remaining " + puppiesTotal + " puppies.");
						} else if (puppiesTotal == 1) {
							System.out.println("You can go and search for the remaining puppy.");
						}
					}
				}

			} else if (distance == 0 && playerPos.equals(escapePos) == false) { // you find a puppy
				puppiesSaved++;
				puppiesTotal--;
				if (puppiesSaved > 1) {
					System.out.println("You found a cute puppy wagging its tail at you!" + "\nYou have " + puppiesSaved
							+ " puppies. Good job!");
				} else if (puppiesSaved == 1) {
					System.out.println(
							"You found a cute puppy wagging its tail at you!" + "\nYou have a puppy. Good job!");
				}
				coordList.remove(coordList.indexOf(playerPos));
			}
		}
	}
}
