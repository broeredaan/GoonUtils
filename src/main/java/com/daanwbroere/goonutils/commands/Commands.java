package com.daanwbroere.goonutils.commands;

import com.daanwbroere.goonutils.GoonUtils;
import com.daanwbroere.goonutils.commands.Broadcast.List;
import com.daanwbroere.goonutils.commands.Broadcast.Send;
import com.daanwbroere.goonutils.commands.Restart.Start;
import com.daanwbroere.goonutils.commands.Restart.Stop;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

public class Commands {

    private final GoonUtils plugin;

    public Commands(@Nonnull final GoonUtils goonutils) {
        plugin = goonutils;
        initializeCommands();
    }

    public void initializeCommands() {

        CommandSpec reload = CommandSpec.builder()
                .description(Text.of("Reload Goon Utils configs"))
                .permission("goonutils.reload")
                .executor(new CmdReload(plugin))
                .build();

        CommandSpec info = CommandSpec.builder()
                .description(Text.of("Reload Goon Utils configs"))
                .permission("goonutils.info")
                .executor(new CmdInfo(plugin))
                .build();

        CommandSpec main = CommandSpec.builder()
                .description(Text.of("Goon Utils base command"))
                .child(reload, "reload")
                .child(info, "info")
                .child(initializeCommandBroadcast(), "broadcast")
                .child(initializeCommandsRestart(), "restart")
                .build();

        Sponge.getCommandManager().register(plugin, main, "goon", "goonutils");

    }

    public CommandSpec initializeCommandsRestart() {

        CommandSpec start = CommandSpec.builder()
                .description(Text.of("Start a new restart timer based on configs. /goon reload to update configs"))
                .arguments(GenericArguments.flags().valueFlag(GenericArguments.integer(Text.of("time")), "t").buildWith(GenericArguments.none()))
                .permission("goonutils.restart.start")
                .executor(new Start(plugin))
                .build();

        CommandSpec stop = CommandSpec.builder()
                .description(Text.of("Stop the current restart"))
                .permission("goonutils.restart.stop")
                .executor(new Stop(plugin))
                .build();

        CommandSpec restart = CommandSpec.builder()
                .description(Text.of("Restart base command"))
                .child(stop, "stop")
                .child(start, "start")
                .build();
        return restart;
    }

    public CommandSpec initializeCommandBroadcast() {

        CommandSpec send = CommandSpec.builder()
                .description(Text.of("Send a broadcast message"))
                .permission("goonutils.broadcast.send")
                .arguments(GenericArguments.remainingJoinedStrings(Text.of("message")))
                .executor(new Send(plugin))
                .build();

        CommandSpec list = CommandSpec.builder()
                .description(Text.of("List all current broadcast messages"))
                .permission("goonutils.broadcast.list")
                .executor(new List(plugin))
                .build();

        CommandSpec broadcast = CommandSpec.builder()
                .description(Text.of("Broadcast base command"))
                .child(send, "send")
                .child(list, "list")
                .build();
        return broadcast;
    }
}