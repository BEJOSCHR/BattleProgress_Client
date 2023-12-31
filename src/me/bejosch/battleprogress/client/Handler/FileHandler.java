package me.bejosch.battleprogress.client.Handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import me.bejosch.battleprogress.client.Data.FileData;

public class FileHandler {

//==========================================================================================================
	/**
	 * Fill first configs if empty
	 */
	public static void firstWrite() {
		
		if(!FileData.Ordner.exists()) {
			FileData.Ordner.mkdir();
		}
		if(readOutData(FileData.file_Settings, "Successfully") == null) {
			createNewXmlFile(FileData.file_Settings);
			saveDataInFile(FileData.file_Settings, "UserName", "");
			saveDataInFile(FileData.file_Settings, "ServerAdresse", "ipcwup.no-ip.biz");
			saveDataInFile(FileData.file_Settings, "Port", "8998");
		}
		if(readOutData(FileData.file_Maps, "Successfully") == null) {
			createNewXmlFile(FileData.file_Maps);
			saveDataInFile(FileData.file_Maps, "MapList", "[Standard]");
			saveStandardMap();
		}
		
	}
	
//==========================================================================================================
	/**
	 * Save data in a given XML File witch could later be read out by the key
	 * @param file - file - The file there the data should be saved
	 * @param key - String - The key later this data can be read out
	 * @param value - String the value btw. the data should be saved !!!If null the current data will be deleted!!!
	 * @return boolean - true if save worked, false if something goes wrong
	 */
	public static boolean saveDataInFile(File file, String key, String value) {
		
		Node testLoadedNote = readOutNote(file, key);
		
		try{ 
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.parse(file);
			
			if(value == null) {
				//DELETE MODE - REMOVE IT
				document.getDocumentElement().removeChild(document.getDocumentElement().getElementsByTagName(key).item(0));
			}else if(testLoadedNote != null) {
				//ALREADY SAVED - SO DELETE FOR THE NEW ONE
				document.getDocumentElement().removeChild(document.getDocumentElement().getElementsByTagName(key).item(0));
				//THEN SAVE THE NEW ONE
				Element data = document.createElement(key);
				data.appendChild(document.createTextNode(value));
				document.getDocumentElement().appendChild(data);
			}else {
				//JUST SAVE
				Element data = document.createElement(key);
				data.appendChild(document.createTextNode(value));
				document.getDocumentElement().appendChild(data);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(file);
			
			transformer.transform(source, streamResult);
			
			return true;
			
		}catch(ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
		}
			
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Load if possible a data witch is connected to the key
	 * @param file - file - The target file
	 * @param key - String - The key should be searched for
	 * @return String - The data found from the key or null if not
	 */
	public static String readOutData(File file, String key) {
		
		try {
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			
			NodeList nodeList = document.getElementsByTagName("Container");
			
			for(int i = 0 ; i < nodeList.getLength() ; i++) {
				
				Node node = nodeList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element element = (Element) node;
					String data = element.getElementsByTagName(key).item(0).getTextContent();
					
					return data;
					
				}
				
			}
		
		}catch(FileNotFoundException e) {
			//IGNORE - FILE WILL BE CREATED
			return null;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Load if possible a Node witch has the same file and key values
	 * @param file - file - The target file
	 * @param key - String - The key should be searched for
	 * @return Node - The node found from the key or null if not
	 */
	public static Node readOutNote(File file, String key) {
		
		try {
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			
			NodeList nodeList = document.getElementsByTagName("Container");
			
			for(int i = 0 ; i < nodeList.getLength() ; i++) {
				
				Node node = nodeList.item(i);
				
				if(node.getNodeType() == Node.ELEMENT_NODE && node != null) {
					
					Element element = (Element) node;
					
					if(element.getElementsByTagName(key).item(0) != null) {
						if(element.getElementsByTagName(key).item(0).getNodeName().equalsIgnoreCase(key)) {
							return element.getElementsByTagName(key).item(0);
						}
					}
					
				}
				
			}
		
		}catch(FileNotFoundException e) {
			//IGNORE - FILE WILL BE CREATED
			return null;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
//==========================================================================================================
	/**
	 * Create a XML File from a given File
	 * @param file - file - The file witch should be created
	 * @return boolean - true if create worked, false if something goes wrong
	 */
	public static boolean createNewXmlFile(File file) {
		
		try{ 
			
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			
			Document document = documentBuilder.newDocument();
			
			Element element = document.createElement("Container");
			document.appendChild(element);
			
			Element data = document.createElement("Successfully");
			data.appendChild(document.createTextNode("Created"));
			element.appendChild(data);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			
			StreamResult streamResult = new StreamResult(file);
			
			transformer.transform(source, streamResult);
			
		}catch(ParserConfigurationException | DOMException | TransformerException e) {
			e.printStackTrace();
		}
			
		return false;
		
	}
	
//==========================================================================================================
	/**
	 * Add the Standard map to the list
	 */
	public static void saveStandardMap() {
		
		String mapName = "Standard";
		CreateMapHandler.addMapNameToConfigList(mapName);
		saveDataInFile(FileData.file_Maps, mapName+"_Fields", "w:0:0-w:0:1-w:0:2-w:0:3-w:0:4-w:0:5-w:0:6-w:0:7-w:0:8-w:0:9-w:0:10-w:0:11-w:0:12-w:0:13-w:0:14-w:0:15-w:0:16-w:0:17-w:0:18-w:0:19-w:0:20-w:0:21-w:0:22-s:0:23-s:0:24-s:0:25-s:0:26-s:0:27-s:0:28-s:0:29-s:0:30-s:0:31-s:0:32-s:0:33-s:0:34-s:0:35-s:0:36-w:0:37-w:0:38-w:0:39-w:0:40-w:0:41-w:0:42-w:0:43-w:0:44-w:0:45-w:0:46-w:0:47-w:0:48-w:0:49-w:0:50-w:0:51-w:0:52-w:0:53-w:0:54-w:0:55-w:0:56-w:0:57-w:0:58-w:0:59-w:1:0-w:1:1-w:1:2-w:1:3-w:1:4-w:1:5-w:1:6-w:1:7-w:1:8-w:1:9-w:1:10-w:1:11-w:1:12-w:1:13-w:1:14-w:1:15-w:1:16-w:1:17-w:1:18-w:1:19-w:1:20-w:1:21-w:1:22-w:1:23-s:1:24-s:1:25-s:1:26-s:1:27-s:1:28-s:1:29-s:1:30-s:1:31-s:1:32-s:1:33-s:1:34-s:1:35-w:1:36-w:1:37-w:1:38-w:1:39-w:1:40-w:1:41-w:1:42-w:1:43-w:1:44-w:1:45-w:1:46-w:1:47-w:1:48-w:1:49-w:1:50-w:1:51-w:1:52-w:1:53-w:1:54-w:1:55-w:1:56-w:1:57-w:1:58-w:1:59-w:2:0-w:2:1-w:2:2-w:2:3-w:2:4-w:2:5-w:2:6-w:2:7-w:2:8-w:2:9-w:2:10-w:2:11-w:2:12-w:2:13-w:2:14-w:2:15-w:2:16-w:2:17-w:2:18-w:2:19-w:2:20-w:2:21-w:2:22-w:2:23-s:2:24-s:2:25-s:2:26-s:2:27-s:2:28-s:2:29-s:2:30-s:2:31-s:2:32-s:2:33-s:2:34-s:2:35-w:2:36-w:2:37-w:2:38-w:2:39-w:2:40-w:2:41-w:2:42-w:2:43-w:2:44-w:2:45-w:2:46-w:2:47-w:2:48-w:2:49-w:2:50-w:2:51-w:2:52-w:2:53-w:2:54-w:2:55-w:2:56-w:2:57-w:2:58-w:2:59-w:3:0-w:3:1-w:3:2-w:3:3-w:3:4-w:3:5-w:3:6-w:3:7-w:3:8-w:3:9-w:3:10-w:3:11-w:3:12-w:3:13-w:3:14-w:3:15-w:3:16-w:3:17-w:3:18-w:3:19-w:3:20-w:3:21-w:3:22-w:3:23-s:3:24-s:3:25-s:3:26-s:3:27-s:3:28-s:3:29-s:3:30-s:3:31-s:3:32-s:3:33-s:3:34-s:3:35-w:3:36-w:3:37-w:3:38-w:3:39-w:3:40-w:3:41-w:3:42-w:3:43-w:3:44-w:3:45-w:3:46-w:3:47-w:3:48-w:3:49-w:3:50-w:3:51-w:3:52-w:3:53-w:3:54-w:3:55-w:3:56-w:3:57-w:3:58-w:3:59-w:4:0-w:4:1-w:4:2-w:4:3-w:4:4-w:4:5-w:4:6-w:4:7-w:4:8-w:4:9-w:4:10-w:4:11-w:4:12-w:4:13-w:4:14-w:4:15-w:4:16-w:4:17-w:4:18-w:4:19-w:4:20-w:4:21-w:4:22-w:4:23-w:4:24-s:4:25-s:4:26-s:4:27-s:4:28-s:4:29-s:4:30-s:4:31-s:4:32-s:4:33-s:4:34-w:4:35-w:4:36-w:4:37-w:4:38-w:4:39-w:4:40-w:4:41-w:4:42-w:4:43-w:4:44-w:4:45-w:4:46-w:4:47-w:4:48-w:4:49-w:4:50-w:4:51-w:4:52-w:4:53-w:4:54-w:4:55-w:4:56-w:4:57-w:4:58-w:4:59-w:5:0-w:5:1-w:5:2-w:5:3-w:5:4-w:5:5-w:5:6-w:5:7-w:5:8-w:5:9-w:5:10-w:5:11-w:5:12-w:5:13-w:5:14-w:5:15-w:5:16-w:5:17-w:5:18-w:5:19-w:5:20-w:5:21-w:5:22-w:5:23-w:5:24-s:5:25-s:5:26-s:5:27-s:5:28-s:5:29-s:5:30-s:5:31-s:5:32-s:5:33-s:5:34-w:5:35-w:5:36-w:5:37-w:5:38-w:5:39-w:5:40-w:5:41-w:5:42-w:5:43-w:5:44-w:5:45-w:5:46-w:5:47-w:5:48-w:5:49-w:5:50-w:5:51-w:5:52-w:5:53-w:5:54-w:5:55-w:5:56-w:5:57-w:5:58-w:5:59-w:6:0-w:6:1-w:6:2-w:6:3-w:6:4-w:6:5-w:6:6-w:6:7-w:6:8-w:6:9-w:6:10-w:6:11-w:6:12-w:6:13-w:6:14-w:6:15-w:6:16-w:6:17-w:6:18-w:6:19-w:6:20-w:6:21-w:6:22-w:6:23-w:6:24-s:6:25-s:6:26-s:6:27-s:6:28-s:6:29-s:6:30-s:6:31-s:6:32-s:6:33-s:6:34-w:6:35-w:6:36-w:6:37-w:6:38-w:6:39-w:6:40-w:6:41-w:6:42-w:6:43-w:6:44-w:6:45-w:6:46-w:6:47-w:6:48-w:6:49-w:6:50-w:6:51-w:6:52-w:6:53-w:6:54-w:6:55-w:6:56-w:6:57-w:6:58-w:6:59-w:7:0-w:7:1-w:7:2-w:7:3-w:7:4-w:7:5-w:7:6-w:7:7-w:7:8-w:7:9-w:7:10-w:7:11-w:7:12-w:7:13-w:7:14-w:7:15-w:7:16-w:7:17-w:7:18-w:7:19-w:7:20-w:7:21-w:7:22-w:7:23-w:7:24-w:7:25-s:7:26-s:7:27-s:7:28-s:7:29-s:7:30-s:7:31-s:7:32-s:7:33-w:7:34-w:7:35-w:7:36-w:7:37-w:7:38-w:7:39-w:7:40-w:7:41-w:7:42-w:7:43-w:7:44-w:7:45-w:7:46-w:7:47-w:7:48-w:7:49-w:7:50-w:7:51-w:7:52-w:7:53-w:7:54-w:7:55-w:7:56-w:7:57-w:7:58-w:7:59-w:8:0-w:8:1-w:8:2-w:8:3-w:8:4-w:8:5-w:8:6-w:8:7-w:8:8-w:8:9-w:8:10-w:8:11-w:8:12-w:8:13-w:8:14-w:8:15-w:8:16-w:8:17-w:8:18-w:8:19-w:8:20-w:8:21-w:8:22-w:8:23-w:8:24-w:8:25-s:8:26-s:8:27-s:8:28-s:8:29-s:8:30-s:8:31-s:8:32-s:8:33-w:8:34-w:8:35-w:8:36-w:8:37-w:8:38-w:8:39-w:8:40-w:8:41-w:8:42-w:8:43-w:8:44-w:8:45-w:8:46-w:8:47-w:8:48-w:8:49-w:8:50-w:8:51-w:8:52-w:8:53-w:8:54-w:8:55-w:8:56-w:8:57-w:8:58-w:8:59-w:9:0-w:9:1-w:9:2-w:9:3-w:9:4-w:9:5-w:9:6-w:9:7-w:9:8-r:9:18-s:9:19-s:9:20-s:9:26-s:9:27-s:9:28-s:9:29-s:9:30-s:9:31-s:9:32-s:9:33-s:9:39-s:9:40-r:9:41-w:9:51-w:9:52-w:9:53-w:9:54-w:9:55-w:9:56-w:9:57-w:9:58-w:9:59-w:10:0-w:10:1-w:10:2-w:10:3-w:10:4-w:10:5-w:10:6-w:10:7-w:10:8-r:10:18-s:10:19-s:10:20-r:10:26-s:10:27-s:10:28-s:10:29-s:10:30-s:10:31-s:10:32-r:10:33-s:10:39-s:10:40-r:10:41-w:10:51-w:10:52-w:10:53-w:10:54-w:10:55-w:10:56-w:10:57-w:10:58-w:10:59-w:11:0-w:11:1-w:11:2-w:11:3-w:11:4-w:11:5-w:11:6-w:11:7-w:11:8-r:11:18-s:11:19-s:11:20-s:11:27-s:11:28-s:11:29-s:11:30-s:11:31-s:11:32-s:11:39-s:11:40-r:11:41-w:11:51-w:11:52-w:11:53-w:11:54-w:11:55-w:11:56-w:11:57-w:11:58-w:11:59-w:12:0-w:12:1-w:12:2-w:12:3-w:12:4-w:12:5-w:12:6-w:12:7-w:12:8-s:12:19-s:12:20-s:12:27-s:12:28-s:12:29-s:12:30-s:12:31-s:12:32-s:12:39-s:12:40-w:12:51-w:12:52-w:12:53-w:12:54-w:12:55-w:12:56-w:12:57-w:12:58-w:12:59-w:13:0-w:13:1-w:13:2-w:13:3-w:13:4-w:13:5-w:13:6-w:13:7-w:13:8-s:13:19-s:13:20-r:13:27-s:13:28-s:13:29-s:13:30-s:13:31-r:13:32-s:13:39-s:13:40-w:13:51-w:13:52-w:13:53-w:13:54-w:13:55-w:13:56-w:13:57-w:13:58-w:13:59-w:14:0-w:14:1-w:14:2-w:14:3-w:14:4-w:14:5-w:14:6-w:14:7-w:14:8-s:14:28-s:14:29-s:14:30-s:14:31-w:14:51-w:14:52-w:14:53-w:14:54-w:14:55-w:14:56-w:14:57-w:14:58-w:14:59-w:15:0-w:15:1-w:15:2-w:15:3-w:15:4-w:15:5-w:15:6-w:15:7-w:15:8-s:15:28-s:15:29-s:15:30-s:15:31-w:15:51-w:15:52-w:15:53-w:15:54-w:15:55-w:15:56-w:15:57-w:15:58-w:15:59-w:16:0-w:16:1-w:16:2-w:16:3-w:16:4-w:16:5-w:16:6-w:16:7-w:16:8-p:16:16-p:16:17-p:16:18-r:16:28-s:16:29-s:16:30-r:16:31-p:16:41-p:16:42-p:16:43-w:16:51-w:16:52-w:16:53-w:16:54-w:16:55-w:16:56-w:16:57-w:16:58-w:16:59-w:17:0-w:17:1-w:17:2-w:17:3-w:17:4-w:17:5-w:17:6-w:17:7-w:17:8-p:17:16-p:17:17-p:17:18-p:17:19-p:17:20-s:17:29-s:17:30-p:17:39-p:17:40-p:17:41-p:17:42-p:17:43-w:17:51-w:17:52-w:17:53-w:17:54-w:17:55-w:17:56-w:17:57-w:17:58-w:17:59-w:18:0-w:18:1-w:18:2-w:18:3-w:18:4-w:18:5-w:18:6-w:18:7-w:18:8-r:18:9-r:18:10-r:18:11-p:18:16-p:18:17-p:18:18-p:18:19-p:18:20-p:18:21-p:18:22-p:18:23-s:18:29-s:18:30-p:18:36-p:18:37-p:18:38-p:18:39-p:18:40-p:18:41-p:18:42-p:18:43-r:18:48-r:18:49-r:18:50-w:18:51-w:18:52-w:18:53-w:18:54-w:18:55-w:18:56-w:18:57-w:18:58-w:18:59-w:19:0-w:19:1-w:19:2-w:19:3-w:19:4-w:19:5-w:19:6-w:19:7-w:19:8-s:19:9-s:19:10-s:19:11-s:19:12-s:19:13-p:19:17-p:19:18-p:19:19-p:19:20-p:19:21-p:19:22-p:19:23-p:19:24-p:19:25-p:19:26-p:19:27-p:19:28-p:19:29-p:19:30-p:19:31-p:19:32-p:19:33-p:19:34-p:19:35-p:19:36-p:19:37-p:19:38-p:19:39-p:19:40-p:19:41-p:19:42-s:19:46-s:19:47-s:19:48-s:19:49-s:19:50-w:19:51-w:19:52-w:19:53-w:19:54-w:19:55-w:19:56-w:19:57-w:19:58-w:19:59-w:20:0-w:20:1-w:20:2-w:20:3-w:20:4-w:20:5-w:20:6-w:20:7-w:20:8-s:20:9-s:20:10-s:20:11-s:20:12-s:20:13-p:20:17-p:20:18-p:20:19-p:20:20-p:20:21-p:20:22-p:20:23-p:20:24-p:20:25-p:20:26-p:20:27-p:20:28-p:20:29-p:20:30-p:20:31-p:20:32-p:20:33-p:20:34-p:20:35-p:20:36-p:20:37-p:20:38-p:20:39-p:20:40-p:20:41-p:20:42-s:20:46-s:20:47-s:20:48-s:20:49-s:20:50-w:20:51-w:20:52-w:20:53-w:20:54-w:20:55-w:20:56-w:20:57-w:20:58-w:20:59-w:21:0-w:21:1-w:21:2-w:21:3-w:21:4-w:21:5-w:21:6-w:21:7-w:21:8-p:21:18-p:21:19-p:21:20-p:21:21-p:21:22-p:21:37-p:21:38-p:21:39-p:21:40-p:21:41-w:21:51-w:21:52-w:21:53-w:21:54-w:21:55-w:21:56-w:21:57-w:21:58-w:21:59-w:22:0-w:22:1-w:22:2-w:22:3-w:22:4-w:22:5-w:22:6-w:22:7-w:22:8-p:22:18-p:22:19-p:22:20-p:22:21-p:22:38-p:22:39-p:22:40-p:22:41-w:22:51-w:22:52-w:22:53-w:22:54-w:22:55-w:22:56-w:22:57-w:22:58-w:22:59-s:23:0-w:23:1-w:23:2-w:23:3-w:23:4-w:23:5-w:23:6-w:23:7-w:23:8-p:23:18-p:23:19-p:23:20-p:23:39-p:23:40-p:23:41-w:23:51-w:23:52-w:23:53-w:23:54-w:23:55-w:23:56-w:23:57-w:23:58-s:23:59-s:24:0-s:24:1-s:24:2-s:24:3-w:24:4-w:24:5-w:24:6-w:24:7-w:24:8-p:24:19-p:24:20-p:24:39-p:24:40-w:24:51-w:24:52-w:24:53-w:24:54-w:24:55-s:24:56-s:24:57-s:24:58-s:24:59-s:25:0-s:25:1-s:25:2-s:25:3-s:25:4-s:25:5-s:25:6-w:25:7-w:25:8-p:25:19-p:25:20-s:25:27-s:25:28-s:25:31-s:25:32-p:25:39-p:25:40-w:25:51-w:25:52-s:25:53-s:25:54-s:25:55-s:25:56-s:25:57-s:25:58-s:25:59-s:26:0-s:26:1-s:26:2-s:26:3-s:26:4-s:26:5-s:26:6-s:26:7-s:26:8-s:26:9-r:26:10-p:26:19-p:26:20-s:26:26-s:26:27-w:26:28-w:26:29-w:26:30-w:26:31-s:26:32-s:26:33-p:26:39-p:26:40-r:26:49-s:26:50-s:26:51-s:26:52-s:26:53-s:26:54-s:26:55-s:26:56-s:26:57-s:26:58-s:26:59-s:27:0-s:27:1-s:27:2-s:27:3-s:27:4-s:27:5-s:27:6-s:27:7-s:27:8-s:27:9-s:27:10-s:27:11-s:27:12-r:27:13-p:27:19-p:27:20-s:27:25-s:27:26-w:27:27-w:27:28-w:27:29-w:27:30-w:27:31-w:27:32-s:27:33-s:27:34-p:27:39-p:27:40-r:27:46-s:27:47-s:27:48-s:27:49-s:27:50-s:27:51-s:27:52-s:27:53-s:27:54-s:27:55-s:27:56-s:27:57-s:27:58-s:27:59-s:28:0-s:28:1-s:28:2-s:28:3-s:28:4-s:28:5-s:28:6-s:28:7-s:28:8-s:28:9-s:28:10-s:28:11-s:28:12-s:28:13-s:28:14-s:28:15-r:28:16-p:28:19-p:28:20-s:28:25-w:28:26-w:28:27-w:28:28-w:28:29-w:28:30-w:28:31-w:28:32-w:28:33-s:28:34-p:28:39-p:28:40-r:28:43-s:28:44-s:28:45-s:28:46-s:28:47-s:28:48-s:28:49-s:28:50-s:28:51-s:28:52-s:28:53-s:28:54-s:28:55-s:28:56-s:28:57-s:28:58-s:28:59-s:29:0-s:29:1-s:29:2-s:29:3-s:29:4-s:29:5-s:29:6-s:29:7-s:29:8-s:29:9-s:29:10-s:29:11-s:29:12-s:29:13-s:29:14-s:29:15-s:29:16-s:29:17-s:29:18-p:29:19-p:29:20-w:29:26-w:29:27-w:29:28-w:29:29-w:29:30-w:29:31-w:29:32-w:29:33-p:29:39-p:29:40-s:29:41-s:29:42-s:29:43-s:29:44-s:29:45-s:29:46-s:29:47-s:29:48-s:29:49-s:29:50-s:29:51-s:29:52-s:29:53-s:29:54-s:29:55-s:29:56-s:29:57-s:29:58-s:29:59-s:30:0-s:30:1-s:30:2-s:30:3-s:30:4-s:30:5-s:30:6-s:30:7-s:30:8-s:30:9-s:30:10-s:30:11-s:30:12-s:30:13-s:30:14-s:30:15-s:30:16-s:30:17-s:30:18-p:30:19-p:30:20-w:30:26-w:30:27-w:30:28-w:30:29-w:30:30-w:30:31-w:30:32-w:30:33-p:30:39-p:30:40-s:30:41-s:30:42-s:30:43-s:30:44-s:30:45-s:30:46-s:30:47-s:30:48-s:30:49-s:30:50-s:30:51-s:30:52-s:30:53-s:30:54-s:30:55-s:30:56-s:30:57-s:30:58-s:30:59-s:31:0-s:31:1-s:31:2-s:31:3-s:31:4-s:31:5-s:31:6-s:31:7-s:31:8-s:31:9-s:31:10-s:31:11-s:31:12-s:31:13-s:31:14-s:31:15-r:31:16-p:31:19-p:31:20-s:31:25-w:31:26-w:31:27-w:31:28-w:31:29-w:31:30-w:31:31-w:31:32-w:31:33-s:31:34-p:31:39-p:31:40-r:31:43-s:31:44-s:31:45-s:31:46-s:31:47-s:31:48-s:31:49-s:31:50-s:31:51-s:31:52-s:31:53-s:31:54-s:31:55-s:31:56-s:31:57-s:31:58-s:31:59-s:32:0-s:32:1-s:32:2-s:32:3-s:32:4-s:32:5-s:32:6-s:32:7-s:32:8-s:32:9-s:32:10-s:32:11-s:32:12-r:32:13-p:32:19-p:32:20-s:32:25-s:32:26-w:32:27-w:32:28-w:32:29-w:32:30-w:32:31-w:32:32-s:32:33-s:32:34-p:32:39-p:32:40-r:32:46-s:32:47-s:32:48-s:32:49-s:32:50-s:32:51-s:32:52-s:32:53-s:32:54-s:32:55-s:32:56-s:32:57-s:32:58-s:32:59-s:33:0-s:33:1-s:33:2-s:33:3-s:33:4-s:33:5-s:33:6-s:33:7-s:33:8-s:33:9-r:33:10-p:33:19-p:33:20-s:33:26-s:33:27-w:33:28-w:33:29-w:33:30-w:33:31-s:33:32-s:33:33-p:33:39-p:33:40-r:33:49-s:33:50-s:33:51-s:33:52-s:33:53-s:33:54-s:33:55-s:33:56-s:33:57-s:33:58-s:33:59-s:34:0-s:34:1-s:34:2-s:34:3-s:34:4-s:34:5-s:34:6-w:34:7-w:34:8-p:34:19-p:34:20-s:34:27-s:34:28-s:34:31-s:34:32-p:34:39-p:34:40-w:34:51-w:34:52-s:34:53-s:34:54-s:34:55-s:34:56-s:34:57-s:34:58-s:34:59-s:35:0-s:35:1-s:35:2-s:35:3-w:35:4-w:35:5-w:35:6-w:35:7-w:35:8-p:35:19-p:35:20-p:35:39-p:35:40-w:35:51-w:35:52-w:35:53-w:35:54-w:35:55-s:35:56-s:35:57-s:35:58-s:35:59-s:36:0-w:36:1-w:36:2-w:36:3-w:36:4-w:36:5-w:36:6-w:36:7-w:36:8-p:36:18-p:36:19-p:36:20-p:36:39-p:36:40-p:36:41-w:36:51-w:36:52-w:36:53-w:36:54-w:36:55-w:36:56-w:36:57-w:36:58-s:36:59-w:37:0-w:37:1-w:37:2-w:37:3-w:37:4-w:37:5-w:37:6-w:37:7-w:37:8-p:37:18-p:37:19-p:37:20-p:37:21-p:37:38-p:37:39-p:37:40-p:37:41-w:37:51-w:37:52-w:37:53-w:37:54-w:37:55-w:37:56-w:37:57-w:37:58-w:37:59-w:38:0-w:38:1-w:38:2-w:38:3-w:38:4-w:38:5-w:38:6-w:38:7-w:38:8-p:38:18-p:38:19-p:38:20-p:38:21-p:38:22-p:38:37-p:38:38-p:38:39-p:38:40-p:38:41-w:38:51-w:38:52-w:38:53-w:38:54-w:38:55-w:38:56-w:38:57-w:38:58-w:38:59-w:39:0-w:39:1-w:39:2-w:39:3-w:39:4-w:39:5-w:39:6-w:39:7-w:39:8-s:39:9-s:39:10-s:39:11-s:39:12-s:39:13-p:39:17-p:39:18-p:39:19-p:39:20-p:39:21-p:39:22-p:39:23-p:39:24-p:39:25-p:39:26-p:39:27-p:39:28-p:39:29-p:39:30-p:39:31-p:39:32-p:39:33-p:39:34-p:39:35-p:39:36-p:39:37-p:39:38-p:39:39-p:39:40-p:39:41-p:39:42-s:39:46-s:39:47-s:39:48-s:39:49-s:39:50-w:39:51-w:39:52-w:39:53-w:39:54-w:39:55-w:39:56-w:39:57-w:39:58-w:39:59-w:40:0-w:40:1-w:40:2-w:40:3-w:40:4-w:40:5-w:40:6-w:40:7-w:40:8-s:40:9-s:40:10-s:40:11-s:40:12-s:40:13-p:40:17-p:40:18-p:40:19-p:40:20-p:40:21-p:40:22-p:40:23-p:40:24-p:40:25-p:40:26-p:40:27-p:40:28-p:40:29-p:40:30-p:40:31-p:40:32-p:40:33-p:40:34-p:40:35-p:40:36-p:40:37-p:40:38-p:40:39-p:40:40-p:40:41-p:40:42-s:40:46-s:40:47-s:40:48-s:40:49-s:40:50-w:40:51-w:40:52-w:40:53-w:40:54-w:40:55-w:40:56-w:40:57-w:40:58-w:40:59-w:41:0-w:41:1-w:41:2-w:41:3-w:41:4-w:41:5-w:41:6-w:41:7-w:41:8-r:41:9-r:41:10-r:41:11-p:41:16-p:41:17-p:41:18-p:41:19-p:41:20-p:41:21-p:41:22-p:41:23-s:41:29-s:41:30-p:41:36-p:41:37-p:41:38-p:41:39-p:41:40-p:41:41-p:41:42-p:41:43-r:41:48-r:41:49-r:41:50-w:41:51-w:41:52-w:41:53-w:41:54-w:41:55-w:41:56-w:41:57-w:41:58-w:41:59-w:42:0-w:42:1-w:42:2-w:42:3-w:42:4-w:42:5-w:42:6-w:42:7-w:42:8-p:42:16-p:42:17-p:42:18-p:42:19-p:42:20-s:42:29-s:42:30-p:42:39-p:42:40-p:42:41-p:42:42-p:42:43-w:42:51-w:42:52-w:42:53-w:42:54-w:42:55-w:42:56-w:42:57-w:42:58-w:42:59-w:43:0-w:43:1-w:43:2-w:43:3-w:43:4-w:43:5-w:43:6-w:43:7-w:43:8-p:43:16-p:43:17-p:43:18-r:43:28-s:43:29-s:43:30-r:43:31-p:43:41-p:43:42-p:43:43-w:43:51-w:43:52-w:43:53-w:43:54-w:43:55-w:43:56-w:43:57-w:43:58-w:43:59-w:44:0-w:44:1-w:44:2-w:44:3-w:44:4-w:44:5-w:44:6-w:44:7-w:44:8-s:44:28-s:44:29-s:44:30-s:44:31-w:44:51-w:44:52-w:44:53-w:44:54-w:44:55-w:44:56-w:44:57-w:44:58-w:44:59-w:45:0-w:45:1-w:45:2-w:45:3-w:45:4-w:45:5-w:45:6-w:45:7-w:45:8-s:45:28-s:45:29-s:45:30-s:45:31-w:45:51-w:45:52-w:45:53-w:45:54-w:45:55-w:45:56-w:45:57-w:45:58-w:45:59-w:46:0-w:46:1-w:46:2-w:46:3-w:46:4-w:46:5-w:46:6-w:46:7-w:46:8-s:46:19-s:46:20-r:46:27-s:46:28-s:46:29-s:46:30-s:46:31-r:46:32-s:46:39-s:46:40-w:46:51-w:46:52-w:46:53-w:46:54-w:46:55-w:46:56-w:46:57-w:46:58-w:46:59-w:47:0-w:47:1-w:47:2-w:47:3-w:47:4-w:47:5-w:47:6-w:47:7-w:47:8-s:47:19-s:47:20-s:47:27-s:47:28-s:47:29-s:47:30-s:47:31-s:47:32-s:47:39-s:47:40-w:47:51-w:47:52-w:47:53-w:47:54-w:47:55-w:47:56-w:47:57-w:47:58-w:47:59-w:48:0-w:48:1-w:48:2-w:48:3-w:48:4-w:48:5-w:48:6-w:48:7-w:48:8-r:48:18-s:48:19-s:48:20-s:48:27-s:48:28-s:48:29-s:48:30-s:48:31-s:48:32-s:48:39-s:48:40-r:48:41-w:48:51-w:48:52-w:48:53-w:48:54-w:48:55-w:48:56-w:48:57-w:48:58-w:48:59-w:49:0-w:49:1-w:49:2-w:49:3-w:49:4-w:49:5-w:49:6-w:49:7-w:49:8-r:49:18-s:49:19-s:49:20-r:49:26-s:49:27-s:49:28-s:49:29-s:49:30-s:49:31-s:49:32-r:49:33-s:49:39-s:49:40-r:49:41-w:49:51-w:49:52-w:49:53-w:49:54-w:49:55-w:49:56-w:49:57-w:49:58-w:49:59-w:50:0-w:50:1-w:50:2-w:50:3-w:50:4-w:50:5-w:50:6-w:50:7-w:50:8-r:50:18-s:50:19-s:50:20-s:50:26-s:50:27-s:50:28-s:50:29-s:50:30-s:50:31-s:50:32-s:50:33-s:50:39-s:50:40-r:50:41-w:50:51-w:50:52-w:50:53-w:50:54-w:50:55-w:50:56-w:50:57-w:50:58-w:50:59-w:51:0-w:51:1-w:51:2-w:51:3-w:51:4-w:51:5-w:51:6-w:51:7-w:51:8-w:51:9-w:51:10-w:51:11-w:51:12-w:51:13-w:51:14-w:51:15-w:51:16-w:51:17-w:51:18-w:51:19-w:51:20-w:51:21-w:51:22-w:51:23-w:51:24-w:51:25-s:51:26-s:51:27-s:51:28-s:51:29-s:51:30-s:51:31-s:51:32-s:51:33-w:51:34-w:51:35-w:51:36-w:51:37-w:51:38-w:51:39-w:51:40-w:51:41-w:51:42-w:51:43-w:51:44-w:51:45-w:51:46-w:51:47-w:51:48-w:51:49-w:51:50-w:51:51-w:51:52-w:51:53-w:51:54-w:51:55-w:51:56-w:51:57-w:51:58-w:51:59-w:52:0-w:52:1-w:52:2-w:52:3-w:52:4-w:52:5-w:52:6-w:52:7-w:52:8-w:52:9-w:52:10-w:52:11-w:52:12-w:52:13-w:52:14-w:52:15-w:52:16-w:52:17-w:52:18-w:52:19-w:52:20-w:52:21-w:52:22-w:52:23-w:52:24-w:52:25-s:52:26-s:52:27-s:52:28-s:52:29-s:52:30-s:52:31-s:52:32-s:52:33-w:52:34-w:52:35-w:52:36-w:52:37-w:52:38-w:52:39-w:52:40-w:52:41-w:52:42-w:52:43-w:52:44-w:52:45-w:52:46-w:52:47-w:52:48-w:52:49-w:52:50-w:52:51-w:52:52-w:52:53-w:52:54-w:52:55-w:52:56-w:52:57-w:52:58-w:52:59-w:53:0-w:53:1-w:53:2-w:53:3-w:53:4-w:53:5-w:53:6-w:53:7-w:53:8-w:53:9-w:53:10-w:53:11-w:53:12-w:53:13-w:53:14-w:53:15-w:53:16-w:53:17-w:53:18-w:53:19-w:53:20-w:53:21-w:53:22-w:53:23-w:53:24-s:53:25-s:53:26-s:53:27-s:53:28-s:53:29-s:53:30-s:53:31-s:53:32-s:53:33-s:53:34-w:53:35-w:53:36-w:53:37-w:53:38-w:53:39-w:53:40-w:53:41-w:53:42-w:53:43-w:53:44-w:53:45-w:53:46-w:53:47-w:53:48-w:53:49-w:53:50-w:53:51-w:53:52-w:53:53-w:53:54-w:53:55-w:53:56-w:53:57-w:53:58-w:53:59-w:54:0-w:54:1-w:54:2-w:54:3-w:54:4-w:54:5-w:54:6-w:54:7-w:54:8-w:54:9-w:54:10-w:54:11-w:54:12-w:54:13-w:54:14-w:54:15-w:54:16-w:54:17-w:54:18-w:54:19-w:54:20-w:54:21-w:54:22-w:54:23-w:54:24-s:54:25-s:54:26-s:54:27-s:54:28-s:54:29-s:54:30-s:54:31-s:54:32-s:54:33-s:54:34-w:54:35-w:54:36-w:54:37-w:54:38-w:54:39-w:54:40-w:54:41-w:54:42-w:54:43-w:54:44-w:54:45-w:54:46-w:54:47-w:54:48-w:54:49-w:54:50-w:54:51-w:54:52-w:54:53-w:54:54-w:54:55-w:54:56-w:54:57-w:54:58-w:54:59-w:55:0-w:55:1-w:55:2-w:55:3-w:55:4-w:55:5-w:55:6-w:55:7-w:55:8-w:55:9-w:55:10-w:55:11-w:55:12-w:55:13-w:55:14-w:55:15-w:55:16-w:55:17-w:55:18-w:55:19-w:55:20-w:55:21-w:55:22-w:55:23-w:55:24-s:55:25-s:55:26-s:55:27-s:55:28-s:55:29-s:55:30-s:55:31-s:55:32-s:55:33-s:55:34-w:55:35-w:55:36-w:55:37-w:55:38-w:55:39-w:55:40-w:55:41-w:55:42-w:55:43-w:55:44-w:55:45-w:55:46-w:55:47-w:55:48-w:55:49-w:55:50-w:55:51-w:55:52-w:55:53-w:55:54-w:55:55-w:55:56-w:55:57-w:55:58-w:55:59-w:56:0-w:56:1-w:56:2-w:56:3-w:56:4-w:56:5-w:56:6-w:56:7-w:56:8-w:56:9-w:56:10-w:56:11-w:56:12-w:56:13-w:56:14-w:56:15-w:56:16-w:56:17-w:56:18-w:56:19-w:56:20-w:56:21-w:56:22-w:56:23-s:56:24-s:56:25-s:56:26-s:56:27-s:56:28-s:56:29-s:56:30-s:56:31-s:56:32-s:56:33-s:56:34-s:56:35-w:56:36-w:56:37-w:56:38-w:56:39-w:56:40-w:56:41-w:56:42-w:56:43-w:56:44-w:56:45-w:56:46-w:56:47-w:56:48-w:56:49-w:56:50-w:56:51-w:56:52-w:56:53-w:56:54-w:56:55-w:56:56-w:56:57-w:56:58-w:56:59-w:57:0-w:57:1-w:57:2-w:57:3-w:57:4-w:57:5-w:57:6-w:57:7-w:57:8-w:57:9-w:57:10-w:57:11-w:57:12-w:57:13-w:57:14-w:57:15-w:57:16-w:57:17-w:57:18-w:57:19-w:57:20-w:57:21-w:57:22-w:57:23-s:57:24-s:57:25-s:57:26-s:57:27-s:57:28-s:57:29-s:57:30-s:57:31-s:57:32-s:57:33-s:57:34-s:57:35-w:57:36-w:57:37-w:57:38-w:57:39-w:57:40-w:57:41-w:57:42-w:57:43-w:57:44-w:57:45-w:57:46-w:57:47-w:57:48-w:57:49-w:57:50-w:57:51-w:57:52-w:57:53-w:57:54-w:57:55-w:57:56-w:57:57-w:57:58-w:57:59-w:58:0-w:58:1-w:58:2-w:58:3-w:58:4-w:58:5-w:58:6-w:58:7-w:58:8-w:58:9-w:58:10-w:58:11-w:58:12-w:58:13-w:58:14-w:58:15-w:58:16-w:58:17-w:58:18-w:58:19-w:58:20-w:58:21-w:58:22-w:58:23-s:58:24-s:58:25-s:58:26-s:58:27-s:58:28-s:58:29-s:58:30-s:58:31-s:58:32-s:58:33-s:58:34-s:58:35-w:58:36-w:58:37-w:58:38-w:58:39-w:58:40-w:58:41-w:58:42-w:58:43-w:58:44-w:58:45-w:58:46-w:58:47-w:58:48-w:58:49-w:58:50-w:58:51-w:58:52-w:58:53-w:58:54-w:58:55-w:58:56-w:58:57-w:58:58-w:58:59-w:59:0-w:59:1-w:59:2-w:59:3-w:59:4-w:59:5-w:59:6-w:59:7-w:59:8-w:59:9-w:59:10-w:59:11-w:59:12-w:59:13-w:59:14-w:59:15-w:59:16-w:59:17-w:59:18-w:59:19-w:59:20-w:59:21-w:59:22-s:59:23-s:59:24-s:59:25-s:59:26-s:59:27-s:59:28-s:59:29-s:59:30-s:59:31-s:59:32-s:59:33-s:59:34-s:59:35-s:59:36-w:59:37-w:59:38-w:59:39-w:59:40-w:59:41-w:59:42-w:59:43-w:59:44-w:59:45-w:59:46-w:59:47-w:59:48-w:59:49-w:59:50-w:59:51-w:59:52-w:59:53-w:59:54-w:59:55-w:59:56-w:59:57-w:59:58-w:59:59"); //AND SO ON...
		
	}
	
}
