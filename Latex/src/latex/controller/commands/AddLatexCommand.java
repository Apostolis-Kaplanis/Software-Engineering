package latex.controller.commands;

import java.util.HashMap;

import latex.model.Document;
import latex.controller.commands.EditCommand;

public class AddLatexCommand implements Command {
	private static String EMPTY		= "empty";
	private static String REPORT	= "report";
	private static String BOOK		= "book";
	private static String ARTICLE	= "article";
//	private static String LETTER	= "letter";

	private HashMap<String, String[]> rulesForCommand;

	public AddLatexCommand() {
		rulesForCommand = new HashMap<String, String[]>();

		rulesForCommand.put("\\chapter{...}", new String[]{EMPTY, REPORT, BOOK});
		rulesForCommand.put("\\section{}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\subsection{}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\subsubsection{}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\begin{itemize}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\item ...", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\end{itemize}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\begin{enumerate}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\end{enumerate}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\begin{table}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\caption{....}\\label{...}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\begin{tabular}{|c|c|c|}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\hline", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("... &...&...\\\\", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\end{tabular}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\end{table}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\begin{figure}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\includegraphics[width=...,height=...]{...}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\caption{....}\\label{...}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
		rulesForCommand.put("\\end{figure}", new String[]{EMPTY, REPORT, BOOK, ARTICLE});
	}

	public Object execute(Object[] argumentsOfCommand) {
		String latexCommand = (String) argumentsOfCommand[0];
		String currentDocumentType = (String) argumentsOfCommand[1];
		Document doc = (Document) argumentsOfCommand[2];

		// Check if this latex-command is violating the rules:
		for(String str: rulesForCommand.get(latexCommand)){
			if(str.equals(currentDocumentType)){
				// Add latex-command in document's contents.
				EditCommand editCommand = new EditCommand();
				argumentsOfCommand[0] = doc;
				argumentsOfCommand[1] = doc.getContents();
				argumentsOfCommand[2] = latexCommand;

				doc = (Document) editCommand.execute(argumentsOfCommand);

				return doc;
			}
		}
		return null;
	}
}
