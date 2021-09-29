package finalproject;

import java.util.Scanner;

public class Config{
	static FileManager fm = new FileManager();
	
	static Scanner scan = new Scanner(System.in); 
	public static int mapRow = fm.buildMap().length;  
	public static int mapCol = fm.buildMap()[0].length;  
	final public static char UP = 'W'; 
	final public static char up = 'w';
	final public static char DOWN = 'S';
	final public static char down = 's';
	final public static char Left = 'A';
	final public static char left = 'a';
	final public static char Right = 'D';
	final public static char right = 'd';
	final public static char Push = 'P';
	final public static char push = 'p';
	final public static char Nothing = 'N';
	final public static char nothing = 'n';
	final public static char Eat = 'E';
	final public static char eat = 'e';
	final public static char Attack = 'A';
	final public static char attack = 'a';
	final public static char Leave = 'L';
	final public static char leave='l';
	final public static char Get = 'G';
	final public static char get = 'g';
	
	
	
	final public static String mainMenu = "(A)Left (D)Right (W)Up (S)Down (Q)uit: "; 
	final public static String RockMenu = "(P)ush (N)othing: ";
	final public static String FoodMenu = "(E)at (N)othing: ";
	final public static String MonsterMenu = "(A)ttack (L)eave: ";
	final public static String MAP_FILENAME = "oop\\map.dat";
	final public static String afterEat = "You ate the food";
	final public static String afterAttack="You attacked the monster!!!";
	final public static String receiveAttack = "The monster attacked you!!!";
	final public static String bump = "bump!!";
	final public static String WeaponMenu="(G)et (N)othing: ";
	final public static String afterGet = "Short sword equipped!";

}
