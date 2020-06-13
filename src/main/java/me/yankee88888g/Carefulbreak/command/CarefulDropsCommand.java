package me.yankee88888g.Carefulbreak.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.LiteralMessage;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.yankee88888g.Carefulbreak.CarefulBlocks;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.server.command.CommandManager.argument;


public class CarefulDropsCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("carefulBreak")
            .requires(source -> source.hasPermissionLevel(2))
            .then(argument("setting", ConfigFieldArgumentType.configField()).suggests(ConfigFieldArgumentType.suggestedStrings())
                .then(argument("value", BoolArgumentType.bool())
                    .executes(ctx -> {
                        try {
                            Field setting = ConfigFieldArgumentType.getField(ctx, "setting");
                            boolean value = BoolArgumentType.getBool(ctx, "value");

                            setting.set(null, value);
                            CarefulBlocks.CONFIGHANDLER.saveConfigs();

                            ctx.getSource().sendFeedback(new LiteralText("Setting changed successfully"), false);

                            WriteToFile.file();
                            WriteToFile.file2();

                            return Command.SINGLE_SUCCESS;
                        } catch (IllegalAccessException e) {
                            throw new SimpleCommandExceptionType(new LiteralMessage("Something went wrong setting the config.")).create();
                        }
                    })
                )
            )
        );
    }

    public static class ConfigFieldArgumentType implements ArgumentType<Field> {
        public static final DynamicCommandExceptionType NOT_FOUND_EXCEPTION = new DynamicCommandExceptionType((object) ->
            new LiteralText("Unknown config option")
        );

        public static ConfigFieldArgumentType configField() {
            return new ConfigFieldArgumentType();
        }

        public static SuggestionProvider<ServerCommandSource> suggestedStrings() {
            HashSet<String> values = new HashSet<>();
            CarefulBlocks.CONFIGHANDLER.getConfigFields().forEach((field, config) -> values.add(field.getName()));

            return (ctx, builder) -> ConfigFieldArgumentType.getSuggestions(builder, ImmutableList.copyOf(values));
        }

        public static CompletableFuture<Suggestions> getSuggestions(SuggestionsBuilder builder, List<String> list) {
            String remaining = builder.getRemaining().toLowerCase();

            if (list.isEmpty()) { // If the list is empty then return no suggestions
                return Suggestions.empty(); // No suggestions
            }

            for (String str : list) { // Iterate through the supplied list
                if (str.toLowerCase().startsWith(remaining)) {
                    builder.suggest(str); // Add every single entry to suggestions list.
                }
            }
            return builder.buildFuture(); // Create the CompletableFuture containing all the suggestions
        }

        public static Field getField(CommandContext<ServerCommandSource> ctx, String setting) {
            return ctx.getArgument("setting", Field.class);
        }

        @Override
        public Field parse(StringReader reader) throws CommandSyntaxException {
            int i = reader.getCursor();

            while(reader.canRead() && reader.peek() != ' ') {
                reader.skip();
            }

            String string = reader.getString().substring(i, reader.getCursor());

            for (Field field : CarefulBlocks.CONFIGHANDLER.getConfigFields().keySet()) {
                if (field.getName().toLowerCase().equals(string.toLowerCase())) {
                    return field;
                }
            }

            reader.setCursor(i);
            throw NOT_FOUND_EXCEPTION.create(string);
        }


    }

}
