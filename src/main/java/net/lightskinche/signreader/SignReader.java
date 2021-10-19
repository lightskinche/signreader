package src.main.net.lightskinche.signreader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

import org.lwjgl.glfw.GLFW;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SignReader.MOD_ID, value = Dist.CLIENT)
@Mod(SignReader.MOD_ID)
public class SignReader
{
	public static final String MOD_ID = "signreader";
	
	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event)
	{
		Minecraft client = Minecraft.getInstance();
		
		if (event.getAction() == GLFW.GLFW_PRESS && event.getKey() == GLFW.GLFW_KEY_R && client.player != null)
		{
			try 
			{
				File signFile = new File(client.gameDirectory, "signs.txt");
				FileWriter fileWriter = new FileWriter(signFile);
				
				if (!signFile.exists())
				{
					signFile.createNewFile();
				}
				
				World level = client.level;
				BlockPos playerPos = client.player.blockPosition();
				BlockPos startPos = new BlockPos(playerPos.getX() - 100, 0, playerPos.getZ() - 100);
				BlockPos endPos = new BlockPos(playerPos.getX() + 100, 100, playerPos.getZ() + 100);			
				for (BlockPos pos : BlockPos.betweenClosed(startPos, endPos))
				{
					if (client.level.getBlockState(pos).is(Blocks.OAK_SIGN))
					{
						fileWriter.write("(" + pos.toShortString() + ")" + System.lineSeparator());
					}
				}
				fileWriter.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
