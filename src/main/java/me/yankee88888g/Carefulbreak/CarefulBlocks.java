package me.yankee88888g.Carefulbreak;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import me.yankee88888g.Carefulbreak.command.CarefulDropsCommand;
import me.yankee88888g.Carefulbreak.command.Read;
import me.yankee88888g.Carefulbreak.command.SettingsManager;
import me.yankee88888g.Carefulbreak.command.WriteToFile;
import me.yankee88888g.Carefulbreak.config.CarefulDropsConfig;
import me.yankee88888g.Carefulbreak.config.ConfigFileHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraft.command.arguments.serialize.ConstantArgumentSerializer;

import java.io.IOException;


public class CarefulBlocks<entity> implements ModInitializer {
	public static ConfigFileHandler CONFIGHANDLER;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		SettingsManager.file();
		WriteToFile.file();
		Read.getPropValues();
		Read.getPropValues2();
		// Load/Save the config file
		CONFIGHANDLER = new ConfigFileHandler(CarefulDropsConfig.class, "carefuldrops");

		// Allows for deserializing the commands
		ArgumentTypes.register("carefuldrops:config", CarefulDropsCommand.ConfigFieldArgumentType.class, new ConstantArgumentSerializer<>(CarefulDropsCommand.ConfigFieldArgumentType::configField));

		// Command is valid for both dedicated and integrated
		CommandRegistry.INSTANCE.register(true, CarefulDropsCommand::register);
		CommandRegistry.INSTANCE.register(false, CarefulDropsCommand::register);
	}
}