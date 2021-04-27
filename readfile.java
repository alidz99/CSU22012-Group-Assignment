import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class readfile {
	public static void main(String[] args) throws IOException
	{
		ArrayList<String> stop_name = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("stops.txt"));
		String line;
		try {
			while((line=br.readLine())!=null){

				String[] columns = line.split(",");
				stop_name.add(columns[2]);

			}
		} catch (Exception e) {
			br.close();
		}
		TST tst = new TST(stop_name);
		for(int i = 0; i<stop_name.size();i++) {
			String str = stop_name.get(i);
			String first = str.split(" ")[0];
			str = str.replace(first, "").trim();
			str = str + " " + first;
			System.out.println(str);
			tst.insert(str);
		}
		boolean isEmp = tst.isEmpty();
		if(isEmp == false) {
			System.out.println("not empty");
		}
		else {
			System.out.println("fuck");
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the stop name.");
		String input = sc.next();
		if(tst.search(input) == true) {
			System.out.println("The stop is found.");
		}
		else {
			System.out.println("The stop cannot be found.");
		}
		
	}
}