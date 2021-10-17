package xland.mcfgmod.playerskullforge;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.NameTagItem;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

import java.util.Objects;

import static xland.mcfgmod.playerskullforge.PlayerSkullForge.*;

public class PlayerSkullRecipe extends SpecialRecipe {
    public PlayerSkullRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(@Nonnull CraftingInventory inventory, @Nonnull World worldIn) {
        boolean hasNameTag = false;
        boolean hasSkull = false;
        boolean hasSubIngredient = false;

        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Item item = stack.getItem();
                if (item instanceof NameTagItem
                        && stack.hasDisplayName()) {
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

    @Override @Nonnull
    public ItemStack getCraftingResult(@Nonnull CraftingInventory inventory) {
        ItemStack returnStack = new ItemStack(Items.PLAYER_HEAD);
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof NameTagItem
                        && stack.hasDisplayName()) {
                    returnStack.getOrCreateTag().putString("SkullOwner",
                            Objects.requireNonNull(TextFormatting.getTextWithoutFormattingCodes(
                                    stack.getDisplayName().getString()
                            )));
                }
            }
        } return returnStack;
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 3;
    }

    @Override @Nonnull
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER.get();
    }

    private static boolean hasSPMPeel(Item item) {
        return ForgeRegistries.ITEMS.containsKey(PEEL_ID)
                && PEEL_ID.equals(item.getRegistryName());
    }

    private static final ResourceLocation PEEL_ID = new ResourceLocation("sweet_potato:peel");
}
