import java.util.*;

public class Main {
    static Map<String, String> codeList = new LinkedHashMap<>();
    public static void main(String[] args) {
        Map<String, Double> sourceMap = new LinkedHashMap<>();
//        sourceMap.put("a", 0.17);
//        sourceMap.put("b", 0.21);
//        sourceMap.put("c", 0.17);
//        sourceMap.put("e", 0.17);
//        sourceMap.put("d", 0.15);
//        sourceMap.put("f", 0.06);
//        sourceMap.put("g", 0.07);

//        sourceMap.put("а", 0.31);
//        sourceMap.put("б", 0.006);
//        sourceMap.put("в", 0.15);
//        sourceMap.put("г", 0.1);
//        sourceMap.put("д", 0.12);
//        sourceMap.put("е", 0.02);
//        sourceMap.put("є", 0.03);
//        sourceMap.put("ж", 0.03);
//        sourceMap.put("з", 0.104);
//        sourceMap.put("й", 0.13);

//        sourceMap.put("ab", 0.045);
//        sourceMap.put("ac", 0.045);
//        sourceMap.put("aa", 0.81);
//        sourceMap.put("ba", 0.045);
//        sourceMap.put("bc", 0.0025);
//        sourceMap.put("bb", 0.0025);
//        sourceMap.put("ca", 0.045);
//        sourceMap.put("cb", 0.0025);
//        sourceMap.put("cc", 0.0025);

        sourceMap.put("aaa", 0.729);
        sourceMap.put("aab", 0.0405);
        sourceMap.put("aac", 0.0405);
        sourceMap.put("aba", 0.0405);
        sourceMap.put("abb", 0.00225);
        sourceMap.put("abc", 0.00225);
        sourceMap.put("aca", 0.0405);
        sourceMap.put("acb", 0.00225);
        sourceMap.put("acc", 0.00225);
        sourceMap.put("baa", 0.0405);
        sourceMap.put("bab", 0.00225);
        sourceMap.put("bac", 0.00225);
        sourceMap.put("bba", 0.00225);
        sourceMap.put("bbb", 0.000125);
        sourceMap.put("bbc", 0.000125);
        sourceMap.put("bca", 0.00225);
        sourceMap.put("bcb", 0.000125);
        sourceMap.put("bcc", 0.000125);
        sourceMap.put("caa", 0.0405);
        sourceMap.put("cab", 0.00225);
        sourceMap.put("cac", 0.00225);
        sourceMap.put("cba", 0.00225);
        sourceMap.put("cbb", 0.000125);
        sourceMap.put("cbc", 0.000125);
        sourceMap.put("cca", 0.00225);
        sourceMap.put("ccb", 0.000125);
        sourceMap.put("ccc", 0.000125);
        List<Map.Entry<String, Double>> sortedSourceList = sortMapByValue(sourceMap);

        List<List<Map.Entry<String, Double>>> allStep = new LinkedList<>();
        for(int i = 0; i < sourceMap.size() - 2; i++) {
            if( i == 0) {
                allStep.add(algoHuffman(sortedSourceList));
                continue;
            }
            allStep.add(algoHuffman(allStep.get(i-1)));
        }
        List<List<Map.Entry<String, String>>> resList = new LinkedList<>();
        for(int i = allStep.size()-1; i >= 0; --i) {
            if(i == allStep.size()-1) {
                String codeFirst = allStep.get(i).get(0).getKey();
                codeList.put(codeFirst, "1");
                String codeSecond = allStep.get(i).get(1).getKey();
                codeList.put(codeSecond, "0");
                continue;
            }
            createCodeFromLists(allStep.get(i), allStep.get(i+1));
        }
        createCodeFromLists(sortedSourceList, allStep.get(0));
        for(Map.Entry<String, String> entry : codeList.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        double lSer = 0;
        double lMin = 0;
        for(int i = 0; i < sortedSourceList.size(); i++) {
            Map.Entry<String, Double> tmpEntry = sortedSourceList.get(i);
            lSer += tmpEntry.getValue() * codeList.get(tmpEntry.getKey()).length();
            lMin += tmpEntry.getValue() * (Math.log(tmpEntry.getValue()) / Math.log(2));
        }
        lMin *= -1;
        System.out.println("Середня кодова довжина: " + lSer);
        System.out.println("Мінімальна кодова довжина: " + lMin);
    }

    public static List<Map.Entry<String, Double>> algoHuffman(List<Map.Entry<String, Double>> entryList) {
        int mapLen = entryList.size();
        List<Map.Entry<String, Double>> list = new LinkedList<>(entryList);
        list = list.subList(0, list.size()-2);
        String key = entryList.get(mapLen-1).getKey().concat(entryList.get(mapLen-2).getKey());
        Double value = entryList.get(mapLen-1).getValue() + (entryList.get(mapLen-2).getValue());
        list.add(Map.entry(key, value));
        list = sortListByValue(list);
        return list;
    }

    public static void createCodeFromLists(List<Map.Entry<String, Double>> list1, List<Map.Entry<String, Double>> list2) {
        for(int i = 0; i < list1.size(); i++) {
            if(list2.contains(list1.get(i))) {
                list2.remove(list1.get(i));
                continue;
            }
            String newCodeFirst = codeList.get(list2.get(0).getKey()).concat("1");
            codeList.put(list1.get(i).getKey(), newCodeFirst);
            String newCodeSecond = codeList.get(list2.get(0).getKey()).concat("0");
            i++;
            codeList.put(list1.get(i).getKey(), newCodeSecond);
            codeList.remove(list2.get(0).getKey());
        }
    }

    public static List<Map.Entry<String, Double>> sortMapByValue(Map<String, Double> unsortedMap) {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortedMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        return list;
    }

    public static List<Map.Entry<String, Double>> sortListByValue(List<Map.Entry<String, Double>> list) {
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {

            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });
        return list;
    }
}