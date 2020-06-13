package me.yankee88888g.Carefulbreak.command;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;

import static me.yankee88888g.Carefulbreak.config.CarefulDropsConfig.isOverrideKeyBind;
import static me.yankee88888g.Carefulbreak.config.CarefulDropsConfig.overrideBlockDrops;

public class Read {

    public static void file() throws IOException {
        Read properties = new Read();
        properties.getPropValues();

    }



    public static String getPropValues(){

        Properties prop = new Properties();
        String fileName = "config/setting.properties";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
        }

        isOverrideKeyBind = Boolean.parseBoolean(prop.getProperty("isOverrideKeyBind"));

        
        System.out.println(isOverrideKeyBind);

        return "config/setting.properties";
    }

    public static String getPropValues2(){

        Properties prop = new Properties();
        String fileName = "config/setting.properties";
        InputStream is = null;
        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
        }

        overrideBlockDrops = Boolean.parseBoolean(prop.getProperty("overrideBlockDrops"));
        
        System.out.println(overrideBlockDrops);

        return "config/setting.properties";
    }
}
