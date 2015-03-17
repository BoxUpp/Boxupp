package com.boxupp.addon;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.boxupp.dao.AvailableBoxesDAOManager;
import com.boxupp.db.beans.AvailableBoxesBean;

public class RetrieveBoxes {

	public static RetrieveBoxes obj;

	public RetrieveBoxes(){
	}

	public static RetrieveBoxes getInstance(){
		if(obj == null){
			obj = new RetrieveBoxes();
		}
		return obj;
	}

	public List<AvailableBoxesBean> getAvailableBoxesList() {
		try{
			Document doc = Jsoup.connect("http://www.vagrantbox.es").get();
			int count=0;
			AvailableBoxesBean availableBoxesBean = new AvailableBoxesBean();
			String boxName,boxProvider,boxUrl,boxSize;

			for (Element table : doc.select("table[id=dataTable]")) {
				for (Element row : table.select("tr")) {
					Elements column = row.select("td");
					if(!column.isEmpty()){
						count++;
					}
				}
			}

			if (count>AvailableBoxesDAOManager.getInstance().getAvailableBoxesCount()){
				for (Element table : doc.select("table[id=dataTable]")) {
					for (Element row : table.select("tr")) {
						Elements column = row.select("td");
						if(column.size()==4){
							boxName= column.get(0).text();
							boxProvider= column.get(1).text();
							boxUrl= column.get(2).text();
							boxSize= column.get(3).text();
							availableBoxesBean.setBoxName(boxName);
							availableBoxesBean.setBoxProvider(boxProvider);
							availableBoxesBean.setBoxUrl(boxUrl);
							availableBoxesBean.setBoxSize(boxSize);
							AvailableBoxesDAOManager.getInstance().create(availableBoxesBean);
						}
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return AvailableBoxesDAOManager.getInstance().retireveAvailableBoxes();
	}
}