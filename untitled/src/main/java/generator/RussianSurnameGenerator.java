package generator;

import java.util.Random;

public class RussianSurnameGenerator {
    private static final char[] CONSONANTS = {'б','в','г','д','ж','з','к','л','м','н','п','р','с','т','ф','х','ц','ч','ш','щ'};
    private static final char[] VOWELS = {'а','е','ё','и','о','у','ы','э','ю','я'};
    private static final Random random = new Random();

    private static String generateSyllable() {
        char consonant = CONSONANTS[random.nextInt(CONSONANTS.length)];
        char vowel = VOWELS[random.nextInt(VOWELS.length)];
        return "" + consonant + vowel;
    }

    public static String generateSurname() {
        int syllableCount = 2 + random.nextInt(2); // 2 или 3 слога

        StringBuilder surname = new StringBuilder();
        for (int i = 0; i < syllableCount; i++) {
            surname.append(generateSyllable());
        }

        char lastConsonant = CONSONANTS[random.nextInt(CONSONANTS.length)];
        boolean maleSuffix = random.nextBoolean();

        if (maleSuffix) {
            surname.append(lastConsonant).append("ов");
        } else {
            surname.append(lastConsonant).append("ова");
        }

        surname.setCharAt(0, Character.toUpperCase(surname.charAt(0)));

        return surname.toString();
    }

}