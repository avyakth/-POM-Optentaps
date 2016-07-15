import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;


public class test {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		test t = new test();
		t.test1();
		
	}
	
	public void test1(){
		try{
		/**String datasheetPath = "src/AppName-CXM_HL_Grid.xls";
		
		File  fis = new File(datasheetPath);
		Workbook wb = Workbook.getWorkbook(fis);
	    Sheet s = wb.getSheet("AppConfig");
	    Hashtable AppConfig = new Hashtable();
	  
	    AppConfig.put(s.getCell(0,0).getContents(), s.getCell(0,1).getContents());
        System.out.println(AppConfig.get(s.getCell(0,0).getContents()));
        
        AppConfig.put(s.getCell(1,0).getContents(), s.getCell(1,1).getContents());
        System.out.println(AppConfig.get(s.getCell(1,0).getContents()));
        
        AppConfig.put(s.getCell(2,0).getContents(), s.getCell(2,1).getContents());
        System.out.println(AppConfig.get(s.getCell(2,0).getContents()));
        
        AppConfig.put(s.getCell(3,0).getContents(), s.getCell(3,1).getContents());
        System.out.println(AppConfig.get(s.getCell(3,0).getContents()));
	    
        AppConfig.put(s.getCell(6,0).getContents(), s.getCell(6,1).getContents());
        System.out.println(AppConfig.get(s.getCell(6,0).getContents()));*/
			/**DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String[] date = dateFormat.format(cal.getTime()).split(" ");
			System.out.println(date[0]);*/
			/*File sourceFile   = new File("src/Hello.java");
			if(!sourceFile.exists())
			{
				sourceFile.createNewFile();
			}
		    FileWriter writer = new FileWriter(sourceFile);

		    writer.write(
		    		"import org.testng.annotations.Test; \n" +
		            "public class Hello{ \n" +
		            "@Test \n" +
		            " public void testdoit() { \n" +
		            "   System.out.println(\"Hello world\") ;\n" +
		            " }\n" +
		            "}"
		    );
		    writer.close();

		   JavaCompiler compiler    = ToolProvider.getSystemJavaCompiler();
		    StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		    fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File("bin")));
		    // Compile the file
		    compiler.getTask(null,fileManager,null,null,null,fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call();
		    fileManager.close();
		    
		  //get system compiler:
*/		    /** JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	 
	        // for compilation diagnostic message processing on compilation WARNING/ERROR
	        MyDiagnosticListener c = new MyDiagnosticListener();
	        StandardJavaFileManager fileManager = compiler.getStandardFileManager(c,
	                                                                              Locale.ENGLISH,
	                                                                              null);
	        //specify classes output folder
	        Iterable options = Arrays.asList("-d", classOutputFolder);
	        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,c, options, null,files);
	        Boolean result = task.call();
	        if (result == true)
	        {
	            System.out.println("Succeeded");
	        }*/
			
			/*DateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Calendar cal1 = Calendar.getInstance();
			String[] date1 = dateFormat1.format(cal1.getTime()).split(" ");
			date1[0] = date1[0].replaceAll("/", "-");
			System.out.println("Date1 "+date1[0]);*/
		    
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
}
