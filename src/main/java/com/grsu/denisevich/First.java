package com.grsu.denisevich;

import jxl.Sheet;
import jxl.Workbook;

import java.io.File;
import java.util.*;

public class First {

    private static final String EXCEL_FILE_LOCATION = "D:\\Users\\Karina\\Desktop\\8sem\\IIfirst\\src\\main\\resources\\first.xls";

    public static void main(String[] args) {
        Workbook workbook = null;
        try {
            workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));
            Sheet sheet = workbook.getSheet(0);
            Map<String, ArrayList<Boolean>> map = getMap(sheet);
            Scanner scanner = new Scanner(System.in);

            for (int i = 1; i < sheet.getRows() + 1; i++) {
                System.out.println(sheet.getCell(i, 0).getContents());
                Boolean userInput = getUserInput(scanner);
                
                removeUnsuitableObjects(map, userInput, i);
                if (map.size() == 1) {
                    System.out.println("Your result is : " + map.keySet().iterator().next());
                    break;
                }
            }
            scanner.close();
            System.out.println("There is no result.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
    }

    public static Boolean getUserInput(Scanner scanner) {
        String input = scanner.nextLine();
        while (!(input.equals("true") || input.equals("false"))) {
            System.out.println("Input true or false");
            input = scanner.nextLine();
        }
        return Boolean.valueOf(input);
    }

    public static void removeUnsuitableObjects(Map<String, ArrayList<Boolean>> map,
                                        final Boolean userChoise, int index) {
        map.entrySet().removeIf(entry -> !Objects.equals(entry.getValue().get(index - 1), userChoise));
    }

    public static Map<String, ArrayList<Boolean>> getMap(Sheet sheet) {
        Map<String, ArrayList<Boolean>> map = new HashMap<>();

        for (int i = 1; i < sheet.getRows(); i++) {
            ArrayList<Boolean> attrs = new ArrayList<>();
            for (int j = 1; j < sheet.getColumns(); j++) {
                attrs.add(Boolean.valueOf(sheet.getCell(j, i).getContents()));
            }
            map.put(sheet.getCell(0, i).getContents(), attrs);
        }
        return map;
    }
}
