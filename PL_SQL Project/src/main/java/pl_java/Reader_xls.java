package pl_java;
import org.apache.log4j.Logger;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Reader_xls {
	/* le chemin de fichier sql*/
	private String path;
	


	public  Reader_xls(String path) {
		this.path=path;
		
		
	}
	
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public XSSFSheet sheet_obj() throws IOException{
		/* j'utilise FileInputStream pour obtenir path file*/
		FileInputStream fis=new FileInputStream(new File(getPath()));  
		/* on implémente l’interface HSSFWorkbook pour les fichier excel de format xls*/
		
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		/* on utilise HSSFSheet object pour lire et ecrire dans un fichier excel*/
		XSSFSheet sheet=wb.getSheetAt(0);
		return sheet;
				
	}
  public FormulaEvaluator evaluating()  throws IOException{
	  /* j'utilise FileInputStream pour obtenir path file*/
		FileInputStream fis=new FileInputStream(new File(getPath()));
		/* on implémente l’interface XSSFWorkbook pour les fichier excel de n'importe quelle format xls*/
		XSSFWorkbook wb=new XSSFWorkbook(fis); 
		/* on utilise XSSFSheet object pour lire et ecrire dans un fichier excel*/
		 FormulaEvaluator formulaEvaluator=wb.getCreationHelper().createFormulaEvaluator();
		 /* FormulaEvaluator nous permet d'evaluer la valeur de chaque cellule */
		 return formulaEvaluator;
	
}
  public  ArrayList<String> date_filiere() {
	  ArrayList<String> list1 = new ArrayList<String>();
	  try {
	  int firstRow =this.sheet_obj().getFirstRowNum();
	  Row row = this.sheet_obj().getRow(firstRow); //iteration over cell using for each loop  
		  Cell cell1 = row.getCell(0);
		  Cell cell2=row.getCell(1);
		  String[] strSplit = cell2.getStringCellValue().split("");
		  ArrayList<String> strList = new ArrayList<String>(
				    Arrays.asList(strSplit));
		  int a=strList.size()-1;
		  String date=strList.get(a);
		  String date_complete=cell2.getStringCellValue();
		  String nom_filier=cell1.getStringCellValue();
		  
		  list1.add(nom_filier);
		  list1.add(date_complete);
		  list1.add(date);
		 
	      
	
  } catch (IOException e) {
		 
	  // Print the exception
	  System.out.print(e.getMessage());}
	 return  list1;

	} 
//  public String nb_date() {
//	  String[] strSplit = this.date_acc.split("");
//
//	//   conversion string into ArrayList
//	ArrayList<String> strList = new ArrayList<String>(
//	    Arrays.asList(strSplit));
//	int a=strList.size()-1;
//	return strList.get(a);
//  }
  public void MOD_ELEM( String path_file) {
	  try {
		  ArrayList<String> stringList = this.date_filiere();
		  
			 

          FileWriter fWriter = new FileWriter(
              path_file,true);
          /* le parameter true (boolean) permet d'ajouter un contenu sur le contenu existé sur le fichier sql*/

int nb_ann=0;
int lastRow =this.sheet_obj().getLastRowNum();

for (int index = 1 ; index <= lastRow; index++) {
    Row row = this.sheet_obj().getRow(index);    
  int n_sm=0; int nb_mod=0;
for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {   //iteration over cell using for each loop  
	  Cell cell = row.getCell(cellIndex);
this.evaluating().evaluateInCell(cell).getCellType();
// pour evaluer le type de chaque cellule //

if ( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ) {
	 n_sm=(int)cell.getNumericCellValue();
	 
	 if(n_sm%2==1 && n_sm<6) {
		nb_ann++;
		String text1="---"+nb_ann+"ére Année\n\n";
		 String text2="insert into Mod_Elem_"+stringList.get(0)+" VALUES ('HI"+stringList.get(0)+String.valueOf(nb_ann)+"0"+stringList.get(2)+"','ENH','AN' ,'','VET_ELEM_NOM','VET_ELEM_NOM','','"+stringList.get(1)+"')\n\n";
		 fWriter.write(text1);
		 fWriter.write(text2);
		
		 
	 }
	 String text="--S"+String.valueOf(n_sm)+"\n\n";
	 fWriter.write(text);

	String text3="insert into Mod_Elem_"+stringList.get(0)+" VALUES ('HI"+stringList.get(0)+String.valueOf(n_sm)+"00"+stringList.get(2)+"','ENH','SM0"+String.valueOf(n_sm)+"','S"+String.valueOf(n_sm)+"','semestre"+String.valueOf(n_sm)+"','semestre"+String.valueOf(n_sm)+"','','"+stringList.get(1)+"')\n";
	 fWriter.write(text3);
}
if (cell.getCellType()==Cell.CELL_TYPE_STRING) {
	nb_mod++;
	String text4="insert into Mod_Elem_"+stringList.get(0)+" VALUES ('HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(nb_mod)+"0"+stringList.get(2)+"','ENH','MOD"+nb_mod+"','S"+n_sm+"','"+cell.getStringCellValue()+"','"+cell.getStringCellValue()+"','','"+stringList.get(1)+"')\n";
	 fWriter.write(text4);
	String word = "/";
	 String sentence=cell.getStringCellValue();
	String temp[] = sentence.split(" ");
	int count=0;
	for (int i = 0; i < temp.length; i++) {
		if (word.equals(temp[i]))
		count++;
		}
	if(count!=0) {
		int nb_elem=0;
		for(int i=1 ; i<=count+1;i++) {
			String text5="insert into Mod_Elem_"+stringList.get(0)+" VALUES ('HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(nb_mod)+String.valueOf(i)+stringList.get(2)+"','ENH','ELEM','S"+String.valueOf(n_sm)+"','"+temp[nb_elem]+"','"+temp[nb_elem]+"','','"+stringList.get(1)+"')\n";
			 fWriter.write(text5);
			nb_elem+=2; 
			 
		}
	}

} 
}
} fWriter.close();
System.out.println(
       "MOD_ELEM_ID is created successfully with the content.\n");
} catch (IOException e) {
	 
  // Print the exception
  System.out.print(e.getMessage());
} 
  }
  public void List_Mod_Elm(String path_file) {
	
	  try {
		  ArrayList<String> stringList = this.date_filiere();
		  
	  
	  int nb_semestre=1;
	  FileWriter fWriter = new FileWriter(
              path_file,true);
	  int lastRow =this.sheet_obj().getLastRowNum();
	  for (int index = 1 ; index <= lastRow; index++) {
		    Row row = this.sheet_obj().getRow(index);     //iteration over row using for each loop  
		  int s=1; int n_sm=0;
		  for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {   //iteration over cell using for each loop  
			  Cell cell = row.getCell(cellIndex); 
		this.evaluating().evaluateInCell(cell).getCellType();
		if ( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ) {
			 n_sm=(int)cell.getNumericCellValue();
			 if(n_sm%2==1 && nb_semestre<4) {
				 String text1="insert into List_Mod_Elm_"+stringList.get(0)+" VALUES('HI"+stringList.get(0)+String.valueOf(nb_semestre)+stringList.get(2)+"' ,'VET "+
			 String.valueOf(nb_semestre)+"année "+stringList.get(0)+"','VET "+
					 String.valueOf(nb_semestre)+"année "+stringList.get(0)+"')\n";
				
				 String text2="insert into List_Mod_Elm_"+stringList.get(0)+" VALUES('HI"+stringList.get(0)+String.valueOf(nb_semestre)+"0"+stringList.get(2)+"','VET_ELEM_NOM','VET_ELEM_NOM')\n";
				 fWriter.write(text1);
				 fWriter.write(text2);
				 nb_semestre++;
				 
			 }
	
			String text="insert into List_Mod_Elm_"+stringList.get(0)+" VALUES('HI"+stringList.get(0)+String.valueOf(n_sm)+"00"+stringList.get(2)+"','semestre"+String.valueOf(n_sm)+"','semestre"+String.valueOf(n_sm)+"')\n";
			 fWriter.write(text);
			
	} 
		if (cell.getCellType()==Cell.CELL_TYPE_STRING) { 
			String text="insert into List_Mod_Elm_"+stringList.get(0)+" VALUES('HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(s)+"0"+stringList.get(2)+"','"+cell.getStringCellValue()+"','"+cell.getStringCellValue()+"')\n\n\n";
			 fWriter.write(text);
			 s++;
  } 	
		}
	
		
		
	}
		fWriter.close();
		System.out.println(
			       "LIST_MOD_ELEM is created successfully with the content.\n");
		}  catch (IOException e) {
			 
			  // Print the exception
			  System.out.print(e.getMessage());
			}}
  
  public void Trait_Notes (String path_file) {
	 
	  try {
		  
		  ArrayList<String> stringList = this.date_filiere();
	  int nb_semestre=1;
	  FileWriter fWriter = new FileWriter(
              path_file,true);
	  int lastRow =this.sheet_obj().getLastRowNum();
	  for (int index = 1 ; index <= lastRow; index++) {
		    Row row = this.sheet_obj().getRow(index); 
		  int s=0; int n_sm=0;
		System.out.println("\n");
		  for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {   //iteration over cell using for each loop  
			  Cell cell = row.getCell(cellIndex);   
		this.evaluating().evaluateInCell(cell).getCellType(); 
		
			if ( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ) {
				 n_sm=(int)cell.getNumericCellValue();
				 if(n_sm%2==1 && n_sm<6) {
					 String text1="insert into Trait_Notes_Id values ('HI"+stringList.get(0)+String.valueOf(nb_semestre)+"0"+stringList.get(2)+"','HTN','T')\n";
					 fWriter.write(text1);
					 
					 nb_semestre++;
					 
				 }
				String text="insert into Trait_Notes_Id values ('HI"+stringList.get(0)+String.valueOf(n_sm)+"00"+stringList.get(2)+"','HTN','T')\n";
				 fWriter.write(text);
		} 
			if (cell.getCellType()==Cell.CELL_TYPE_STRING) { 
				String text1="insert into Trait_Notes_Id values ('HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(s)+"0"+stringList.get(2)+"','HTN','T')\n";
				 fWriter.write(text1);
				
//				
				String word = "/";
				 String sentence=cell.getStringCellValue();
				String temp[] = sentence.split(" ");
				int count=0;
				for (int i = 0; i < temp.length; i++) {
					if (word.equals(temp[i]))
					count++;
					}
				if(count!=0) {
					for(int i=1 ; i<=count+1;i++) {
						String text="insert into Trait_Notes_Id values ('HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(s)+String.valueOf(i)+stringList.get(2)+"','HTN','T')\n\n\n";
						 fWriter.write(text);
					}
				}
		}
			s++;
			
			
		}}fWriter.close();
		System.out.println(
			       "TRait_ELEM_ID is created successfully with the content.\n");
	  
  } catch (IOException e) {
		 
	  // Print the exception
	  System.out.print(e.getMessage());
	}}
  
  
  
  public void Inscr_Pedag(String path_file) {
	
	  try {
		  
		  ArrayList<String> stringList = this.date_filiere();
	  int nb_semestre=1;
	  FileWriter fWriter = new FileWriter(
              path_file,true);
	  int lastRow =this.sheet_obj().getLastRowNum();
	  for (int index = 1 ; index <= lastRow; index++) {
		    Row row = this.sheet_obj().getRow(index); 
	  int s=0; int n_sm=0;
	  for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {   //iteration over cell using for each loop  
		  Cell cell = row.getCell(cellIndex);   
		this.evaluating().evaluateInCell(cell).getCellType();
		if ( cell.getCellType()==Cell.CELL_TYPE_NUMERIC ) {
			 n_sm=(int)cell.getNumericCellValue();
			 if(n_sm%2==1 && n_sm<6) {
				
				 String text2="insert into Inscr_Pedag_"+stringList.get(0)+" values ('ENH','HI"+stringList.get(0)+String.valueOf(nb_semestre)+"0"+stringList.get(2)+"')\n";
				 fWriter.write(text2);
				 nb_semestre++;
				 
			 }
	
			String text="insert into Inscr_Pedag_"+stringList.get(0)+" values ('ENH','HI"+stringList.get(0)+String.valueOf(n_sm)+"00"+stringList.get(2)+"')\n";
			 fWriter.write(text);
			
	} 
		if (cell.getCellType()==Cell.CELL_TYPE_STRING) { 
			String text="insert into Inscr_Pedag_"+stringList.get(0)+" VALUES('ENH','HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(s)+"0"+stringList.get(2)+"')\n";
			 fWriter.write(text);

				String word = "/";
				 String sentence=cell.getStringCellValue();
				String temp[] = sentence.split(" ");
				int count=0;
				for (int i = 0; i < temp.length; i++) {
					if (word.equals(temp[i]))
					count++;
					}
				if(count!=0) {
					for(int i=1 ; i<=count+1;i++) {
						String text3="insert into Inscr_Pedag_"+stringList.get(0)+" values ('ENH','HI"+stringList.get(0)+String.valueOf(n_sm)+String.valueOf(s)+String.valueOf(i)+stringList.get(2)+"')\n\n\n";
						 fWriter.write(text3);
					}
				}
  }s++;
		}
	
		
		
	}
		fWriter.close();
		System.out.println(
			       "Inscr_Pedac_Id is created successfully with the content.");
		}  catch (IOException e) {
			 
			  // Print the exception
			  System.out.print(e.getMessage());
			}}
	

}
