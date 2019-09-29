package latex.model;

import java.util.HashMap;

import latex.controller.FileManager;

public class DocumentManager {
	private HashMap <String, Document> templMap = new HashMap <String, Document>();
	private static String EMPTY		= "empty";
	private static String REPORT	= "report";
	private static String BOOK		= "book";
	private static String ARTICLE	= "article";
	private static String LETTER	= "letter";
	private FileManager reader = new FileManager();

	public DocumentManager() {
		mappingTemplates();
	}

	private void mappingTemplates() {
		String contents = null;

		contents = "";
		templMap.put(EMPTY,
				new Document("author", "date", "copyright", "versionID 0", contents)
				);

		contents = reader.fileReader("src/latex/recources/templates/article-template.tex");
		templMap.put(ARTICLE,
				new Document("author", "date", "copyright", "versionID 0", contents)
				);

		contents = reader.fileReader("src/latex/recources/templates/book-template.tex");
		templMap.put(BOOK,
				new Document("author", "date", "copyright", "versionID 0", contents)
				);

		contents = reader.fileReader("src/latex/recources/templates/letter-template.tex");
		templMap.put(LETTER,
				new Document("author", "date", "copyright", "versionID 0", contents)
				);

		contents = reader.fileReader("src/latex/recources/templates/report-template.tex");
		templMap.put(REPORT,
				new Document("author", "date", "copyright", "versionID 0", contents)
				);
	}

	/* Retrieves a Document
	  from the Map and clones it by calling the respective clone() method.*/
	public Document createDocument(String templateID){
		if(templMap.containsKey(templateID)){
			return templMap.get(templateID).cloneDeep();
		}
		else{
			System.out.println("(DocumentManager): Document template wasn't found in the map.");
			return null;
		}
	}
}
