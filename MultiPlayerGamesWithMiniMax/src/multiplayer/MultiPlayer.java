package multiplayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MultiPlayer {
	static int c=0;
	static int maxcount=1;
	static int mind = Integer.MAX_VALUE;
	static int fastPlayer=0;
	static int repeatedStates = 0;
	static Set<String> set;
	static BufferedWriter bw = null;
	public static void main(String[] args) {
		
		try {
			 File file = new File("multiplayer.txt");
			  if (!file.exists()) {
			     file.createNewFile();
			  }
			  FileWriter fw = new FileWriter(file);
			  bw = new BufferedWriter(fw);
			  set = new LinkedHashSet<>();
			  gameDFS(0,0,0,3,3,0,3,3,0, 0, 0,0, -1);
			  System.out.println("\nExplore Set Size:"+set.size());
//			  for(String s: set) {
//				  bw.write("\n"+s);
//			  }
			  bw.write("\n Explore Set Size:"+set.size());
//			  bw.write("\nMaxCount:"+maxcount);
			  bw.write("\nFastest Player: " + fastPlayer + ", moves:"+mind);
			  bw.write("\nNo of repeated states: " + repeatedStates);

			  bw.close();
		} catch(Exception e) {
			System.err.println("@@Error: " + e);
		}
		
	}
	/* 
	 * The core logic of the game using Depth First Search
	 */
	public static void gameDFS(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2, int count, int father, int f1, int f2, int dir) throws IOException {
		//Player1's x,y : (a1,a2); Player2's x,y : (b1,b2); Player3's x,y : (c1,c2); Player4's x,y : (d1,d2)
		
		//Corner conditions to maintain coordinates between 0 to 3
		if(a1 <0 || a1 > 3 ||a2 <0 || a2 > 3 ||b1 <0 || b1 > 3 ||b2 <0 || b2 > 3 ||c1 <0 || c1 > 3 ||c2 <0 || c2 > 3 || d1 <0 || d1 > 3 ||d2 <0 || d2 > 3) {
			return;
		}
		if(checkSameCell(a1, a2, b1, b2, c1, c2, d1, d2)) return;
		String state = "{("+a1+","+a2+"),"+"("+b1+","+b2+"),"+"("+c1+","+c2+"),"+"("+d1+","+d2+")}";
		String repeated = "";
		if(set.contains(state)) {
			repeated = "REPEATED";
			repeatedStates++;
		}
		c++;
		output(a1, a2, b1, b2, c1, c2, d1, d2, count, father, f1, f2, dir, repeated);
		if(set.contains(state)) return;
		
		set.add(state);
		
		if(checkGoal(a1, a2, b1, b2, c1, c2, d1, d2)) {
			String out = "WINS [PLAYER "+ (getWinner(a1, a2, b1, b2, c1, c2, d1, d2) + 1) + "]";
			System.out.println("WINS [PLAYER "+ (getWinner(a1, a2, b1, b2, c1, c2, d1, d2) + 1) + "]");
			if(mind > count) {
				mind = count;
				fastPlayer = getWinner(a1, a2, b1, b2, c1, c2, d1, d2) + 1;
			}
			bw.write(out);
			return;
		}
		
		if(count % 4 == 0) {
			gameDFS(a1, a2+1,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 1);
			gameDFS(a1+1, a2,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 3);
			gameDFS(a1-1, a2,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 0);
			gameDFS(a1, a2-1,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 2);
		}
		if(count % 4 == 1) {
			gameDFS(a1, a2,b1+1,b2,c1,c2,d1,d2,count+1, 1, b1, b2, 3);
			gameDFS(a1, a2,b1,b2+1,c1,c2,d1,d2,count+1, 1, b1, b2, 1);
			gameDFS(a1, a2,b1-1,b2,c1,c2,d1,d2,count+1, 1, b1, b2, 0);
			gameDFS(a1, a2,b1,b2-1,c1,c2,d1,d2,count+1, 1, b1, b2, 2);
		}
		if(count % 4 == 2) {
			gameDFS(a1, a2,b1,b2,c1+1,c2,d1,d2,count+1, 2, c1, c2, 3);
			gameDFS(a1, a2,b1,b2,c1,c2+1,d1,d2,count+1, 2, c1, c2, 1);
			gameDFS(a1, a2,b1,b2,c1-1,c2,d1,d2,count+1, 2, c1, c2, 0);
			gameDFS(a1, a2,b1,b2,c1,c2-1,d1,d2,count+1, 2, c1, c2, 2);
		}
		if(count % 4 == 3) {
			gameDFS(a1, a2, b1, b2, c1, c2, d1+1, d2,count+1, 3, d1, d2, 3);
			gameDFS(a1, a2, b1, b2, c1, c2, d1, d2+1,count+1, 3, d1, d2, 1);
			gameDFS(a1, a2, b1, b2, c1, c2, d1-1, d2,count+1, 3, d1, d2, 0);
			gameDFS(a1, a2, b1, b2, c1, c2, d1, d2-1,count+1, 3, d1, d2, 2);
		}
		maxcount = Math.max(maxcount, count);
	}
	
	/* 
	 * Check if any two players are on the same cell
	 */
	public static boolean checkSameCell(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
		if((a1 == b1 && a2 == b2) || (a1 == c1 && a2 == c2) || (a1 == d1 && a2 == d2) || (b1 == c1 && b2 == c2) || (b1 == d1 && b2 == d2) || (c1 == d1 && c2 == d2)) {
			return true;
		}
		return false;
	}
	
	/* 
	 * Check if any player has reached goal state 
	 */
	public static boolean checkGoal(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
		if(a1 == 3 && a2 == 3 || b1 == 3 && b2 == 0 || c1 == 0 && c2 == 3 || d1 == 0  && d2 == 0) {
			return true;
		}
		return false;
	}
	
	/* 
	 * Returns the player that won 
	 */
	public static int getWinner(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2) {
		if(a1 == 3 && a2 == 3) {
			return 0;
		}
		if(b1 == 3 && b2 == 0) {
			return 1;
		}
		if(c1 == 0 && c2 == 3) {
			return 2;
		}
		return 3;
	}
	
	public static void output(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2, int count, int father, int f1, int f2, int dir, String repeated) throws IOException {
		System.out.println("");
		String out1 = "\nCurrent player = P"+(count%4 + 1);
		String out4 = " | Current Game Node = (" + a1 + "," +a2+"), (" + b1 + "," +b2+"), (" + c1 + "," +c2+"), (" + d1 + "," +d2+")" ;
//		System.out.print("\nCurrent player = P"+(count%4 + 1) +" | Current Game Node = (" + a1 + "," +a2+"), (" + b1 + "," +b2+"), (" + c1 + "," +c2+"), (" + d1 + "," +d2+")" );
		if(father == 0) {
			a1=f1; a2=f2;
		} else if(father == 1) {
			b1=f1; b2=f2;
		}  else if(father == 2) {
			c1=f1; c2=f2;
		}  else if(father == 3) {
			d1=f1; d2=f2;
		}
		String action="INITIAL NODE";
		if(dir == 1) {
			action = "RIGHT";
		} else if(dir == 3) {
			action = "DOWN";
		} else if(dir==2) {
			action = "LEFT";
		} else if (dir == 0) {
			action = "UP";
		}
		String out2 = "";
		String out3="";
		if(dir != -1) out2=" | Father Node = (" + a1 + "," +a2+"), (" + b1 + "," +b2+"), (" + c1 + "," +c2+"), (" + d1 + "," +d2+")";
		out3=" | Action = P" + (father+1) + " moved " + action;
		
//		if(dir != -1) System.out.print(" | Father Node = (" + a1 + "," +a2+"), (" + b1 + "," +b2+"), (" + c1 + "," +c2+"), (" + d1 + "," +d2+")");
		
//		System.out.print(" | Action = P" + (father+1) + " moved " + action + " | " + repeated);
		System.out.println(out1+out2+out3+out4 + " | " + repeated);
		bw.write(out1+out2+out3+out4 + " | " + repeated);
	}
	
}
