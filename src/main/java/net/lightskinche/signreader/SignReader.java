package src.main.net.lightskinche.signreader;

import java.io.File;


import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import com.mojang.datafixers.types.templates.List;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkDataEvent;
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
	public static void onClientTick(TickEvent.ClientTickEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		try 
		{
			File signFile = new File(client.gameDirectory, "signs.txt");
			FileWriter fileWriter = new FileWriter(signFile);
			World level = client.level;
			if (!signFile.exists())
			{
					signFile.createNewFile();
			}
            for(Chunk current_chunk : getChunkRadius(7 * 16, 7 * 16, client.player.blockPosition(), level)) {
			Map<BlockPos, TileEntity> entities = current_chunk.getBlockEntities();
			for(BlockPos pos : current_chunk.getBlockEntitiesPos()) {
				TileEntity tile = entities.get(pos);
				if(tile.getClass() == SignTileEntity.class) {
					SignTileEntity signtile = (SignTileEntity)tile;
					fileWriter.write("(" + pos.toShortString() + ")" + " : " + System.lineSeparator() +
					signtile.getMessage(0).getString() + System.lineSeparator() +
				    signtile.getMessage(1).getString() + System.lineSeparator() + 
				    signtile.getMessage(2).getString() + System.lineSeparator() + 
				    signtile.getMessage(3).getString() + System.lineSeparator());
				}
			}
            }
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//radiusX and radiusZ should be given in block coords and should be divisible by 16
	static ArrayList<Chunk> getChunkRadius(int radiusX, int radiusZ, BlockPos origin, World level){
		ArrayList<Chunk> chunks = new ArrayList();
		for(int i = -radiusX; i <= radiusX; i += 16) {
			for(int j = -radiusZ; j <= radiusZ; j += 16) {
				chunks.add(level.getChunkAt(new BlockPos(i + origin.getX(),origin.getY(),j + origin.getZ())));
			}
		}
		return chunks;
	}
}

