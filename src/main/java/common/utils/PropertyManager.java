package common.utils;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

public class PropertyManager {

    private static PropertyManager manager = null;
    private static Object managerLock = new Object();
    private static String propsName = "/prjcfg.properties";
    private Properties properties = null;
    private Object propertiesLock = new Object();
    private String resourceURI;
    private long propTime;

    /**
     * Returns a Jive property
     *
     * @param name the name of the property to return.
     * @return The property value
     * @returns the property value specified by name.
     */
    public static String getProperty(String name) {
        if (manager == null) {
            synchronized (managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propsName);
                }
            }
        }
        String props = manager.getProp(name);
        if (props == null)
            return null;
        try {
            props = new String(props.getBytes("ISO-8859-1"));
        } catch (Exception e) {

        }
        return props;
    }

    /**
     * Sets a Jive property.
     *
     * @param name  the name of the property being set.
     * @param value the value of the property being set.
     */
    public static void setProperty(String name, String value) {
        if (manager == null) {
            synchronized (managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propsName);
                }
            }
        }
        manager.setProp(name, value);
    }

    /**
     * Returns true if the properties are readable. This method is mainly
     * valuable at setup time to ensure that the properties file is setup
     * correctly.
     *
     * @return Description of the Return Value
     */
    public static boolean propertyFileIsReadable() {
        if (manager == null) {
            synchronized (managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propsName);
                }
            }
        }
        return manager.propFileIsReadable();
    }

    /**
     * Returns true if the properties are writable. This method is mainly
     * valuable at setup time to ensure that the properties file is setup
     * correctly.
     *
     * @return Description of the Return Value
     */
    public static boolean propertyFileIsWritable() {
        if (manager == null) {
            synchronized (managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propsName);
                }
            }
        }
        return manager.propFileIsWritable();
    }

    /**
     * Returns true if the jive.properties file exists where the path property
     * purports that it does.
     *
     * @return Description of the Return Value
     */
    public static boolean propertyFileExists() {
        if (manager == null) {
            synchronized (managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propsName);
                }
            }
        }
        return manager.propFileExists();
    }

    /**
     * Singleton access only.
     *
     * @param resourceURI Description of the Parameter
     */
    private PropertyManager(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    /**
     * Gets a Jive property. Jive properties are stored in jive.properties. The
     * properties file should be accesible from the classpath. Additionally, it
     * should have a path field that gives the full path to where the file is
     * located. Getting properties is a fast operation.
     *
     * @param name Description of the Parameter
     * @return The prop value
     */
    public String getProp(String name) {
        //If properties aren't loaded yet. We also need to make this thread
        //safe, so synchronize...
        if (properties == null) {
            synchronized (propertiesLock) {
                //Need an additional check
                if (properties == null) {
                    loadProps();
                }
            }
        }

        long curPropTime = (new File(propsName)).lastModified();
        if (curPropTime != this.propTime) {
            loadProps();
        }
        return properties.getProperty(name);
    }

    /**
     * Sets a Jive property. Because the properties must be saved to disk every
     * time a property is set, property setting is relatively slow.
     *
     * @param name  The new prop value
     * @param value The new prop value
     */
    public void setProp(String name, String value) {
        //Only one thread should be writing to the file system at once.
        synchronized (propertiesLock) {
            //Create the properties object if necessary.
            if (properties == null) {
                loadProps();
            }
            properties.setProperty(name, value);
            //Now, save the properties to disk. In order for this to work, the user
            //needs to have set the path field in the properties file. Trim
            //the String to make sure there are no extra spaces.
            String path = properties.getProperty("path").trim();
            OutputStream out = null;
            try {
                out = new FileOutputStream(path);
                properties.store(out, "jive.properties -- " + (new Date()));
            } catch (Exception ioe) {
                System.err.println("There was an error writing jive.properties to " +
                        path + ". " +
                        "Ensure that the path exists and that the Jive process has permission " +
                        "to write to it -- " + ioe);
                ioe.printStackTrace();
            } finally {
                try {
                    out.close();
                } catch (Exception e) {
                }
                propTime = (new File(propsName)).lastModified();
            }
        }
    }

    /**
     * Loads Jive properties from the disk.
     */
    private void loadProps() {
        properties = new Properties();
        InputStream in = null;
        try {
            propTime = (new File(propsName)).lastModified();
            in = getClass().getResourceAsStream(resourceURI);
            properties.load(in);
        } catch (IOException ioe) {
            System.err.println(
                    "Error reading Jive properties in DbForumFactory.loadProperties() " +
                            ioe);
            ioe.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * Returns true if the properties are readable. This method is mainly
     * valuable at setup time to ensure that the properties file is setup
     * correctly.
     *
     * @return Description of the Return Value
     */
    public boolean propFileIsReadable() {
        try {
            InputStream in = getClass().getResourceAsStream(resourceURI);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns true if the jive.properties file exists where the path property
     * purports that it does.
     *
     * @return Description of the Return Value
     */
    public boolean propFileExists() {
        String path = getProp("path");
        File file = new File(path);
        if (file.isFile()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the properties are writable. This method is mainly
     * valuable at setup time to ensure that the properties file is setup
     * correctly.
     *
     * @return Description of the Return Value
     */
    public boolean propFileIsWritable() {
        String path = getProp("path");
        File file = new File(path);
        if (file.isFile()) {
            //See if we can write to the file
            if (file.canWrite()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Gets the allProperties attribute of the PropertyManager class
     *
     * @return The allProperties value
     */
    public static Properties getAllProperties() {
        if (manager == null) {
            synchronized (managerLock) {
                if (manager == null) {
                    manager = new PropertyManager(propsName);
                }
            }
        }
        return manager.properties;
    }

    /**
     * Gets the intProperty attribute of the PropertyManager class
     *
     * @param name Description of the Parameter
     * @return The intProperty value
     */
    public static int getIntProperty(String name) {
        try {
            int property = Integer.parseInt(getProperty(name));
            return property;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Gets the longProperty attribute of the SetupManager class
     *
     * @param name Description of the Parameter
     * @return The longProperty value
     */
    public static long getLongProperty(String name) {
        try {
            long property = Long.parseLong(getProperty(name));
            return property;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Gets the floatProperty attribute of the SetupManager class
     *
     * @param name Description of the Parameter
     * @return The floatProperty value
     */
    public static float getFloatProperty(String name) {
        try {
            float property = Float.parseFloat(getProperty(name));
            return property;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Gets the doubleProperty attribute of the SetupManager class
     *
     * @param name Description of the Parameter
     * @return The doubleProperty value
     */
    public static double getDoubleProperty(String name) {
        try {
            double property = Double.parseDouble(getProperty(name));
            return property;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * Description of the Method
     *
     * @param name Description of the Parameter
     * @return Description of the Return Value
     */
    public static Date getDateProperty(String name) {
        if (getCalendarProperty(name) == null) {
            return null;
        } else {
            return getCalendarProperty(name).getTime();
        }
    }

    /**
     * Gets the calendarProperty attribute of the SetupManager class
     *
     * @param name Description of the Parameter
     * @return The calendarProperty value
     */
    public static Calendar getCalendarProperty(String name) {
        String dateStr = getProperty(name);
        try {
            String[] theDate = dateStr.split("-");
            Calendar c = Calendar.getInstance();
            c.set(Integer.parseInt(theDate[0]),
                    Integer.parseInt(theDate[1]) - 1,
                    Integer.parseInt(theDate[2]));
            return c;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Gets the property attribute of the PropertyManager class
     *
     * @param key   Description of the Parameter
     * @param argus Description of the Parameter
     * @return The property value
     */
    public static String getProperty(String key, String[] argus) {
        //将字符串分割成数组，注意字符串末尾是分隔符的情况。
        String src = getProperty(key);
        if (src != null) {
            try {
                src = new String(src.getBytes("ISO-8859-1"));
            } catch (Exception e) {

            }
        }

        //String src = key;//测试用
        String dot = "%s";
        String sup = ""; //末尾是分隔符时要追加的字符
        if (src.endsWith(dot)) {
            sup = "a";
            src = src + sup;
        }
        String[] tmp = src.split(dot);
        if (sup.equals("a")) tmp[tmp.length - 1] = "";
        //%s个数与argus个数不符，退出
        if (tmp.length - 1 != argus.length) {
            return null;
        }
        //按顺序替换掉%s字符串
        else {
            StringBuffer sbf = new StringBuffer();
            for (int i = 0; i < argus.length; i++) {
                sbf.append(tmp[i]);
                sbf.append(argus[i]);
            }
            sbf.append(tmp[tmp.length - 1]);

            return sbf.toString().trim();
        }
    }
}
