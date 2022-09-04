package net.cjsah.cga;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;

public class CGAClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            LiteralArgumentBuilder<FabricClientCommandSource> literal = literal("cgui");
            dispatcher.register(literal.executes((context) -> {
                ClientPlayNetworking.send(CGAUtil.C2SGetRule, PacketByteBufs.empty());
                return Command.SINGLE_SUCCESS;
            }));
        });

        ClientPlayNetworking.registerGlobalReceiver(CGAUtil.S2CReturnRule, (client, handler, buffer, responseSender) ->
                openGui(buffer.readCollection(ArrayList::new, buf -> {
                    try {
                        return new ClientRule(
                                buf.readUtf(),
                                buf.readCollection(ArrayList::new, FriendlyByteBuf::readComponent),
                                buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf),
                                buf.readCollection(ArrayList::new, FriendlyByteBuf::readUtf),
                                Class.forName(buf.readUtf()),
                                buf.readUtf(),
                                buf.readUtf()
                        );
                    }catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                }))
        );
    }

    private static void openGui(List<ClientRule> list) {
        Map<String, Integer> map = new HashMap<>();
        for (ClientRule rule : list) {
            String type = rule.type().toString();
            map.put(type, map.containsKey(type) ? map.get(type) + 1 : 1);
        }
        System.out.println(map);
    }
}
