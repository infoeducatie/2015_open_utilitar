import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;


public class UtilitarOpenEvaluator {
	
	static final int N = 500;
	
	static final String FILE_NAME = "solutieTriviala.txt";

	public static void main(String[] args) {
		double[][] correctAnswer = readAnswer("answers.txt");
		double[][] evaluatedAnswer = readAnswer(FILE_NAME);
		double positiveScore = 0;
		double negativeScore = 0;
		for (int i = 1; i <= N; ++i) {
			for (int j = i + 1; j <= N; ++j) {
				if (evaluatedAnswer[i][j] > 0) {
					if (correctAnswer[i][j] > 0) {
						positiveScore += correctAnswer[i][j];
					}
					if (correctAnswer[i][j] < 0) {
						negativeScore += correctAnswer[i][j];
					}
				}
			}
		}
		System.out.println("Evaluarea rezultatului pentru: " + FILE_NAME);
		System.out.println("Scorul pozitiv: " + positiveScore);
		System.out.println("Scorul negativ: " + negativeScore);
	}

	private static double[][] readAnswer(String fileName) {
		double[][] costs = new double[1 + N][1 + N];
		try {
			@SuppressWarnings("unchecked")
			Vector<Integer>[] answer = new Vector[1 + N * N];
			for(int i = 0; i <= N * N; ++i) {
				answer[i] = new Vector<Integer>();
			}
			Scanner scanner = new Scanner(new File(fileName));
			int category = 0;
			while (scanner.hasNextLine()) {
				category++;
				String line = scanner.nextLine();
				String[] pages = line.split(" ");
				for(int i = 0; i < pages.length; ++i) {
					int link = new Integer(pages[i]);
					if (1 <= link && link <= N) {
						answer[category].addElement(link);
					}
				}
			}
			scanner.close();
			
			int nonLinks = N * (N - 1) / 2;
			category = 1;
			while (answer[category].size() > 0) {
				int size = answer[category].size();
				double weight = 1.0 / ((size * (size - 1)) / 2);
				for (Integer line1 : answer[category]) {
					for (Integer line2 : answer[category]) {
						if (line1 < line2) {
							costs[line1][line2] = weight;
							nonLinks--;
						}
					}
				}
				category++;
			}
			int i, j;
			for (i = 1; i <= N; ++i) {
				for (j = i + 1; j <= N; ++j) {
					if (costs[i][j] == 0.0) {
						costs[i][j] = -1.0 / nonLinks;
					} else {
						costs[i][j] /= category;
					}
					//System.out.print(costs[i][j] + " ");
				}
				//System.out.println();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return costs;
	}

}
