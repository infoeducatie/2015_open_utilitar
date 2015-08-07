import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;


public class UtilitarOpenSolutieTriviala {
	
	public static final int N = 500;
	public static final double THRESHOLD = 0.65;
	public static final String LINKS_FILE_NAME = "links.txt";
	public static final String ANSWERS_FILE_NAME = "solutieTriviala.txt";
	
	public static double distance[][] = new double[1 + N][1 + N];
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		String[] links = new String[1 + N];
		
		Vector<Integer>[] answer = new Vector[N];
		for(int i = 0; i < N; ++i) {
			answer[i] = new Vector<Integer>();
		}
		for(int i = 0; i < N; ++i) {
			links[i] = "";
		}
		
		try {
			Scanner scanner = new Scanner(new File(LINKS_FILE_NAME));
			int currentDirectory = 0;
			while (scanner.hasNextLine()) {
				currentDirectory++;
				String line = scanner.nextLine();
				links[currentDirectory] = line.split("\\|")[2];
			}
			scanner.close();
			
			for (int i = 1; i <= N; ++i) {
				for (int j = i + 1; j <= N; ++j) {
					distance[j][i] = distance[i][j] = cmlscDif(links[i], links[j]);
					//System.out.println(links[i] + "   "+ links[j] + "   " + distance[i][j]);
				}
			}
			
			
			boolean[] isSolved = new boolean[1 + N];
			currentDirectory = 0;
			for (int i = 1; i <= N; ++i) {
				if (!isSolved[i]) {
					isSolved[i] = true;
					Cluster cluster = new Cluster(i);
					for (int j = i + 1; j <= N; ++j) {
						if (!isSolved[j]) {
							if (cluster.computeDistance(j) >= THRESHOLD) {
								cluster.addLink(j);
								isSolved[j] = true;
							}
						}
					}
					currentDirectory++;
					answer[currentDirectory] = cluster.links;
				}
			}
			
			PrintWriter writer = new PrintWriter(ANSWERS_FILE_NAME);
			currentDirectory = 1;
			while (answer[currentDirectory].size() > 0) {
				for (Integer line : answer[currentDirectory]) {
					writer.print(line + " ");
				}
				writer.println();
				currentDirectory++;
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static double cmlscDif(String A, String B) {
		int Asize = A.length();
		int Bsize = B.length();
		int matrix[][] = new int[1 + Asize][1 + Bsize];
		for (int a = 1; a <= Asize; ++a) {
			for (int b = 1; b <= Bsize; ++b) {
				matrix[a][b] = Math.max(matrix[a][b - 1], matrix[a - 1][b]);
				if (A.charAt(a - 1) == B.charAt(b - 1) && matrix[a][b] < matrix[a - 1][b - 1] + 1) {
					matrix[a][b] = matrix[a - 1][b - 1] + 1;
				}
			}
		}
		return (2.0 * matrix[Asize][Bsize]) / (Asize + Bsize);
	}

}
