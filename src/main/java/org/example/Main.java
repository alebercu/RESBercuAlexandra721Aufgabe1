package org.example;

import org.example.Kampf;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        String tsvFilePath = "/Users/alexandrabercu/IdeaProjects/RESbercualexandra721/marvel_konfrontationen.tsv"; // Ensure this file exists

        List<Kampf> games = readTsv(tsvFilePath);
        System.out.println("Total games found: " + games.size());

        // Display games with capacity >= 70000
        int minCapacity = 500;
        System.out.println("\n >= " + minCapacity + ":");
        displayKampfByMinGlobalerEinfluss(games, minCapacity);

        System.out.println(getGalaktischKampfbyDate(games));

        writeKampfByKategorie(games,"/Users/alexandrabercu/IdeaProjects/RESbercualexandra721/bericht_konfrontationen.txt");

    }

    public static List<Kampf> readTsv(String filePath) {
        List<Kampf> games = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Skip the first line (headers)
            for (int i = 1; i < lines.size(); i++) {
                String[] fields = lines.get(i).split("\t");

                if (fields.length < 6) {
                    System.err.println("Skipping invalid row: " + lines.get(i));
                    continue;
                }

                try {
                    int id = Integer.parseInt(fields[0]); // Id
                    String Held = fields[1];             // Team1
                    String Antagonist = fields[2];             // Team2
                    Konfrontationstyp typ = Konfrontationstyp.valueOf(fields[3]);             // Datum
                    String ort = fields[4];
                    LocalDate datum = LocalDate.parse(fields[5]);
                    double GlobalerEinfluss = Double.parseDouble(fields[6]);// Kapazität

                    games.add(new Kampf(id, Held, Antagonist,typ, ort, datum, GlobalerEinfluss));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format in row: " + lines.get(i));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading TSV file: " + e.getMessage());
        }
        return games;
    }

    public static void displayKampfByMinGlobalerEinfluss(List<Kampf> games, int minCapacity) {
        games.stream()
                .filter(g -> g.getGlobalerEinfluss() >= minCapacity)
                .forEach(System.out::println);
    }

    public static List<Kampf> getGalaktischKampfbyDate(List<Kampf> games) {
        String Typ = Konfrontationstyp.valueOf("Galaktisch").toString();
        return (List<Kampf>) games.stream()
                .filter(game -> game.getTyp().toString().equals(Typ))
                .collect(Collectors.toList());

    }

    public static void writeKampfByKategorie(List<Kampf> games, String outputFilePath) {
        // Count games per city
        Map<Konfrontationstyp, Long> cityGameCount = games.stream()
                .collect(Collectors.groupingBy(Kampf::getTyp, Collectors.counting()));

        // Sort: first by count (descending), then alphabetically by city name
        List<Map.Entry<Konfrontationstyp, Long>> sortedCities = cityGameCount.entrySet().stream()
                .sorted((Comparator<? super Map.Entry<Konfrontationstyp, Long>>) Comparator.comparing(Map.Entry<Konfrontationstyp, Long>::getValue).reversed()
                                .thenComparing(Map.Entry::getKey)).toList();


        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            for (Map.Entry<Konfrontationstyp, Long> entry : sortedCities) {
                writer.write(entry.getKey() + "%" + entry.getValue());
                writer.newLine();
            }
            System.out.println("written to " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }


}

