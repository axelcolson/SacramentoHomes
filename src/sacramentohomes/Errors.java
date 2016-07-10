package sacramentohomes;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Errors {

	private static BufferedWriter writer = null;
	private static PrintStream ps = null;
	
	public static void Error(Exception ex, String filename){
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:SS");
		Date date = new Date();
		try {
			writer = new BufferedWriter(new FileWriter(filename + ".log"));
			writer.write("------- ERROR ----------" + dateFormat.format(date).toString() + "-------------");
			writer.newLine();
			writer.write("---------- MESSAGE -----------");
			writer.newLine();
			writer.write(ex.getMessage());
			writer.newLine();
			writer.write("--------- STACKTRACE ------------");
			writer.newLine();
			writer.close();
			ps = new PrintStream(new FileOutputStream(filename + ".log", true));
			ex.printStackTrace(ps);
			ps.close();
		} catch (Exception ex2) {
			ex2.printStackTrace();
			System.out.println("Something went very wrong..");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ps != null) {
				ps.close();
			}
		}
	}

}
