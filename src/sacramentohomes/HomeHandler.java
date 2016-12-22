package sacramentohomes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class HomeHandler extends Thread {

	public static void main(String[] args) throws IOException {
		Stream<String> lines = null;
		long aol = 0;
		BufferedReader reader = null;
		HashSet<Homes> homeSet = new HashSet<>();
		HomeHandler hand = new HomeHandler();

		try {
			Path p1 = Paths.get("Sacramentorealestatetransactions.csv"); // Opvragen van het pad van de csv file.
			lines = Files.lines(p1); // Lines initialiseren
			aol = lines.count(); // Aantal lijnen tellen.
			hand.readFunctie(aol, "Sacramentorealestatetransactions.csv", homeSet);
			System.out.println("Huizen met een waarde groter dan 200 000");

			Thread T200P = new Thread(new Runnable() {
				public void run() {
					try {
						HomeHandler.writeToFile("HuizenMetWaarde", hand.waardeBoventhd(homeSet));
					} catch (IOException e) {
						Errors.Error(e, "HuizenMetWaardeWriteException");
					}
				}
			});
			Thread T4PSK = new Thread(new Runnable() {
				public void run() {
					try {
						HomeHandler.writeToFile("huizen", hand.huizenMet4PlusSk(homeSet));
					} catch (IOException e) {
						Errors.Error(e, "HM4PSKWriteException");
						;
					}
				}
			});
			Thread TVPZ = new Thread(new Runnable() {
				public void run() {
					System.out.println("Huizen met 4 of meer slaapkamers");
					ArrayList<Integer> zips = hand.checkZip(homeSet);
					Map<Long, Long> vkpz = hand.verkochtePanden(homeSet, zips);
					try {
						HomeHandler.writeToFile("VerkochtPerZip", vkpz);
					} catch (IOException e) {
						Errors.Error(e, "VerkochtPerZipWriteException");
					}
				}
			});

			T200P.start(); // Threads moeten simultaan lopen, we gebruiken dus geen join() om te wachten tot de thread beëindigd is.
			T4PSK.start();
			TVPZ.start();
			
		} catch (Exception ex) {
			Errors.Error(ex, "ComplexError");
		} finally {
			if (lines != null) {
				lines.close();
			}
		}
	}

	private void readFunctie(long aantalloops, String bestand, HashSet<Homes> homes)
			throws IOException {
		BufferedReader reader = null;
		String[] home = new String[12]; // 12 items
		try {
			reader = new BufferedReader(new FileReader(bestand)); // Inlezen bestand
			String line = reader.readLine(); // Leest de eerste lijn in met CSV
												// Headers
			for (int i = 0; i < aantalloops - 1; i++) { // alle lijnen buiten de eerste -1
				line = reader.readLine(); // lees volgende lijn in
				System.out.println(line);
				home = line.split(","); // lijn splitten
				homes.add(new Homes(home[0], home[1], Integer.parseInt(home[2]), home[3], Integer.parseInt(home[4]),  // Object maken en in HashSet steken.
						Integer.parseInt(home[5]), Double.parseDouble(home[6]), home[7], home[8],
						Double.parseDouble(home[9]), Double.parseDouble(home[10]), Double.parseDouble(home[11]))); // parsen naar de juiste datatypes.
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	private ArrayList<Integer> checkZip(HashSet<Homes> homes) {
		ArrayList<Integer> list = new ArrayList<>(); // Zip codes checken in de Hashset en toevoegen aan een ArrayList indien deze er nog niet in zit
		for (Homes h : homes) {						// We doen dit om ervoor te zorgen dat we alle verschillende ZIP codes hebben om later het aantal verkochte huizen per zip code op te vragen.
			if (!list.contains(h.getZip())) {
				list.add(h.getZip());
			}
		}
		return list;
	}

	private Map<Long, Long> verkochtePanden(HashSet<Homes> homes, ArrayList<Integer> zips) {
		Map<Long, Long> vkpz = new HashMap<Long, Long>(); // Map maken
		for (Integer z : zips) {																	// Voor iedere zip code gaan we het aantal panden opvragen en opslaan in een Map
			long amount = homes.stream().filter(s -> s.getZip().equals(z)).count();
			System.out.println("Het aantal verkochte panden voor zipcode: " + z + " is " + amount);
			vkpz.put((long) z, amount); // We casten de integer naar een Long en slaan de values samen met hun key op in de map.
		}
		return vkpz;
	}

	private Stream<Homes> waardeBoventhd(HashSet<Homes> homes) {
		return homes.stream().filter(s -> s.getPrice() > 200000); // return een stream om later makkelijk weg te kunnen schrijven naar een bestand.
	}

	private Stream<Homes> huizenMet4PlusSk(HashSet<Homes> homes) {
		return homes.stream().filter(s -> s.getBeds() >= 4); // return een stream om later makkelijk weg te kunnen schrijven naar een bestand
	}

	private static void writeToFile(String bestand, Stream<Homes> collection) throws IOException { // Wegschrijven van een stream naar een bestand.
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(bestand)); // initialiseren van de Writer
			Iterator<Homes> it = collection.iterator();	// iterator aanmaken die over de collectie zal gaan.
			while (it.hasNext()) {			// zolang er een volgende is
				writer.write(it.next().toString());	// neem de volgende waarde (dit is een Homes object) en we passen de .ToString() methode erop toe, dit schrijven we weg naar de file
				writer.newLine(); // we zetten een enter.
			}
		} catch (Exception ex) {
			Errors.Error(ex, "WriteError");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private static void writeToFile(String bestand, Map<Long, Long> map) throws IOException {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(bestand)); // initialiseren van de writer
			for (Entry<Long, Long> v : map.entrySet()) { // voor iedere entry in de map
				writer.write("Er zijn " + v.getValue() + " huizen verkocht in zip code: " + v.getKey()); // schrijf de key en value weg
				writer.newLine(); // zet enter.
			}
		} catch (Exception ex) {
			Errors.Error(ex, "WriteMapError");
		} finally {
			if (writer != null) {
				writer.close();
			}
		}

	}
}
