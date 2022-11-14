package application;

import entities.Product;
import services.CalculationService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);
        List<Product> products = new ArrayList<>();

        System.out.print("Informe a quantidade de itens: ");
        int itens = sc.nextInt();

        System.out.println("Entre com as informações do produto (nome e preço):");
        for (int i = 1; i <= itens; i++) {
            System.out.print("Nome do item #" + i + ": ");
            sc.nextLine();
            String name = sc.nextLine();
            System.out.print("Preço unitário: ");
            double price = sc.nextDouble();

            products.add(new Product(name, price));
        }

        String strPath = "c:\\temp";
        boolean success = new File(strPath + "\\in").mkdir();
        String pathIn = "c:\\temp\\in\\order.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathIn, true))){
            for (Product product : products) {
                bw.write(String.valueOf(product));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        success = new File(strPath + "\\out").mkdir();
        String pathOut = "c:\\temp\\out\\summary.csv";

        List<Product> resumo = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathIn))) {
            String line = br.readLine();
            while (line != null) {
                String[] fields = line.split(",");

                resumo.add(new Product(fields[0], Double.parseDouble(fields[1])));
                line = br.readLine();
            }
            Product x = CalculationService.max(resumo);
            System.out.println("Most expensive:");
            System.out.println(x);
        } catch (IOException e){
            System.out.println("Error: " + e.getMessage());
        }
        sc.close();
    }
}
