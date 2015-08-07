import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class UtilitarOpenInchidereTranzitiva {

	static final int N = 500;
	
	static final String INPUT_FILE_NAME = "input.txt";
	static final String OUTPUT_FILE_NAME = "output.txt";
	
	public static void main(String[] args) {
		try {
			Scanner scanner = new Scanner(new File(INPUT_FILE_NAME));
			boolean[][] costs = new boolean[1 + N][1 + N];
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				int i = new Integer(line.split(" ")[0]);
				int j = new Integer(line.split(" ")[1]);
				costs[i][j] = costs[j][i] = true;
			}
			scanner.close();
			
			for (int k = 1; k <= N; ++k) {
				for (int i = 1; i <= N; ++i) {
					for (int j = 1; j <= N; ++j) {
						if (costs[i][k] && costs[k][j]) {
							costs[i][j] = true;
						}
					}
				}
			}
			
			boolean[] visited = new boolean[1 + N];
			PrintWriter writer = new PrintWriter(OUTPUT_FILE_NAME);
			for (int i = 1; i <= N; ++i) {
				if (!visited[i]) {
					visited[i] = true;
					writer.print(i + " ");
					for (int j = i + 1; j <= N; ++j) {
						if (costs[i][j]) {
							visited[j] = true;
							writer.print(j + " ");
						}
					}
					writer.println();
				}
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
