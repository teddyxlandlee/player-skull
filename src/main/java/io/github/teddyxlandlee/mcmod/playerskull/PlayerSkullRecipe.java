package io.github.teddyxlandlee.mcmod.playerskull;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

/**
 * Use:<br />
 * - Name Tag<br />
 * - Any skull<br />
 * - Sub-ingredient: Pufferfish / Any dye / Peel (with SPM) / Raw Pork
 *              / Baked Pork / Feather / Leather
 */
public class PlayerSkullRecipe extends SpecialCraftingRecipe {
    public static final DynamicItemTag SUB_INGREDIENTS;

    public PlayerSkullRecipe(Identifier id) {
        super(id);
    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        boolean hasNameTag = false;
        boolean hasSkull = false;
        boolean hasSubIngredient = false;

        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item instanceof NameTagItem
                    && stack.hasCustomName()) {
                    if (hasNameTag) return false;
                    hasNameTag = true;
                } else if (SKULLS.contains(item)) {
                    if (hasSkull) return false;
                    hasSkull = true;
                } else if (SUB_INGREDIENTS.contains(item) || hasSPMPeel(item)) {
                    //if (hasSubIngredient) return false;
                    // Any amount of sub ingredients are okay
                    hasSubIngredient = true;
                } else {
                    // None of above
                    return false;
                }
            }
        } return hasNameTag && hasSkull && hasSubIngredient;
    }

    private static boolean hasSPMPeel(Item item) {
        return LOADED_SPM && PEEL_ID.equals(Registry.ITEM.getId(item));
    }
    private static final boolean LOADED_SPM = FabricLoader.getInstance()
            .getModContainer("sweet_potato").isPresent();
    private static final Identifier PEEL_ID = new Identifier("sweet_potato", "peel");

    @Override
    public ItemStack craft(CraftingInventory inventory) {
        ItemStack returnStack = new ItemStack(Items.PLAYER_HEAD);
        for (int i = 0; i < inventory.size(); ++i) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof NameTagItem
                        && stack.hasCustomName()) {
                    returnStack.getOrCreateTag().putString("SkullOwner",
                            Formatting.strip(stack.getName().getString()));
                }
            }
        } return returnStack;
    }

    @Override @Environment(EnvType.CLIENT)
    public boolean fits(int width, int height) {
        return width * height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public ItemStack getOutput() {  // FAKE
        return new ItemStack(Items.PLAYER_HEAD);
    }

    private static final SpecialRecipeSerializer<PlayerSkullRecipe> SERIALIZER;

    static {
        SUB_INGREDIENTS = DynamicItemTag.of(
                Items.PUFFERFISH,
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP,
                Items.FEATHER,
                Items.LEATHER
        );
        for (DyeColor dyeColor : DyeColor.values()) {
            SUB_INGREDIENTS.add(DyeItem.byColor(dyeColor));
        }
        SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new Identifier("player_skull", "player_skull"),
                new SpecialRecipeSerializer<>(PlayerSkullRecipe::new));
    }

    public static void load() {
        /* Entrypoint */
        FabricLoader.getInstance().getEntrypoints("player-skull", PlayerSkullLinkage.class).forEach(PlayerSkullLinkage::init);
    }

    private static final DynamicItemTag SKULLS = DynamicItemTag.of(
            Items.SKELETON_SKULL,
            Items.WITHER_SKELETON_SKULL,
            Items.ZOMBIE_HEAD,
            Items.PLAYER_HEAD,
            Items.CREEPER_HEAD,
            Items.DRAGON_HEAD
    );
}
