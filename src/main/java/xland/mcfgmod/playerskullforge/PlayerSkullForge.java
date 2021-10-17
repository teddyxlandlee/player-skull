package xland.mcfgmod.playerskullforge;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod("player-skull-forge")
public class PlayerSkullForge {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS
            = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "data/player_skull");

    public PlayerSkullForge() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ITag<Item> SUB_INGREDIENTS;
    protected static final RegistryObject<SpecialRecipeSerializer<PlayerSkullRecipe>> SERIALIZER;

    static {
        SUB_INGREDIENTS = ItemTags.createOptional(new ResourceLocation("player_skull", "sub_ingredients"));

        SERIALIZER = RECIPE_SERIALIZERS.register("player_skull", () -> new SpecialRecipeSerializer<>(PlayerSkullRecipe::new));
    }

    protected static final ITag<Item> SKULLS
            = ItemTags.createOptional(new ResourceLocation("player_skull", "skulls"));

}
