package ioc.config;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ConfigManager {
    public static HashMap<String, Bean> getConfig(String path) {
        HashMap<String, Bean> map = new HashMap<String, Bean>();

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new File(path));
        } catch (DocumentException e) {
            System.out.println("parse xml error");
            e.printStackTrace();
        }

        Element root = document.getRootElement();

        for (Element element : root.elements("bean")) {
            Bean bean = new Bean();
            String id = element.attributeValue("id");
            String className = element.attributeValue("class");
            String scope = element.attributeValue("scope");
            if (scope!=null) {
                bean.setScope(scope);
            }
            bean.setId(id);
            bean.setClassName(className);
            List<Property> properties = new LinkedList<Property>();
//            LinkedList<Element> child = (LinkedList<Element>) element.elements("property");
//            if (child!=null) {
                for (Element element1 : element.elements("property")) {
                    String name = element1.attributeValue("name");
                    String ref = element1.attributeValue("ref");
                    String value = element1.attributeValue("value");
                    Property property = new Property();
                    property.setName(name);
                    if (ref!=null) {
                        property.setRef(ref);
                    }
                    else {
                        property.setValue(value);
                    }
                    properties.add(property);
                }
                bean.setProperties(properties);
//            }
            map.put(id, bean);
        }
        return map;
    }
}
