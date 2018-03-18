package ioc.context;

import ioc.entity.*;
import ioc.config.Bean;
import ioc.config.ConfigManager;
import ioc.config.Property;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FileSystemXmlApplicationContext implements BeanFactory {
    public Object getBean(String name) {
        Object object = context.get(name);
        if (object==null) {
            object = createBean(map.get(name));
        }
        return object;
    }

    private Map<String, Bean> map;
    // 作为IOC容器使用,放置sring放置的对象
    private Map<String, Object> context = new HashMap<String, Object>();

    public FileSystemXmlApplicationContext(String path) {
        map = ConfigManager.getConfig(path);

        for (Map.Entry kv : map.entrySet()) {
            String name = (String) kv.getKey();
            Bean bean = (Bean) kv.getValue();
            Object object = context.get(name);
            if (object == null && bean.getScope().equals("singleton")) {
                context.put(name, createBean(bean));
            }
        }

    }

    private Object createBean(Bean bean) {
        Class cls = null;
        try {
//            if (bean.getClassName()==null) System.out.println("null");

            cls = Class.forName(bean.getClassName());
//            System.out.println(cls.getName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            System.out.println("class name: " + bean.getClassName());
        }

        Object object = null;
        try {
            object = cls.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (bean.getProperties() != null) {
            for (Property property : bean.getProperties()) {
                String name = property.getName();
                String ref = property.getRef();
                String value = property.getValue();
//                Object temp = context.get(name);
                if (value != null) {
                    Map<String, String[]> map = new HashMap<String, String[]>();
                    map.put(name, new String[]{value});
                    try {
                        BeanUtils.populate(object, map);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Object temp = context.get(ref);
                    if (temp == null) {
                        System.out.println("ref: " + ref);
                        temp = createBean(map.get(ref));
                        if (map.get(ref).getScope().equals("singleton")) {
                            context.put(ref, temp);
                        }
                    }
                    try {
                        BeanUtils.setProperty(object, name, temp);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return object;
    }
}
