package finalproject;

import java.util.ArrayList;
import java.util.Vector;



public class WorldMap {

	FileManager fm = new FileManager();
		private static ArrayList<Entity> ar = new ArrayList<Entity>();
		private Player p;
		private Rock r[]=new Rock[3];
		private Food f[]=new Food[2];
		private Monster m;
		private Weapon w;
	
	
		private static Entity[][] map=new Entity[Config.mapRow][Config.mapCol];
		static char[][] map2 = new char[Config.mapRow][Config.mapCol];
		
	
	//원래 맵이 여기 있다면....
	
	private void initializeplayer(){
		p.hp=30;
		p.max_hp=100;
		p.damage=10;
		p.setIcon('@');
		
	}
	
	
	void run(){
		
		
		
		while(true){
		
			System.out.println("Entity의 개수 : "+Entity.cnt);
			showMap();
			showInfo(p);
			p.inputCommand();
			
		
		}
		
		
	}
	private void insertEntity(Entity e){ //엔티티를 맵에 삽입
		ar.add(e);
		map[e.getRow()][e.getCol()]=e;
		
	}
	
	
	
	private void deleteEntity(Entity e){//엔티티를 맵에서 삭제.
	
		
			map[e.getRow()][e.getCol()]=null;
			map2[e.getRow()][e.getCol()]='.';
			
			for(int i=0;i<ar.size();i++){
				if(ar.get(i).equals(e)){
					ar.remove(i);
					Entity.cnt--;
				}
			}
			
		
				
	}
	
	
	
	void initialize(char[][] map){
		//player의 초기화
		p=new Player();
		p.setCol(2);
		p.setRow(4);
		initializeplayer();
		//Rock의 초기화
		for(int i=0;i<r.length;i++){
			r[i]= new Rock();
			r[i].setIcon('R');
		}
			r[0].setCol(5);r[0].setRow(3);
			r[1].setCol(9);r[1].setRow(1);
			r[2].setCol(9);r[2].setRow(2);
		//Food의 초기화!
		f[0]=new Food();f[0].setCol(4);f[0].setRow(1);f[0].setIcon('F');	
		f[1]=new Food();f[1].setCol(18);f[1].setRow(5);f[1].setIcon('F');
		//Monster의 초기화!
		m=new Monster();
		m.setCol(18);m.setRow(3);m.damage=20;m.hp=50;m.max_hp=50;m.setIcon('M');
		//Weapon의 초기화!
		w=new Weapon();
		w.setCol(18);w.setRow(1);w.damage=30;w.setIcon('W');w.name="Short Sword";
		
			
		insertEntity(p);
		for(int i=0;i<r.length;i++)
			insertEntity(r[i]);
		insertEntity(f[0]);
		insertEntity(f[1]);
		f[0]=null;
		f[1]=null;
		insertEntity(m);
		insertEntity(w);
		
		for(int i=0; i<ar.size();i++){
			
			map[ar.get(i).getRow()][ar.get(i).getCol()]=ar.get(i).getIcon();
		}
		
		
		map2 = map;
		
		
		
		
	}
	private void showMap(){
		FileManager fm = new FileManager();
		
		
		
		Vector<String> v = fm.loadMap(Config.MAP_FILENAME);
		for(int i=0; i<v.size();i++){
			for(int j=0; j<v.get(i).length();j++){
				
				System.out.print(map2[i][j]);
				
			}
			System.out.println();
		}
	}
	private void showInfo(Player p){
		System.out.println("Player  Hp: "+p.hp+"/"+p.max_hp+"\t"+"Damage: "+p.damage);
		System.out.println("          "+p.getWeapon());
		System.out.println("          X: "+p.getCol()+"\t\tY: "+p.getRow());
		showMenu();
	}
	
