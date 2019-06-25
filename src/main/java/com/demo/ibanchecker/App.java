package com.demo.ibanchecker;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App
{

    /**
     * 
     */
    private static final String ALLOWED_CHARACTERS_REGEX = "[0-9A-Z]+";

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" Would you like to input the IBAN (y/n): ");

        String decision = scanner.next().toUpperCase();
        if (decision.equals("Y"))
        {

            System.out.println("Enter the IBAN : ");

            String iban = scanner.next().replaceAll("\\s", "").toUpperCase();

            System.out.println(validateIBAN(iban.substring(0, 2)));
        }
        else
        {
            System.out.println("Enter the full path to file : ");
            String fileName = scanner.next();
            Stream<String> result;
            try (Stream<String> lines = Files.lines(Paths.get(fileName)))
            {
                result = lines.map(e -> e + ";" + String.valueOf(validateIBAN(e)));
                Files.write(Paths.get(fileName.replaceAll("\\.txt", ".out")), (Iterable<String>) result::iterator);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                main(null);
            }

        }

    }

    public static boolean validateIBAN(String iban)
    {

        if (iban.length() < 4 || !iban.matches(ALLOWED_CHARACTERS_REGEX) || validateCountry(iban))
        {
            return false;
        }

        iban = iban.substring(4) + iban.substring(0, 4);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iban.length(); i++)
            sb.append(Character.digit(iban.charAt(i), 36));

        BigInteger bigInt = new BigInteger(sb.toString());

        return bigInt.mod(BigInteger.valueOf(97)).intValue() == 1;
    }

    /**
     * @param iban
     * @return
     */
    private static boolean validateCountry(String iban)
    {
        return Stream.of(Locale.getISOCountries()).noneMatch(e -> e.equals(iban.substring(0, 2)));
    }

}
