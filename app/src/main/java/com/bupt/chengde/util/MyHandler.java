package com.bupt.chengde.util;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.bupt.chengde.entity.Area;
import com.bupt.chengde.entity.City;
import com.bupt.chengde.entity.Province;


public class MyHandler extends DefaultHandler {
	private List<Province> provinceList;
	private Province pro;
	private City city;
	private Area area;
	private String tagName = "";
	
	public MyHandler(List<Province> provinceList){
		this.provinceList = provinceList;
	}
	
	@Override
	public void startDocument() throws SAXException {
		
	}
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		tagName = qName;
		if("p".equals(qName)){
			pro = new Province();
			pro.setId(attributes.getValue(0));
		}else if("c".equals(qName)){
			city = new City();
			city.setId(attributes.getValue(0));
		}else if("d".equals(qName)){
			area = new Area();
			area.setId(attributes.getValue(0));
		}
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String info = new String(ch, start, length);
		if("pn".equals(tagName)){
			pro.setName(info);
		}else if("cn".equals(tagName)){
			city.setName(info);
		}else if("d".equals(tagName)){
			area.setName(info);
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		tagName = "";
		if("d".equals(qName)){
			city.getList().add(area);
		}else if("c".equals(qName)){
			//把city对象添加到pro中
			pro.getList().add(city);
		}else if("p".equals(qName)){
			provinceList.add(pro);
		}
	}
	@Override
	public void endDocument() throws SAXException {
//		Log.v("TAG", "provinceList----------------->" + provinceList.toString());
	}	
}