	private void MonsterHunt(Entity e,Monster m){
		
		Player p = (Player)e;
		while(true){
			System.out.println();
			System.out.print(Config.MonsterMenu);
			m.inputCommand();
			if(m.did==1){
				m.decreaseHP(e.getAttackPower());
				System.out.println("Monster's HP: "+m.hp+"/"+m.max_hp);
				if(m.hp==0){
					deleteEntity(m);
								
					break;
				}
				else{
				System.out.println(Config.receiveAttack);
				e.decreaseHP(m.getAttackPower());
				m.did=0;
				}
				
				if(getEnd(e)){
					
					
					deleteEntity(e);
					showMap();
					
					showInfo(p);
					System.out.println("Lose!");
					System.exit(0);
				}
				showMap();
				showInfo(p);
			}
			else if(m.did==2){
				m.did=0;
			break;	
			}
			
			}
	}
	
	private void weaponget(Entity e, Weapon w){
		while(true){
		w.showMenu();
		w.inputCommand();
		Player p = (Player)e;
		if(w.did==1){
			p.damage=w.getAttackPower();
			p.setWeaponChange(w.name);
			deleteEntity(w);
			break;
		}
		else if(w.did==2)
		{
			w.did=0;
			break;
		}
		}
	}
	private void foodeat(Entity e, Food f){
		while(true){
		f.showMenu();
		f.inputCommand();
		if(f.did==1){
			
			System.out.print("HP increased: "+e.hp+ " -> ");
			e.increaseHP(30);
			System.out.println(e.hp+"HP");
			deleteEntity(f);
			f=null;
			break;
		}
		else if(f.did==2){
			f.did=0;
			break;
		}
		}
	}
	
	
	void moveEntity(char c, Entity e){
		
		
		
		
		
		try{
		
		if(c==Config.Left){
						
			if(map2[e.getRow()][e.getCol()-1]=='.'){
			map2[e.getRow()][e.getCol()]='.';
			map[e.getRow()][e.getCol()]=null;
			e.setCol(e.getCol()-1);
			map2[e.getRow()][e.getCol()]=e.getIcon();
			map[e.getRow()][e.getCol()]=e;
			}
			else if(map2[e.getRow()][e.getCol()-1]=='R'){
				while(true){
					
				if(map2[e.getRow()][e.getCol()-2]=='.'){
				Rock temp1 = (Rock)map[e.getRow()][e.getCol()-1];
				temp1.showMenu();
				temp1.direction=0;
				temp1.inputCommand();
				if(temp1.did==1){
					temp1.did=0;
					break;
				}
				}
				else{
					System.out.println(Config.bump);
					break;
				}
				}
			}
			else if(map2[e.getRow()][e.getCol()-1]=='F'){
				Food f = (Food)map[e.getRow()][e.getCol()-1];
				foodeat(e,f);
			}
			else if(map2[e.getRow()][e.getCol()-1]=='M'){
				Monster m = (Monster)map[e.getRow()][e.getCol()-1];
				MonsterHunt(e,m);
				
			}
			else if(map2[e.getRow()][e.getCol()-1]=='W'){
				Weapon w = (Weapon)map[e.getRow()][e.getCol()-1];
				weaponget(e,w);
			}
			else{
				System.out.println(Config.bump);
			}
			}
			
		
		
		
		else if(c==Config.Right){
						
			if(map2[e.getRow()][e.getCol()+1]=='.'){
			map2[e.getRow()][e.getCol()]='.';
			map[e.getRow()][e.getCol()]=null;
			e.setCol(e.getCol()+1);
			map2[e.getRow()][e.getCol()]=e.getIcon();
			map[e.getRow()][e.getCol()]=e;
			}
			else if(map2[e.getRow()][e.getCol()+1]=='R'){
				
				while(true){
				if(map2[e.getRow()][e.getCol()+2]=='.'){
				Rock temp2 = (Rock)map[e.getRow()][e.getCol()+1];
				temp2.showMenu();
				temp2.direction=1;
				temp2.inputCommand();
				if(temp2.did==1){
					temp2.did=0;
					break;
				}
				}
				else{
					System.out.println(Config.bump);
					break;
				}
				}
					
			}
			
			else if(map2[e.getRow()][e.getCol()+1]=='F'){
				Food f = (Food)map[e.getRow()][e.getCol()+1];
				foodeat(e,f);
			}
			else if(map2[e.getRow()][e.getCol()+1]=='M'){
				Monster m = (Monster)map[e.getRow()][e.getCol()+1];
				MonsterHunt(e,m);
				
			}
			
			else if(map2[e.getRow()][e.getCol()+1]=='W'){
				Weapon w = (Weapon)map[e.getRow()][e.getCol()+1];
				weaponget(e,w);
			}
			
			
			else{
				System.out.println(Config.bump);
			}	
		}
			
			
			
			
		else if(c==Config.UP){
						
			if(map2[e.getRow()-1][e.getCol()]=='.'){
			map2[e.getRow()][e.getCol()]='.';
			map[e.getRow()][e.getCol()]=null;
			
			e.setRow(e.getRow()-1);
			map2[e.getRow()][e.getCol()]=e.getIcon();
			map[e.getRow()][e.getCol()]=e;
			}
			else if(map2[e.getRow()-1][e.getCol()]=='R'){
				
			
				
				while(true){
				if(map2[e.getRow()-2][e.getCol()]=='.'){
				Rock temp3 = (Rock)map[e.getRow()-1][e.getCol()];
				temp3.showMenu();
				temp3.direction=2;
				temp3.inputCommand();
				if(temp3.did==1){
					temp3.did=0;
					break;
				}
				}
				
				else{
					System.out.println(Config.bump);
					break;
				}
				}
			}
			else if(map2[e.getRow()-1][e.getCol()]=='F'){
				Food f = (Food)map[e.getRow()-1][e.getCol()];
				foodeat(e,f);
			}
			
			else if(map2[e.getRow()-1][e.getCol()]=='M'){
				Monster m = (Monster)map[e.getRow()-1][e.getCol()];
				MonsterHunt(e,m);
				
			}
			else if(map2[e.getRow()-1][e.getCol()]=='W'){
				Weapon w = (Weapon)map[e.getRow()-1][e.getCol()];
				weaponget(e,w);
			}
			
			else{
				System.out.println(Config.bump);
			}
		}
			
			
			
			
		else if(c==Config.DOWN){
			
			if(map2[e.getRow()+1][e.getCol()]=='.'){
			map2[e.getRow()][e.getCol()]='.';
			map[e.getRow()][e.getCol()]=null;
			e.setRow(e.getRow()+1);
			map2[e.getRow()][e.getCol()]=e.getIcon();
			map[e.getRow()][e.getCol()]=e;
			}
			
			
			else if(map2[e.getRow()+1][e.getCol()]=='R'){
				while(true){
				if(map2[e.getRow()+2][e.getCol()]=='.'){
				Rock temp4 = (Rock)map[e.getRow()+1][e.getCol()];
				temp4.showMenu();
				temp4.direction=3;
				temp4.inputCommand();
				if(temp4.did==1){
					temp4.did=0;
					break;
				}
					
				}
				else{
					System.out.println(Config.bump);
					break;
				}
				}
			}
			else if(map2[e.getRow()+1][e.getCol()]=='F'){
				Food f = (Food)map[e.getRow()+1][e.getCol()];
				foodeat(e,f);
				
			}
			else if(map2[e.getRow()+1][e.getCol()]=='M'){
				Monster m = (Monster)map[e.getRow()+1][e.getCol()];
				MonsterHunt(e,m);
				
			}
			else if(map2[e.getRow()+1][e.getCol()]=='W'){
				Weapon w = (Weapon)map[e.getRow()+1][e.getCol()];
				weaponget(e,w);
			}
			else{
				System.out.println(Config.bump);
			}
			
		}
			
			
			
			
		}
		catch(ArrayIndexOutOfBoundsException ee){
			ee.printStackTrace();
			
		}
		
	}
	private void showMenu(){
		System.out.print(Config.mainMenu);
	}
	private boolean getEnd(Entity e){ //게임 진행종료 상태의 T,F
		if(e.hp==0)
			return true;
		else
			return false;
	}
}



















