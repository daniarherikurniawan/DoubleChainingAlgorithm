package commonOperation;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class analysisOperation {

	private  fileOperation fileOp = new fileOperation();
	
	public  ArrayList<Integer> frequencyAnalysis(String file1) {
		ArrayList<Integer> data1 = fileOp.readFile(file1);
		int numbOfSameChar = 0;
		ArrayList<Integer> arrayFrequency = new ArrayList();
		for (int i = 0; i < 256; i++) {
			arrayFrequency.add(0);
		}
		for (int i = 0; i < data1.size(); i++) {
			arrayFrequency.set(data1.get(i), arrayFrequency.get(data1.get(i))+1);
		}
		return arrayFrequency;
	}
	
	public  void compareTwoFile(String file1, String file2) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(5);
		
		ArrayList<Integer> data1 = fileOp.readFile(file1);
		ArrayList<Integer> data2 = fileOp.readFile(file2);
		int numbOfSameChar = 0;
		for (int i = 0; i < data2.size(); i++) {
			if(data1.get(i).compareTo(data2.get(i))== 0){
				numbOfSameChar++;
			}
		}

		double tingkatKesamaan = numbOfSameChar/(data1.size()*1.0)*100;
		double avalencheScore = (data1.size() - numbOfSameChar)*1.0/data1.size();
		
		System.out.println("Avalenche score :"
		+df.format(avalencheScore)+" % ");
		
		System.out.println("The degree of similarity :"
		+df.format(tingkatKesamaan)+" %\n");
	}

}
