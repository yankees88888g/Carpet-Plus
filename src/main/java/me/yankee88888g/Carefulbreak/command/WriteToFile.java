package me.yankee88888g.Carefulbreak.command;

import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class WriteToFile {

    public static void file() {
        try {
            FileWriter myWriter = new FileWriter("config/setting.properties");
            if(CarefulDropsConfig.overrideBlockDrops) {
                myWriter.write("\n" +"overrideBlockDrops=false");
                myWriter.close();
            }else{
                myWriter.write( "overrideBlockDrops="+CarefulDropsConfig.overrideBlockDrops + "\n" + "isOverrideKeyBind="+CarefulDropsConfig.isOverrideKeyBind + "\n ");


                myWriter.close();
            }
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}