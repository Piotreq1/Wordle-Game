import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Wordle {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    //Metoda porównująca podane przez użytkownika słowo z szukanym wyrazem
    //Wypisuje podane słowo ze zmianą koloru znaków na odpowiednich pozycjach
    //Zwraca liczbę pokrywających się znaków
    private static int comparisonOfWords(String word, String inputWord) {
        int counter = 0;
        StringBuilder comparisonResult = new StringBuilder("");
        for (int i = 0; i < inputWord.length(); i++) {
            char c = inputWord.charAt(i);
            char d = word.charAt(i);
            if (c == d) {
                counter++;
                comparisonResult.append(ANSI_GREEN).append(c).append(ANSI_RESET);
            } else if (inWord(word, c) == 1) {
                comparisonResult.append(ANSI_YELLOW).append(c).append(ANSI_RESET);
            } else {
                comparisonResult.append(c);
            }
        }
        System.out.println(comparisonResult);
        return counter;
    }

    //Metoda szukająca znaku w odgadywanym słowie
    //Wartość zwracana:
    // 1 - znak znajduje się w słowie
    // 0 - znak nie znajduje się w słowie
    public static int inWord(String word, Character c) {
        for (int j = 0; j < word.length(); j++) {
            if (word.charAt(j) == c) return 1;
        }
        return 0;
    }

    //Metoda pobiera słowa z pliku, losuje słowo do odgadnięcia i zwraca je
    private static String getWord() throws IOException {
        File file = new File("words.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String tempWord;
        ArrayList<String> wordsList = new ArrayList<>();
        while ((tempWord = br.readLine()) != null) {
            wordsList.add(tempWord);
        }
        Random random = new Random();
        int i = random.nextInt(wordsList.size());
        //   System.out.println(words.get(i));
        return wordsList.get(i);
    }

    public static void main(String[] args) throws IOException {
        String word = getWord();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 6; i++) {
            System.out.println("_____");
            String inputWord = scanner.nextLine();
            while (inputWord.length() != word.length()) {
                System.out.println("Zła dlugosc slowa! Wprowadz poprawne słowo.");
                inputWord = scanner.nextLine();
            }
            if (comparisonOfWords(word, inputWord) == inputWord.length()) {
                System.out.println("Gratulacje! Odgadłes słowo za " + (i + 1) + " proba.");
                return;
            }
        }
        System.out.println("Niestety nie udało ci się odgadnąc słowa. Szukane słowo to " + word + ".");

    }
}
