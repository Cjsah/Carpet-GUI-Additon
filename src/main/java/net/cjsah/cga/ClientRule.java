package net.cjsah.cga;

import net.minecraft.network.chat.Component;

import java.util.Collection;

public record ClientRule(String name, Collection<Component> extraInfo, Collection<String> categories, Collection<String> suggestions, Class<?> type, String value, String defaultValue) {}
