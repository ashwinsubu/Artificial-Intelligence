package multiplayer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MiniMaxMultiPlayer {
	static int c=0;
	static int maxcount=1;
	static Set<String> set;
	static BufferedWriter bw = null;
	public static void main(String[] args) {
		
		try {
			 File file = new File("minimax.txt");
			  if (!file.exists()) {
			     file.createNewFile();
			  }
			  FileWriter fw = new FileWriter(file);
			  bw = new BufferedWriter(fw);
			  set = new LinkedHashSet<>();
			  int ans[]=gameDFS(0,0,0,3,3,0,3,3,0, 0, 0,0, -1);
			  bw.write("\nExplored Set Size:"+set.size());
			  bw.close();
			  System.out.println("\n\nExplored Set Size:"+set.size());
			  System.out.println("\nFinal Minimax answer:"+ Arrays.toString(ans));
		} catch(Exception e) {
			System.err.println("@@Error: " + e);
		}
		
	}
	
	/* 
	 * The core logic of the game using Depth First Search. This returns Minimax
	 */
	public static int[] gameDFS(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2, int count, int father, int f1, int f2, int dir) throws IOException {
		//Player1's x,y : (a1,a2); Player2's x,y : (b1,b2); Player3's x,y : (c1,c2); Player4's x,y : (d1,d2)
		
		//Corner conditions to maintain coordinates between 0 to 3
		if(a1 <0 || a1 > 3 ||a2 <0 || a2 > 3 ||b1 <0 || b1 > 3 ||b2 <0 || b2 > 3 ||c1 <0 || c1 > 3 ||c2 <0 || c2 > 3 || d1 <0 || d1 > 3 ||d2 <0 || d2 > 3) {
			return new int[] {0,0,0,0};
		}
		if(checkSameCell(a1, a2, b1, b2, c1, c2, d1, d2)) return new int[] {0,0,0,0};
		String state = "{("+a1+","+a2+"),"+"("+b1+","+b2+"),"+"("+c1+","+c2+"),"+"("+d1+","+d2+")}";
		String repeated = "";
		if(set.contains(state)) {
			repeated = "REPEATED";
		}
		c++;
		output(a1, a2, b1, b2, c1, c2, d1, d2, count, father, f1, f2, dir, repeated);
		if(set.contains(state)) return new int[] {0,0,0,0};
		
		set.add(state);
		
		if(checkGoal(a1, a2, b1, b2, c1, c2, d1, d2)) {
			int winner = getWinner(a1, a2, b1, b2, c1, c2, d1, d2);
			String out = "WINS [PLAYER "+ (winner+1) + "]";
			System.out.println("WINS [PLAYER "+ (winner+1) + "]");
			bw.write(out);
			if(winner == 0) {
				//Based on Player1's utility
				return new int[]{200, 10, 30, 10};
			} else if(winner == 1) {
				//Based on Player2's utility
				return new int[]{100, 300, 150, 200};
			} else if(winner == 2) {
				//Based on Player3's utility
				return new int[]{150, 200, 400, 300};
			}
			//Based on Player4's utility
			return new int[]{220, 330, 440, 500};
		}
		// 4 minimax arrays for each direction and player
		int u1[]= new int[4];
		int u2[]= new int[4];
		int u3[]= new int[4];
		int u4[]= new int[4];
		int ans[] = new int[4];
		
		//Player1's turn
		if(count % 4 == 0) {
			u1= gameDFS(a1, a2+1,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 1);
			u2= gameDFS(a1+1, a2,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 3);
			u3= gameDFS(a1-1, a2,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 0);
			u4= gameDFS(a1, a2-1,b1,b2,c1,c2,d1,d2,count+1, 0, a1, a2, 2);
		}
		
		//Player2's turn
		if(count % 4 == 1) {
			u1=gameDFS(a1, a2,b1+1,b2,c1,c2,d1,d2,count+1, 1, b1, b2, 3);
			u2=gameDFS(a1, a2,b1,b2+1,c1,c2,d1,d2,count+1, 1, b1, b2, 1);
			u3=gameDFS(a1, a2,b1-1,b2,c1,c2,d1,d2,count+1, 1, b1, b2, 0);
			u4=gameDFS(a1, a2,b1,b2-1,c1,c2,d1,d2,count+1, 1, b1, b2, 2);
		}
		
		//Player3's turn
		if(count % 4 == 2) {
			u1=gameDFS(a1, a2,b1,b2,c1+1,c2,d1,d2,count+1, 2, c1, c2, 3);
			u2=gameDFS(a1, a2,b1,b2,c1,c2+1,d1,d2,count+1, 2, c1, c2, 1);
			u3=gameDFS(a1, a2,b1,b2,c1-1,c2,d1,d2,count+1, 2, c1, c2, 0);
			u4=gameDFS(a1, a2,b1,b2,c1,c2-1,d1,d2,count+1, 2, c1, c2, 2);
		}
		
		//Player4's turn
		if(count % 4 == 3) {
			u1=gameDFS(a1, a2, b1, b2, c1, c2, d1+1, d2,count+1, 3, d1, d2, 3);
			u2=gameDFS(a1, a2, b1, b2, c1, c2, d1, d2+1,count+1, 3, d1, d2, 1);
			u3=gameDFS(a1, a2, b1, b2, c1, c2, d1-1, d2,count+1, 3, d1, d2, 0);
			u4=gameDFS(a1, a2, b1, b2, c1, c2, d1, d2-1,count+1, 3, d1, d2, 2);
		}
		
		maxcount = Math.max(maxcount, count);
		
		//Return the best of the minimax arrays based on current player
		ans = u1;
		if(ans[count%4] < u2[count%4]) {
			ans=u2;
		}
		if(ans[count%4] < u3[count%4]) {
			ans=u3;
		}
		if(ans[count%4] < u4[count%4]) {
			ans=u4;
		}
		
		System.out.print(" MINIMAX = " + Arrays.toString(ans));
		bw.write(" MINIMAX = " + Arrays.toString(ans));
		
		return ans;
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
	
	/* 
	 * Displays output in prescribed format
	 */
	public static void output(int a1, int a2, int b1, int b2, int c1, int c2, int d1, int d2, int count, int father, int f1, int f2, int dir, String repeated) throws IOException {
		System.out.println("");
		String out1 = "\nCurrent player = P"+(count%4 + 1);
		String out4 = " | Current Game Node = (" + a1 + "," +a2+"), (" + b1 + "," +b2+"), (" + c1 + "," +c2+"), (" + d1 + "," +d2+")" ;
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
		System.out.print(out1+out2+out3+out4 + " | " + repeated+" | ");
		bw.write(out1+out2+out3+out4 + " | " + repeated + " | ");
	}
	
}
