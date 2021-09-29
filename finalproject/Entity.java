package finalproject;

public abstract class Entity extends WorldMap{
	int hp;
	int max_hp;
	int damage;
	private int row;
	private int col;
	static int cnt=0;
	private char icon;
	
	Entity(){
		cnt++;
	}
	
	public abstract void inputCommand(); //명령을 입력받는 함수.
	
	void setRow(int row){
		this.row=row;
	}
	void setCol(int col){
		this.col=col;
	}
	int getRow(){
		return row;
	}
	int getCol(){
		return col;	
	}
	int getHP(){
		return hp;
	}
	int getAttackPower(){
		return damage;
	}
	void increaseHP(int amount){
		if((hp+amount)<=max_hp)
			hp+=amount;
		else if((hp+amount)>max_hp)
			hp=max_hp;
			
	}
	void decreaseHP(int amount){
		if((hp-amount)>0)
			hp-=amount;
		else if((hp-amount)<=0)
			hp=0;
	}
	char getIcon(){
		return icon;
	}
	void setIcon(char c){
		icon=c;
	}
	void showMenu(){//객체의 선택매뉴 출력.. 무기앞에서 무기로 이동했을때..?
		
	}
}
class Weapon extends Entity{
	int did=0;
	String name;
	
	@Override
	public void inputCommand() {
		String command = Config.scan.next();
		if(command.charAt(0)==Config.get || command.charAt(0)==Config.Get){
			System.out.println(Config.afterGet);
			did=1;
		}
		else if(command.charAt(0)==Config.nothing || command.charAt(0)==Config.Nothing){
			did=2;
		}
	}
	
	@Override
	void showMenu(){
		System.out.print(Config.WeaponMenu);
	}
	
	
}

class Monster extends Entity{
	int did=0;
	
	@Override
	public void inputCommand() {
		String command = Config.scan.next();
		if(command.charAt(0)==Config.Attack || command.charAt(0)==Config.attack ){
			System.out.println(Config.afterAttack);
				did=1;
		}
		else if(command.charAt(0)==Config.Leave || command.charAt(0)==Config.leave){
				did=2;
		}
	}
	@Override
	void showMenu(){
		System.out.print(Config.MonsterMenu);
	}
	
	
}

class Food extends Entity{
	
	int did=0;
	
	
	@Override
	public void inputCommand() {
		String command = Config.scan.next();
		if(command.charAt(0)==Config.Eat || command.charAt(0)==Config.eat){
			System.out.println(Config.afterEat);
			did=1;
		}
		else if(command.charAt(0)==Config.Nothing||command.charAt(0)==Config.nothing){
			did=2;
		}
	}
	@Override
	void showMenu(){
		System.out.print(Config.FoodMenu);
	}
	
	
}

class Rock extends Entity{
	int direction;
	int did=0;
	
	@Override
	public void inputCommand() {
		String command = Config.scan.next();
		if(command.charAt(0)==Config.Push||command.charAt(0)==Config.push){
			
			if(direction==0){
				moveEntity(Config.Left,this);
			}
			else if(direction==1){
				moveEntity(Config.Right,this);
			}
			else if(direction==2){
				moveEntity(Config.UP,this);
			}
			else if(direction==3){
				moveEntity(Config.DOWN,this);
			}
		}
		else if(command.charAt(0)==Config.Nothing||command.charAt(0)==Config.nothing){
			did=1;
		}
		
	}
	@Override
	void showMenu(){
		System.out.print(Config.RockMenu);
	}
	
	
	
}


class Player extends Entity{
	
	private String weapon = "Bare hands";
	
	public void inputCommand() {
		
		String command = Config.scan.next();
		if(command.charAt(0)==Config.Left || command.charAt(0)==Config.left){ //move left
			moveEntity(Config.Left,this);
			
		}
		else if(command.charAt(0) ==Config.Right||command.charAt(0) ==Config.right){//move right
			moveEntity(Config.Right,this);
		}
		else if(command.charAt(0)==Config.UP||command.charAt(0)==Config.up){//move up
			moveEntity(Config.UP,this);
		}
		else if(command.charAt(0)==Config.DOWN||command.charAt(0)==Config.down){//move down
			moveEntity(Config.DOWN,this);
		}
		else if(command.charAt(0)=='Q'||command.charAt(0)=='q'){
			int count=0;
			
			for(int i=0;i<Config.mapRow;i++){
				for(int j=0;j<Config.mapCol;j++){
					if(map2[i][j]=='M')
						count++;		
				}
			}
			if(count==0){
				System.out.println("Victory!");
				System.exit(0);
			}
			else{
				System.out.println("Fail..");
				System.exit(0);
			}
		}
	}
	
	void setWeaponChange(String f){
		weapon = f;
	}
	String getWeapon(){
		return weapon;
	}
}
