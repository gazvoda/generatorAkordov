import java.io.*;
//import java.util.*;

public class Akordi {
    public static void main(String[] args) {
        if (args.length != 0) {
            String vhodnaDatoteka = args[0];
            String izhodnaDatoteka = args[0] + "_chords";

            String[] akordi1 = {"C", "D", "E", "F", "G", "A", "B"};
            // m - minor; m - maj; s - sus; d - dim;
            String[] pripone = {"", "b", "#", "m", "6", "7", "9", "+", "s", "d"};
            // najdaljsi akord: Ebmaj7.length = 6;

            File out = new File(izhodnaDatoteka);
            try {
                FileReader fr = new FileReader(vhodnaDatoteka);
                BufferedReader br = new BufferedReader(fr);
                FileWriter fw = new FileWriter(out.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);

                String vrstica = "";


                while ((vrstica = br.readLine()) != null) {
                    if (vrstica.trim().length() > 0) {
                        String[] ar = vrstica.trim().split("\\s+");
                        // pogledam prvo besedo v vrstici (dolzina najdaljsega akorda)
                        if (ar[0].length() <= 6 && ar[0].length() > 0) {
                            // ce prva crka prve besede != A normalno pregledam seznam akordov
                            if (ar[0].charAt(0) != 'A' && jeAkord(akordi1, pripone, ar[0])) {
                                // nasel vrstico z akordi
                                // izpisiK(ar);
                                for (int i = 0; i < ar.length; i++) {
                                    bw.write(ar[i] + " ");
                                }
                                bw.newLine();
                            }
                            // ce == A, moramo preveriti, da ni člen v angleščini
                            else {
                                // pregledam s seznamom akordov
                                if (jeAkord(akordi1, pripone, ar[0])) {
                                    // za vsak slucaj lahko pogledamo ce ne sledi akord,
                                    // bi morala biti naslednja beseda zapisana z malo
                                    if (ar.length > 1) {
                                        if (Character.isUpperCase(ar[1].charAt(0)) && jeAkord(akordi1, pripone, ar[1])) {
                                            // nasel vrstico z akordi, ki se zacne z A
                                            // izpisiK(ar);
                                            for (int i = 0; i < ar.length; i++) {
                                                bw.write(ar[i] + " ");
                                            }
                                            bw.newLine();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // break;
                }
                //System.out.println();
                //bw.newLine();

                br.close();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * metoda ki preveri prvi dve crki besede, ce se ujemata
     * s prvima dvema crkama vseh moznih kombinacij akordov
     */
    private static boolean jeAkord(String[] akordi1, String[] pripone, String akord) {
        for (int i = 0; i < akordi1.length; i++) {
            if (akord.length() == 1) {
                if (akord.equals(akordi1[i])) {
                    return true;
                }
            } else {
                for (int j = 0; j < pripone.length; j++) {
                    String test = akordi1[i] + pripone[j];
                    String akordTest = Character.toString(akord.charAt(0)) + Character.toString(akord.charAt(1));
                    if (akordTest.equals(test)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * pomozna metoda za izpis vrstice v konzolo
     */
    private static void izpisiK(String[] ar) {
        for (int i = 0; i < ar.length; i++) {
            System.out.print(ar[i] + ", ");
        }
        System.out.println();
    }
}
