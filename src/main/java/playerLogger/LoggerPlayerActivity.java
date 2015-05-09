package playerLogger;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class LoggerPlayerActivity implements Runnable {
    private ArrayList<String> mBufferedPlayerActivity = new ArrayList<String>();

    public static PrintStream mLog = null;

    public LoggerPlayerActivity(PrintStream aLog) {
        MinecraftForge.EVENT_BUS.register(this);
        mLog = aLog;
    }

    @SubscribeEvent
    public void onPlayerInteraction(PlayerInteractEvent aEvent) {
        if (aEvent.entityPlayer != null && aEvent.entityPlayer.worldObj != null && aEvent.action != null && aEvent.world.provider != null && !aEvent.entityPlayer.worldObj.isRemote && aEvent.action != null && aEvent.action != PlayerInteractEvent.Action.RIGHT_CLICK_AIR && mLog != null) mBufferedPlayerActivity.add(dateAndTime()+";"+aEvent.action.name()+";"+aEvent.entityPlayer.getDisplayName()+";DIM:"+aEvent.world.provider.dimensionId+";"+aEvent.x+";"+aEvent.y+";"+aEvent.z+";|;"+aEvent.x/10+";"+aEvent.y/10+";"+aEvent.z/10);
    }

    @SubscribeEvent
    public void onBlockHarvestingEvent(BlockEvent.HarvestDropsEvent aEvent) {
        if (aEvent.harvester != null && !aEvent.world.isRemote && mLog != null) mBufferedPlayerActivity.add(dateAndTime()+";HARVEST_BLOCK;"+aEvent.harvester.getDisplayName()+";DIM:"+aEvent.world.provider.dimensionId+";"+aEvent.x+";"+aEvent.y+";"+aEvent.z+";|;"+aEvent.x/10+";"+aEvent.y/10+";"+aEvent.z/10);
    }

    @Override
    public void run() {
        while (true) {try {
            if (mLog == null) return;
            ArrayList<String> tList = mBufferedPlayerActivity;
            mBufferedPlayerActivity = new ArrayList<String>();
            String tLastOutput = "";
            for (int i = 0, j = tList.size(); i < j; i++) {
                if (!tLastOutput.equals(tList.get(i))) mLog.println(tList.get(i));
                tLastOutput = tList.get(i);
            }
            Thread.sleep(10000);
        } catch(Throwable e) {/**/}}
    }

    private static final DateFormat sDateFormat = DateFormat.getInstance();
    public static String dateAndTime() {
        return sDateFormat.format(new Date());
    }
}