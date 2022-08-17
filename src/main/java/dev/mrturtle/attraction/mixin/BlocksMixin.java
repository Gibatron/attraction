package dev.mrturtle.attraction.mixin;

import dev.mrturtle.attraction.blocks.LodestoneBlock;
import dev.mrturtle.attraction.blocks.NetheriteBlockBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public class BlocksMixin {
	@Redirect(method = "<clinit>()V",
			// Target NEW opcodes
			// Using the classname rather than a constructor descriptor because Block has only 1 constructor
			at = @At(value = "NEW", target = "net/minecraft/block/Block"),
			// There are many calls to the Block constructor in Blocks,
			// so target a slice of the class, rather than the whole thing
			slice = @Slice(
					// from the PUTSTATIC opcode for the field before the target
					from = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC,
							target = "Lnet/minecraft/block/Blocks;POTTED_WARPED_ROOTS:Lnet/minecraft/block/Block;"),
					// to the PUTSTATIC opcode for the target field
					to = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC,
							target = "Lnet/minecraft/block/Blocks;LODESTONE:Lnet/minecraft/block/Block;")))
	private static Block makeLodestoneMagnetic(AbstractBlock.Settings settings) {
		return new LodestoneBlock(settings);
	}

	@Redirect(method = "<clinit>()V",
			// Target NEW opcodes
			// Using the classname rather than a constructor descriptor because Block has only 1 constructor
			at = @At(value = "NEW", target = "net/minecraft/block/Block"),
			// There are many calls to the Block constructor in Blocks,
			// so target a slice of the class, rather than the whole thing
			slice = @Slice(
					// from the PUTSTATIC opcode for the field before the target
					from = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC,
							target = "Lnet/minecraft/block/Blocks;HONEYCOMB_BLOCK:Lnet/minecraft/block/Block;"),
					// to the PUTSTATIC opcode for the target field
					to = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC,
							target = "Lnet/minecraft/block/Blocks;NETHERITE_BLOCK:Lnet/minecraft/block/Block;")))
	private static Block makeNetheriteBlockMagnetic(AbstractBlock.Settings settings) {
		return new NetheriteBlockBlock(settings);
	}
}
