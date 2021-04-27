import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class readfile1 {

	public static void main(String[] args) throws IOException
	{
		ArrayList<String> stopID = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("stop_times.txt"));
		String line;
		try {
			while((line=br.readLine())!=null){

				String[] columns = line.split(",");
				stopID.add(columns[1]);

			}

		} catch (Exception e) {
			br.close();
		}
		System.out.println(stopID.get(3)); //enter stopID to output the Arraylist of StopID, enter StopID.get(number) to get a specific stoID at the index

	}
}
