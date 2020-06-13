package me.yankee88888g.Carefulbreak.command;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;

import java.io.*;
import java.util.Properties;

public class SettingsManager {

    public static void file() {
        try {
            File file = new File("config/setting.properties");
            /*If file gets created then the createNewFile()
             * method would return true or if the file is
             * already present it would return false
             */
            boolean fvar = file.createNewFile();
            if (fvar){
                System.out.println("File has been created successfully");
            }
            else{
                System.out.println("File already present at the specified location");
            }
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            e.printStackTrace();
        }
    }
}