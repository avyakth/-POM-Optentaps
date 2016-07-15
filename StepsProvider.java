

public class StepsProvider {

	public static String generateSteps(String SheetName, String Step_No, String TC_Name, String Element_Name, String Element_Property, String Element_Property_Value, String Action, String Element_Input_Value, String ChkElement_Property, String ChkElement_Property_Value, String Report, String ActionIfNotFound, String ErrorType)
	{
		String code = "";
		 code = code + "System.out.println(\"Step_No\""+Step_No+");\n";
		
		  String xPath = null;
		  String[] PropArray, PropValArray;
		  if(Element_Property.toUpperCase().contentEquals("XPATH"))
		  {
			  xPath = Element_Property_Value;
		  }
		  else
		  {
			  if(Element_Property.contains(",,"))
			  {
				  PropArray = Element_Property.split(",,");
				  PropValArray = Element_Property_Value.split(",,");
				  xPath = "//*[";
				  for(int i = 0; i < PropArray.length; i++)
				  {
					  if(i>0)
						  xPath = xPath + " and ";
					  xPath = xPath + "@"+PropArray[i] + "='" + PropValArray[i] + "'";
				  }
				  xPath = xPath + "]";
			  }
			  else
				  xPath = "//*[@"+Element_Property+"='"+Element_Property_Value+"']";
		  }
		  
		 
		  switch(Action.toUpperCase())
		  {
		case "SOAP":
			code = code + "soap(code,Element_Property_Value);\n";
		break;
	    case "SOAP_VERIFY":
	    	code = code + "soap_verify(code, ChkElement_Property_Value );\n";
	    break;
	  	case "MOVETO":
	  		code = code + "moveTo(code, xPath);\n";
	  	break;
	  	case "MINIMIZE_WINDOW":
	  		code = code + "driver.manage().window().setPosition(new Point(-2000, 0));\n";
		break;
	  	case "LAUNCH_URL":
	  		code = code + "driver.get("+Element_Input_Value+");\n";
	  		code = code + "driver.manage().window().maximize();\n";
	  		code = code + "new WebDriverWait(driver, 30);\n";
			break;
		case "DRAGANDDROP":
			code = code + "dragAndDrop(code, xPath, Element_Input_Value, Action);\n";
		break;
	  	case "SET": case "SETIFEXIST":
	  		code = code + "set(code, xPath, Element_Input_Value, Action);\n";
		break;
	  	case "CLICK": case "CLICKIFEXIST":
	  		code = code + "click(code, xPath, Action);\n";
	  	break;
	  	case "DRAW":
	  		code = code + "g.drawString("+Element_Input_Value+", 15, 15);\n";
		break;
		case "SUBMIT": case "SUBMITIFEXIST":
			code = code + "submit(code, xPath, Action);\n";
		break;
	  	case "DBLCLICK": case "DBLCLICKIFEXIST":
	  		code = code + "dblClick(code, xPath, Action, Element_Name, Element_Property, Element_Property_Value);\n";
	  	break;
	  	case "SELECT": case "INDEX": case "SELECTIFEXIST": case "INDEXIFEXIST":
	  		code = code + "select(code, xPath, Action, Element_Input_Value, Element_Name, Element_Property, Element_Property_Value, Step_No);\n";
	  	break;
	  	case "FRAMECHANGE": case "FRAMECHANGEIFEXIST":
	  		code = code + "frameChange(code, Element_Property_Value, Action);\n";
  		break;
	  	case "CHKBOX": case "CHKBOXIFEXIST":
	  		code = code + "checkBox(code, xPath, Action, Element_Input_Value);\n";
  		break;
	  	case "VERIFY": case "VERIFYIFEXIST":
	  		code = code + "verify(code, ChkElement_Property, ChkElement_Property_Value, Action, Element_Input_Value, xPath);\n";
		break;
  		case "CHKEXIST":
  			code = code + "chkExist(code, xPath);\n";
  		break;
	  	case "ACCEPT": case "ACCEPTIFEXIST":
	  		code = code + "accept(code, Action);\n";
	  	break;
	  	case "STOREVARIABLE": case "STOREVARIABLEIFEXIST":
	  		code = code + "storeVariable(code, xPath, Action, Element_Property, Element_Property_Value, Element_Input_Value, ChkElement_Property, ChkElement_Property_Value);\n";
		break;
	  	case "VERIFYSTOREVARIABLE": case "VERIFYSTOREVARIABLEIFEXIST":
	  		code = code + "verifyStoreVariable(code, ChkElement_Property_Value, Element_Input_Value, Action);\n";
		break;
	  	case "SCRNCAPTURE": case "SCREENCAPTURE":
	  		code = code + "screencapture(SheetName, Application_Name);\n";
	  	break;
	  	case "ERRORCHECK":
	  		code = code + "errorchk_Filepath = \"src/selectedApp-Error Sheet.xls\";\n";
	  		code = code + "errorchecker(errorchk_Filepath, "+SheetName+", driver);\n";
	  	break;
	  	case "WAIT":
	  		code = code + "wait = Integer.parseInt("+Element_Input_Value+")*1000;\n";
	  		code = code + "Thread.sleep(wait);\n";
        break;
	  	case "SCROLL":
	  		code = code + "scroll(code, Element_Input_Value);\n";  		
	  		break;
	  	case "TYPE":
	  		code = code + "inpvalue = Element_Input_Value.toUpperCase();\n";			        				
	  		code = code + "wordtype(inpvalue);\n";	
	  		break;
	  	case "PRESS":
	  		code = code + "presskey = "+Element_Input_Value.toUpperCase()+"\";\n";			        				
	  		code = code + "keyboard(presskey);\n";
		break;
	  	case "MOUSE":
	  		code = code + "mouseclick("+Element_Input_Value+", "+xPath+", driver);\n";
	  	break;
		case "SWITCHNEWWINDOW":
			code = code + "switchNewWindow(code);\n";
		break;
		case "SWITCHOLDWINDOW":
			code = code + "driver.switchTo().window(ParentWindow);\n";
		break;
		  }
		return code ;
	}
	
	
}
