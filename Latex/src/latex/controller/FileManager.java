package latex.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

import latex.model.Document;

public class FileManager {
	private StringBuilder strBuilder = new StringBuilder();

	public String loadFile(File file) throws Exception{
		Scanner input = new Scanner(file);

		//read text from file
		while(input.hasNext()){
			strBuilder.append(input.nextLine());
			strBuilder.append("\n");
		}
		input.close();

		return strBuilder.toString();
	}

	public void saveFile(Document document, File file) throws IOException{
		PrintWriter pw = new PrintWriter(file);

	    String content = document.getContents();

	    String [] lines = content.split("\n");
	    for(String line:lines){
	    	pw.write(line);
	    	pw.println();
	    }

	    pw.flush();
	    pw.close();
	}

	public String fileReader(String filePath){
		String contents = "";

		try{
			BufferedReader reader = new BufferedReader(
					new FileReader (filePath)
					);

			String line = null;
			while((line = reader.readLine()) != null){
				contents = contents.concat(line).concat("\n");
			}
			reader.close();
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return contents;
	}
}
