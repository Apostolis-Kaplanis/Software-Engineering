package latex.model;

import java.io.Serializable;

public class Document implements Serializable{
	private static final long serialVersionUID = 1L;

	private String author;
	private String date;
	private String copyright;
	private String versionID;
	private String contents;

	public Document(String author, String date, String copyright, String versionID, String contents){
		this.author = author;
		this.date = date;
		this.copyright = copyright;
		this.versionID = versionID;
		this.contents = contents;
	}

	public void setContents(String contents){
		this.contents = contents;
	}

	public String getContents(){
		return contents;
	}

	public void setVersionID(String newVersionsID){
		versionID = newVersionsID;
	}

	public String getVersionID(){
		return versionID;
	}

	public void save(){

	}

	public Document cloneDeep(){
		return new Document(
				new String(this.author), new String(this.date), new String(this.copyright), new String(this.versionID), new String(this.contents)
				);
	}

}
