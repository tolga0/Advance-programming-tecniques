
import com.lexicalscope.jewel.cli.CliFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        String regexForMention = "([@][\\w_]+)";
        String regexForHastag = "([#][\\w_]+)";

        Pattern patternForHastag = Pattern.compile(regexForHastag);
        Pattern patternForMention = Pattern.compile(regexForMention);

        CommandLine result = CliFactory.parseArguments(CommandLine.class, args);
        String entityName = result.getEntity();
        System.out.println("Entity is : " + entityName);

        int numberOfEntity = 0;
        numberOfEntity = result.getNumber();
        System.out.println("Number of entity is : " + numberOfEntity + "\n");

        boolean reversed = result.isReverse();
        System.out.println("Is reversed ? : " + reversed);

        boolean ignored = result.isIgnore();
        System.out.println("Is ignored : ? " + ignored);

        String fileName = result.fileName();
        System.out.println("filename is : " + fileName);



        if (entityName.toLowerCase().equals("mention")) {
            if (ignored) {
                if (reversed) {
                    printMapReversedOrder(putValuesToHasmapWithIgnored(fileName, patternForMention),numberOfEntity);
                } else {
                    printMapNotReversedOrder(putValuesToHasmapWithIgnored(fileName, patternForMention), numberOfEntity);
                }

            } else {

                if (reversed) {
                    printMapReversedOrder(putValuesToHasmapWithNotIgnored(fileName, patternForMention),numberOfEntity);
                } else {
                    printMapNotReversedOrder(putValuesToHasmapWithNotIgnored(fileName, patternForMention), numberOfEntity);
                }

            }

        } else if (entityName.toLowerCase().equals("hashtag")) {

            if (ignored) {
                if (reversed) {
                    printMapReversedOrder(putValuesToHasmapWithIgnored(fileName, patternForHastag),numberOfEntity);
                } else {
                    printMapNotReversedOrder(putValuesToHasmapWithIgnored(fileName, patternForHastag),numberOfEntity);
                }

            } else {

                if (reversed) {
                    printMapReversedOrder(putValuesToHasmapWithNotIgnored(fileName, patternForHastag),numberOfEntity);
                } else {
                    printMapNotReversedOrder(putValuesToHasmapWithNotIgnored(fileName, patternForHastag),numberOfEntity);
                }

            }

        } else {
            System.out.println("please give a correct argument; it can be \"mention\" or \"hashtag\"");
        }

    }

    public static HashMap<String, Integer> putValuesToHasmapWithNotIgnored(String fileName, Pattern pattern) {
        HashMap<String, Integer> resultHashMap = new HashMap<>();

        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (resultHashMap.containsKey(matcher.group())) {
                        int keyValue = resultHashMap.get(matcher.group());
                        keyValue++;
                        resultHashMap.put(matcher.group(), keyValue);
                    } else {
                        resultHashMap.put(matcher.group(), 1);
                    }
                }

            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return resultHashMap;
    }

    public static HashMap<String, Integer> putValuesToHasmapWithIgnored(String fileName, Pattern pattern) {
        HashMap<String, Integer> resultHashMap = new HashMap<>();

        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (resultHashMap.containsKey(matcher.group().toLowerCase())) {
                        int keyValue = resultHashMap.get(matcher.group().toLowerCase());
                        keyValue++;
                        resultHashMap.put(matcher.group().toLowerCase(), keyValue);
                    } else {
                        resultHashMap.put(matcher.group().toLowerCase(), 1);
                    }
                }

            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        return resultHashMap;
    }

    public static void printMapNotReversedOrder(Map map, int entityNumber) {

        Object[] b = map.entrySet().toArray();
        Arrays.sort(b, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        for (int i = 0; i < entityNumber; i++) {
            System.out.println(((Map.Entry<String, Integer>) b[i]).getKey() + "\t"
                    + (((Map.Entry<String, Integer>) b[i]).getValue()));
        }

    }

    public static void printMapReversedOrder(Map map, int entityNumber) {

        Object[] b = map.entrySet().toArray();
        Arrays.sort(b, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });

        for (int i = b.length-1; i > (b.length - 1 - entityNumber); i--) {
            System.out.println(((Map.Entry<String, Integer>) b[i]).getKey() + "\t"
                    + (((Map.Entry<String, Integer>) b[i]).getValue()));
        }

    }


}
