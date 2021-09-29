package finalproject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class FileManager {
	Vector<String> v=new Vector<String>();
	
	Vector<String> loadMap(String src) {//멥을 로드해서 v vector에 담아내는것.
		
		
		BufferedReader br = null;
		String str=new String();
		
		try {
		br = new BufferedReader(new FileReader(src),25);
		
		
	
			while(str!=null){
				
				str = br.readLine();
				if(str==null)
					break;
				v.add(str);
				
			
				
			}
			
			
			
			br.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return v;
	}

	char[][] buildMap() {
		
		v = loadMap(Config.MAP_FILENAME);
		char[][] build	= new char[v.size()][];

		for(int i=0; i<v.size();i++){
			build[i] = new char[v.get(i).length()];
			for(int j=0; j<v.get(i).length();j++){
				build[i][j]=v.get(i).charAt(j);
			}
		}
		
		return build;
		
	}

}



















