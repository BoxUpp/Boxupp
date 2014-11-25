package com.boxupp;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.boxupp.beans.Config;

public class ToolConfigurationReader {
	private static Logger logger = LogManager.getLogger(ToolConfigurationReader.class.getName());

	public Config getConfiguration(){

		JAXBContext jc;
		Config config = null ;
		try {
			jc = JAXBContext.newInstance(Config.class);
			InputStream toolSettings = getClass().getResourceAsStream("/config.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document doc;
			doc = db.parse(toolSettings);
			Binder<Node> binder = jc.createBinder();
		    config = (Config) binder.unmarshal(doc);
			binder.updateXML(config);
		} catch (ParserConfigurationException e) {
			logger.error("Error in parsing configuration file :"+e.getMessage());
		} catch (SAXException e) {
			logger.error("Error in reading configuration file :"+e.getMessage());
		} catch (IOException e) {
			logger.error("Error in reading configuration file :"+e.getMessage());
		} catch (JAXBException e) {
			logger.error("Error in reading configuration file :"+e.getMessage());
		}
		return config;

	}
}