package pl_java;
import org.apache.log4j.Logger;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.FormulaEvaluator;  
import org.apache.poi.ss.usermodel.Row;
import java.io.FileWriter;
import java.io.IOException;


public class main {
	// on crée une instance de la classe Logger pour créer un Logger avec le nom de la classe de notre subsysteme//  
	private static Logger LOGGER = Logger.getLogger(Reader_xls.class);
	public static void main(String args[]) throws IOException  
	{  
// logger.debug nous permet de lancer un message durant chaque lancement des fonctions entre try et catch//
		LOGGER.debug("Creating Sql Tables");
		try {
		   /* la date et l'accronyme de tous les infos de filiere il faut les ajouter dans fichier excel */
			Reader_xls obj=new Reader_xls("C:\\Users\\aitma\\Downloads\\exemple_file.xlsx");
			/*LOGGER.debug(obj) nous permet de prendre  les informations qui peuvent être nécessaires au diagnostic des problèmes et 
			au dépannage ou lors de l’exécution de l’application dans l’environnement 
			de test afin de s’assurer que tout fonctionne correctement*/
			
			
			 
			 LOGGER.debug(obj);
			 
			XSSFSheet sheet=obj.sheet_obj();
			
			FormulaEvaluator formulaEvaluator=obj.evaluating(); 
			String path="C:\\Users\\aitma\\Desktop\\fichier_sql-04-01-23.txt";
			
			obj.MOD_ELEM(path);
			obj.List_Mod_Elm(path);
			obj.Trait_Notes(path);
			obj.Inscr_Pedag(path);
			
//			obj.List_Mod_Elm(path);
//	
//			obj.Trait_Notes(path);
//		
//			obj.Inscr_Pedag(path);
			
			
		
		}catch(Exception e) {
			
			//LOGGER>error nous permet de lancer le message erreur
			 LOGGER.error(e.getMessage(), e);
		}
		

		 }
}
