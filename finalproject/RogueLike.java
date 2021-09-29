package finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class RogueLike {
	public static void main(String[] args) {

		WorldMap gameMap = new WorldMap();
		FileManager file = new FileManager();
		
		file.loadMap(Config.MAP_FILENAME);
		char[][] map = file.buildMap();
		
		
		
		gameMap.initialize(map);
		gameMap.run();
		
		
		System.out.println("Game over...");
	}
}
