package net.cjsah.cga;

import net.minecraft.resources.ResourceLocation;

public class CGAUtil {
    public static final String ID = "cga";
    public static final String NAME = "Carpet-GUI-Addition";

    public static final ResourceLocation C2SGetRule = id("cga_get");
    public static final ResourceLocation S2CReturnRule = id("cga_return");

    public static ResourceLocation id(String id) {
        return new ResourceLocation(ID, id);
    }
}
