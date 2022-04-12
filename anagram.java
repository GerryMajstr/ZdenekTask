import java.util.ArrayList;
import java.util.List;

public class anagram {
    public static void main(String[] args) throws Exception {
        String a = "bored";
        String b = "robed";
        List<String> lettersOfFirstString = new ArrayList<String>();
        List<String> lettersOfSecondString = new ArrayList<String>();

        if (a.length() != b.length()){
            throw new Exception("Strings are not anagram");
        }

        for (int i = 0; i < a.length(); i++){
            lettersOfFirstString.add(Character.toString(a.charAt(i)));
            lettersOfSecondString.add(Character.toString(b.charAt(i)));
        }

         for (String letter : lettersOfFirstString){
             if (lettersOfSecondString.contains(letter)) {
                 lettersOfSecondString.remove(letter);
                 System.out.println("Letter " + letter + " found in both Strings");
             } else {
                throw new Exception("Strings are not anagram");
             }
         }

        if (lettersOfSecondString.size() == 0){
            System.out.println("Strings are anagram");
        } else {
            throw new Exception("Strings are not anagram");
        }
    }
}
