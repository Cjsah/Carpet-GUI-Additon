package net.cjsah.cga;

import carpet.CarpetServer;
import carpet.api.settings.CarpetRule;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Collection;

public class CGAServer implements ModInitializer {
    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(CGAUtil.C2SGetRule, (server, player, handler, ignore, responseSender) -> {
            Collection<CarpetRule<?>> rules = CarpetServer.settingsManager.getCarpetRules();
            FriendlyByteBuf buffer = PacketByteBufs.create();
            buffer.writeCollection(rules, (buf, rule) -> {
                buf.writeUtf(rule.name());
                buf.writeCollection(rule.extraInfo(), FriendlyByteBuf::writeComponent);
                buf.writeCollection(rule.categories(), FriendlyByteBuf::writeUtf);
                buf.writeCollection(rule.suggestions(), FriendlyByteBuf::writeUtf);
                buf.writeUtf(rule.type().getTypeName());
                buf.writeUtf(rule.value().toString());
                buf.writeUtf(rule.defaultValue().toString());
            });
            ServerPlayNetworking.send(player, CGAUtil.S2CReturnRule, buffer);
        });
    }
}
