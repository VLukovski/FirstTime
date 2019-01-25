package test;

import app.test;

public class app {
	public static void main(String[] s) {
		int objectNo = test.objectCheck();
		test.playGame(test.makeObjects(objectNo), objectNo, 0);
	}
}
