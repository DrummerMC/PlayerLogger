package playerLogger;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import java.io.File;
import java.io.PrintStream;

@Mod(modid = PlayerLogger.MODID, version = PlayerLogger.VERSION, acceptableRemoteVersions = "*")
public class PlayerLogger
{
    public static final String MODID = "playerLogger";
    public static final String VERSION = "1.0";
    private LoggerPlayerActivity mPlayerLogger;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        File tFile = new File(event.getModConfigurationDirectory().getParentFile(), "logs/PlayerActivity.log");
        if (!tFile.exists()) {try {tFile.createNewFile();} catch (Throwable e) {}}
        try {mPlayerLogger = new LoggerPlayerActivity(new PrintStream(tFile));} catch (Throwable e) {}
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (mPlayerLogger != null) new Thread(mPlayerLogger).start();
    }
}
