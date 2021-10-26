package src.main.net.lightskinche.signreader;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.mojang.datafixers.types.templates.List;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import java.util.*;
@EventBusSubscriber(bus = Bus.FORGE, modid = SignReader.MOD_ID, value = Dist.CLIENT)
@Mod(SignReader.MOD_ID)
public class SignReader
{
	public static final String MOD_ID = "signreader";
	
	@SubscribeEvent
	public static void onWorldTick(TickEvent.WorldTickEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		try 
		{
			File signFile = new File(client.gameDirectory, "signs.txt");
			FileWriter fileWriter = new FileWriter(signFile);
			World level = event.world;
			if (!signFile.exists())
			{
					signFile.createNewFile();
			}
			Chunk current_chunk = level.getChunk(client.player.blockPosition().getX(), client.player.blockPosition().getZ());
			//TODO: Find out why we don't get any block entities from here
			Map<BlockPos, TileEntity> entities = current_chunk.getBlockEntities();
			for(BlockPos pos : current_chunk.getBlockEntitiesPos()) {
				fileWriter.write(pos.toShortString());
			}
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
