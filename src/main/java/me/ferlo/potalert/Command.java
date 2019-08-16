package me.ferlo.potalert;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Command extends CommandBase {

    private static final List<String> ALIASES;

    static {
        final List<String> temp = new ArrayList<String>();
        temp.add("potalert");
        temp.add("palert");

        ALIASES = Collections.unmodifiableList(temp);
    }

    private final Config cfg;

    public Command(Config cfg) {
        this.cfg = cfg;
    }

    @Override
    public String getCommandName() {
        return "potalert";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/portalert [number from 1-9/disable]";
    }

    @Override
    public List<String> getCommandAliases() {
        return ALIASES;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
            throw invalidArgument();

        if (args[0].equalsIgnoreCase("disable")) {
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Disabled potalert"));
            cfg.enabled(false);
            return;
        }

        try {
            parseInt(args[0]);
        } catch (NumberFormatException ex) {
            throw invalidArgument();
        }

        int alertValue = parseInt(args[0], 1, 9);

        cfg.enabled(true);
        cfg.alertValue(alertValue);
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Potalert set to " + alertValue));
    }

    private CommandException invalidArgument() {
        return new WrongUsageException("/potalert [number from 1 - 9/disable]");
    }
}
