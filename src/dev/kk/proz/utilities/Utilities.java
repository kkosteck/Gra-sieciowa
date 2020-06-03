package dev.kk.proz.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utilities {

	public static String loadFileAsString(String path) {
		StringBuilder builder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line;
			while((line = br.readLine()) != null)
				builder.append(line + "\n");
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return builder.toString();
	}
	
	public static int parseInt(String number) {
		try {
			return Integer.parseInt(number);
			
		}catch(NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static enum Teams {
		NONE(0), RED(1), BLUE(2);
		
		private int teamId;
		
		private Teams(int teamId) {
			this.teamId = teamId;
		}
		
		public int getId() {
			return teamId;
		}
	}
	
}
