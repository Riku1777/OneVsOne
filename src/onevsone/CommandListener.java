package onevsone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CommandListener extends JavaPlugin implements Listener
{
    
    HashMap<String, String> setupstatus = new HashMap<String, String>();
    HashMap<String, String> setuparena = new HashMap<String, String>();
    HashMap<String, Player> waitingplayer = new HashMap<String, Player>();
    HashMap<String, Player> battleplayer = new HashMap<String, Player>();
    HashMap<Player, String> PlayerArena = new HashMap<Player, String>();
    HashMap<Player, Integer> WIn = new HashMap<Player, Integer>();
    HashMap<Player, Location> temploc = new HashMap<Player, Location>();
    HashMap<Player, Double> temphealth = new HashMap<Player, Double>();
    HashMap<Player, Integer> tempfood = new HashMap<Player, Integer>();
    HashMap<Player, Integer> tempXP = new HashMap<Player, Integer>();
    HashMap<Player, ItemStack[]> temparmor = new HashMap<Player, ItemStack[]>();
    HashMap<Player, ItemStack[]> tempContent = new HashMap<Player, ItemStack[]>();
    HashMap<Player, Boolean> Winer = new HashMap<Player, Boolean>();
    
    List<Player> nomove = new ArrayList<Player>();
    List<Player> waiting = new ArrayList<Player>();
    List<Player> muteki = new ArrayList<Player>();
    
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        reloadConfig();
        
    }
    public void onDisable()
    {
        saveConfig();
    }
    
    public Boolean BAKADESU = false;
    public boolean onCommand(CommandSender sender, Command cmd,
            String commandLabel, final String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("1vs1"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage("§C§L[OneVsOne] §R§F§LPlayer only!");
            }
            final Player me = (Player) sender;
            if (args[0] == null)
            {
                viewhelp((Player) sender);
            }
            else if (args[0].equalsIgnoreCase("createarena"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou already configration!");
                    return true;
                }
                if (args[1] == null)
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LNot set arena name! type /1vs1 createarena _ARENANAME_");
                    return true;
                }
                else
                {
                    setupstatus.put(sender.getName(), "startcreate");
                    sender.sendMessage("§C§L[OneVsOne] §R§F§L/1vs1 setspawn1 to set blue player spawn location");
                    this.getConfig().set(args[1] + ".enable", false);
                    setuparena.put(sender.getName(), args[1]);
                    return true;
                }
                
            }
            else if (args[0].equalsIgnoreCase("setspawn1"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    if (setupstatus.get(sender.getName()).equals("startcreate"))
                    {
                        getConfig().set(
                                setuparena.get(sender.getName())
                                        + ".spawn.1.world",
                                me.getLocation().getWorld().getName());
                        getConfig()
                                .set(setuparena.get(sender.getName())
                                        + ".spawn.1.x", me.getLocation().getX());
                        getConfig()
                                .set(setuparena.get(sender.getName())
                                        + ".spawn.1.y", me.getLocation().getY());
                        getConfig()
                                .set(setuparena.get(sender.getName())
                                        + ".spawn.1.z", me.getLocation().getZ());
                        getConfig().set(
                                setuparena.get(sender.getName())
                                        + ".spawn.1.yaw",
                                me.getLocation().getYaw());
                        getConfig().set(
                                setuparena.get(sender.getName())
                                        + ".spawn.1.pitch",
                                me.getLocation().getPitch());
                        setupstatus.put(sender.getName(), "setspawn1");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§LSet Blue player spawn Location!");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§L/1vs1 setspawn2 to set red player spawn location");
                        return true;
                    }
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou not setup arena or 順序 is invaild!");
                }
            }
            else if (args[0].equalsIgnoreCase("setspawn2"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    if (setupstatus.get(sender.getName()).equals("setspawn1"))
                    {
                        getConfig().set(
                                setuparena.get(sender.getName())
                                        + ".spawn.2.world",
                                me.getLocation().getWorld().getName());
                        getConfig()
                                .set(setuparena.get(sender.getName())
                                        + ".spawn.2.x", me.getLocation().getX());
                        getConfig()
                                .set(setuparena.get(sender.getName())
                                        + ".spawn.2.y", me.getLocation().getY());
                        getConfig()
                                .set(setuparena.get(sender.getName())
                                        + ".spawn.2.z", me.getLocation().getZ());
                        getConfig().set(
                                setuparena.get(sender.getName())
                                        + ".spawn.2.yaw",
                                me.getLocation().getYaw());
                        getConfig().set(
                                setuparena.get(sender.getName())
                                        + ".spawn.2.pitch",
                                me.getLocation().getPitch());
                        setupstatus.put(sender.getName(), "setspawn2");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§LSet Red player spawn Location!");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§L/1vs1 setinv to Set Inventory!");
                        return true;
                    }
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou not setup arena or 順序 is invaild!");
                }
            }
            else if (args[0].equalsIgnoreCase("setinv"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    if (setupstatus.get(sender.getName()).equals("setspawn2"))
                    {
                        ItemStack[] Armor = me.getInventory()
                                .getArmorContents();
                        for (int i = 0; i < Armor.length; i++)
                        {
                            getConfig().set(
                                    setuparena.get(sender.getName())
                                            + ".invArmors." + i, Armor[i]);
                        }
                        
                        ItemStack[] Items = me.getInventory().getContents();
                        for (int i = 0; i < Items.length; i++)
                        {
                            getConfig().set(
                                    setuparena.get(sender.getName())
                                            + ".invItems." + i, Items[i]);
                        }
                        
                        setupstatus.put(sender.getName(), "setinv");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§LSet Inventory!");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§L/1vs1 savearena  is save arena!");
                        return true;
                    }
                    sender.sendMessage("You not setup arena or 順序 is invaild!");
                }
            }
            else if (args[0].equalsIgnoreCase("savearena"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    if (setupstatus.get(sender.getName()).equals("setinv"))
                    {
                        
                        sender.sendMessage("§C§L[OneVsOne] §R§F§LSaved arena! YEAH!");
                        sender.sendMessage("§C§L[OneVsOne] §R§F§L/1vs1 enable <ArenaName> is enable stage!");
                        setupstatus.remove(me.getName());
                        saveConfig();
                        return true;
                    }
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou not setup arena or 順序 is invaild!");
                }
            }
            else if (args[0].equalsIgnoreCase("author"))
            {
                me.sendMessage("§C§L[OneVsOne] §R§F§LAuthor is Riku177!(OfficialZED)");
                return true;
                
            }
            else if (args[0].equalsIgnoreCase("enable"))
            {
                if (args[1] == null)
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LNot set arena name! type /1vs1 enable _ARENANAME_");
                    return true;
                }
                else
                {
                    
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LEnabled! HAHA!");
                    this.getConfig().set(args[1] + ".enable", true);
                    this.saveConfig();
                    return true;
                }
                
            }
            else if (args[0].equalsIgnoreCase("leave"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou already configration!");
                    return true;
                }
                if (waiting.contains(me))
                {
                    String arena = PlayerArena.get(me);
                    waiting.remove(me);
                    waitingplayer.remove(arena);
                    PlayerArena.remove(me);
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou leave arena "
                            + arena);
                    return true;
                }
                else
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou not joined! or You in Game!");
                    return true;
                }
                
            }
            else if (args[0].equalsIgnoreCase("join"))
            {
                if (setupstatus.containsKey(sender.getName()))
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou already configration!");
                    return true;
                }
                if (args[1] == null)
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LNot found that arena!");
                    return true;
                }
                String stage = getConfig().getString(args[1] + ".enable");
                if (stage == null || stage.equals("false"))
                {
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LNot found that arena! or Not Enable that arena! type /1vs1 enable <ArenaName>");
                    return true;
                }
                if (PlayerArena.containsKey(me))
                {
                    
                    sender.sendMessage("§C§L[OneVsOne] §R§F§LYou already joined arena! : "
                            + args[1]);
                    return true;
                }
                final Player red = waitingplayer.get(args[1]);
                if (PlayerArena != null && red != null)
                {
                    if (red.equals(me) || PlayerArena.containsKey(me)
                            || waiting.contains(me))
                    {
                        sender.sendMessage("§C§L[OneVsOne] §R§F§LYou already joined arena! : "
                                + args[1]);
                        return true;
                    }
                }
                
                sender.sendMessage("§C§L[OneVsOne] §R§F§LYou joined arena: "
                        + args[1]);
                if (waitingplayer.containsKey(args[1]))
                {
                    
                    if (red.equals(me) || PlayerArena.containsKey(me)
                            || waiting.contains(me))
                    {
                        sender.sendMessage("§C§L[OneVsOne] §R§F§LYou already joined arena! : "
                                + args[1]);
                        return true;
                    }
                    waitingplayer.remove(args[1]);
                    waiting.remove(me);
                    waiting.remove(red);
                    try
                    {
                        startarena(args[1], me, red, false);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO 自動生成された catch ブロック
                        e.printStackTrace();
                    }
                }
                else
                {
                    waitingplayer.put(args[1], me);
                    waiting.add(me);
                    Bukkit.getServer().broadcastMessage(
                            "§C§L[OneVsOne] §R§2§L" + me.getName()
                                    + " Joined Arena " + args[1]
                                    + " ! /1vs1 join " + args[1]);
                }
                return true;
                
            }
            
        }
        
        viewhelp((Player) sender);
        return true;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        // Called when a player leaves a server
        Player player = event.getPlayer();
        // Player InGame Arena?
        if (PlayerArena.containsKey(player))
        {
            String arena = PlayerArena.get(player);
            stoparena(arena, true);
            
        }
        // Player Waiting Arena?
        else if (waiting.contains(player) || waitingplayer.containsKey(player))
        {
            String arena = PlayerArena.get(player);
            waiting.remove(player);
            waitingplayer.remove(arena);
            PlayerArena.remove(player);
        }
        
    }
    
    public void startarena(final String arenaname, final Player blue,
            final Player red, final Boolean restart)
            throws InterruptedException
    {
        // あえて非同期
        // ClearPotion
        for (PotionEffect potion : blue.getActivePotionEffects())
        {
            blue.removePotionEffect(potion.getType());
        }
        for (PotionEffect potion : red.getActivePotionEffects())
        {
            red.removePotionEffect(potion.getType());
        }
        if (restart == false)
        {
            
            // BACKUP BACKUP BACKUP
            // health
            temphealth.put(red, red.getHealth());
            temphealth.put(blue, blue.getHealth());
            
            // FoodLevel
            tempfood.put(red, red.getFoodLevel());
            tempfood.put(blue, blue.getFoodLevel());
            
            // Xperiens!
            tempXP.put(red, red.getTotalExperience());
            tempXP.put(blue, blue.getTotalExperience());
            
            // Location
            temploc.put(red, red.getLocation());
            temploc.put(blue, blue.getLocation());
            
            // Armor
            temparmor.put(red, red.getInventory().getArmorContents());
            temparmor.put(blue, blue.getInventory().getArmorContents());
            
            // Content
            tempContent.put(red, red.getInventory().getContents());
            tempContent.put(blue, blue.getInventory().getContents());
        }
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
            @Override
            public void run()
            {
                
                try
                {
                    
                    ItemStack[] Armor = new ItemStack[4];
                    ItemStack[] Items = new ItemStack[36];
                    Location blueloc = new Location(Bukkit.getWorld(getConfig()
                            .getString(arenaname + ".spawn.1.world")), Double
                            .parseDouble(getConfig().getString(
                                    arenaname + ".spawn.1.x")), Double
                            .parseDouble(getConfig().getString(
                                    arenaname + ".spawn.1.y")), Double
                            .parseDouble(getConfig().getString(
                                    arenaname + ".spawn.1.z")), Float
                            .parseFloat(getConfig().getString(
                                    arenaname + ".spawn.1.yaw")), Float
                            .parseFloat(getConfig().getString(
                                    arenaname + ".spawn.1.pitch")));
                    Location redloc = new Location(Bukkit.getWorld(getConfig()
                            .getString(arenaname + ".spawn.2.world")), Double
                            .parseDouble(getConfig().getString(
                                    arenaname + ".spawn.2.x")), Double
                            .parseDouble(getConfig().getString(
                                    arenaname + ".spawn.2.y")), Double
                            .parseDouble(getConfig().getString(
                                    arenaname + ".spawn.2.z")), Float
                            .parseFloat(getConfig().getString(
                                    arenaname + ".spawn.2.yaw")), Float
                            .parseFloat(getConfig().getString(
                                    arenaname + ".spawn.2.pitch")));
                    
                    try
                    {
                        for (int i = 0; i < Armor.length; i++)
                        {
                            Armor[i] = getConfig().getItemStack(
                                    arenaname + ".invArmors." + i);
                        }
                        for (int i = 0; i < Items.length; i++)
                        {
                            Items[i] = getConfig().getItemStack(
                                    arenaname + ".invItems." + i);
                        }
                    }
                    catch (Exception e)
                    {
                    }
                    //無敵
                    muteki.add(red);
                    muteki.add(blue);
                    
                    red.getInventory().setArmorContents(Armor);
                    red.getInventory().setContents(Items);
                    blue.getInventory().setArmorContents(Armor);
                    blue.getInventory().setContents(Items);
                    
                    red.teleport(redloc);
                    blue.teleport(blueloc);
                    
                    nomove.add(red);
                    nomove.add(blue);
                    
                    red.setMaxHealth(20);
                    blue.setMaxHealth(20);
                    
                    red.setHealth(20);
                    blue.setHealth(20);
                    
                    red.setFoodLevel(20);
                    blue.setFoodLevel(20);
                    
                    Thread.sleep(1000);
                    blue.sendMessage("§C§L[OneVsOne] §R§A§LStarting 5 seconds!");
                    red.sendMessage("§C§L[OneVsOne] §R§A§LStarting 5 seconds!");
                    
                    Thread.sleep(1000);
                    
                    blue.sendMessage("§C§L[OneVsOne] §R§A§LStarting 4 seconds!");
                    red.sendMessage("§C§L[OneVsOne] §R§A§LStarting 4 seconds!");
                    
                    red.playSound(redloc, Sound.NOTE_PLING, 2, 1);
                    blue.playSound(blueloc, Sound.NOTE_PLING, 2, 1);
                    
                    Thread.sleep(1000);
                    
                    blue.sendMessage("§C§L[OneVsOne] §R§A§LStarting 3 seconds!");
                    red.sendMessage("§C§L[OneVsOne] §R§A§LStarting 3 seconds!");
                    
                    red.playSound(redloc, Sound.NOTE_PLING, 2, 1);
                    blue.playSound(blueloc, Sound.NOTE_PLING, 2, 1);
                    
                    Thread.sleep(1000);
                    
                    blue.sendMessage("§C§L[OneVsOne] §R§A§LStarting 2 seconds!");
                    red.sendMessage("§C§L[OneVsOne] §R§A§LStarting 2 seconds!");
                    
                    red.playSound(redloc, Sound.NOTE_PLING, 2, 1);
                    blue.playSound(blueloc, Sound.NOTE_PLING, 2, 1);
                    
                    Thread.sleep(1000);
                    blue.sendMessage("§C§L[OneVsOne] §R§A§LStarting 1 seconds!");
                    red.sendMessage("§C§L[OneVsOne] §R§A§LStarting 1 seconds!");
                    
                    red.playSound(redloc, Sound.NOTE_PLING, 2, 1);
                    blue.playSound(blueloc, Sound.NOTE_PLING, 2, 1);
                    
                    Thread.sleep(1000);
                    blue.sendMessage("§C§L[OneVsOne] §R§A§LStart!");
                    red.sendMessage("§C§L[OneVsOne] §R§A§LStart!");
                    red.playSound(redloc, Sound.NOTE_PLING, 2, 3);
                    blue.playSound(blueloc, Sound.NOTE_PLING, 2, 3);
                    
                    battleplayer.put(arenaname + ":red", red);
                    battleplayer.put(arenaname + ":blue", blue);
                    
                    PlayerArena.put(red, arenaname);
                    PlayerArena.put(blue, arenaname);
                    
                    nomove.remove(red);
                    nomove.remove(blue);
                    
                    //無敵解除
                    muteki.remove(red);
                    muteki.remove(blue);
                    
                }
                catch (InterruptedException e)
                {
                    // TODO 自動生成された catch ブロック
                    e.printStackTrace();
                }
            }
            
        }, 1);
        
    }
    
    public void stoparena(final String arenaname, final Boolean quick)
    {
        
        final Player red = battleplayer.get(arenaname + ":red");
        final Player blue = battleplayer.get(arenaname + ":blue");
        
        //無敵
        muteki.add(red);
        muteki.add(blue);
        
        PlayerArena.remove(red);
        PlayerArena.remove(blue);
        battleplayer.remove(arenaname);
        waitingplayer.remove(red);
        waitingplayer.remove(blue);
        nomove.remove(red);
        nomove.remove(blue);
        WIn.remove(red);
        WIn.remove(blue);
        
        final Player WonPlayer;
        final Player LostPlayer;
        
        if (Winer.containsKey(red) && quick == false)
        {
            // red WON FireWork
            FireworkEffect.Type[] types =
            {FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE,
                    FireworkEffect.Type.BURST, FireworkEffect.Type.CREEPER,
                    FireworkEffect.Type.STAR,};
            Random rand = new Random();
            Location loc = red.getLocation();
            
            Firework firework = loc.getWorld().spawn(loc, Firework.class);
            
            // 花火の設定情報オブジェクトを取り出す
            FireworkMeta meta = firework.getFireworkMeta();
            Builder effect = FireworkEffect.builder();
            
            // 形状をランダムに決める
            effect.with(types[rand.nextInt(types.length)]);
            
            // 基本の色を単色～5色以内でランダムに決める
            effect.withColor(getRandomCrolors(1 + rand.nextInt(5)));
            
            // 余韻の色を単色～3色以内でランダムに決める
            effect.withFade(getRandomCrolors(1 + rand.nextInt(3)));
            
            // 爆発後に点滅するかをランダムに決める
            effect.flicker(true);
            
            // 爆発後に尾を引くかをランダムに決める
            effect.trail(true);
            
            // 打ち上げ高さを1以上4以内でランダムに決める
            meta.setPower(2);
            
            // 花火の設定情報を花火に設定
            meta.addEffect(effect.build());
            firework.setFireworkMeta(meta);
            
            red.playSound(red.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
            
            WonPlayer = red;
            LostPlayer = blue;
            Winer.remove(red);
            LostPlayer.addPotionEffect(new PotionEffect(
                    PotionEffectType.INVISIBILITY, 20 * 5, 250));
        }
        else
        {
            if (quick == false)
            {
                // Blue Won FIrework
                FireworkEffect.Type[] types =
                {FireworkEffect.Type.BALL, FireworkEffect.Type.BALL_LARGE,
                        FireworkEffect.Type.BURST, FireworkEffect.Type.CREEPER,
                        FireworkEffect.Type.STAR,};
                Random rand = new Random();
                Location loc = blue.getLocation();
                
                Firework firework = loc.getWorld().spawn(loc, Firework.class);
                
                // 花火の設定情報オブジェクトを取り出す
                FireworkMeta meta = firework.getFireworkMeta();
                Builder effect = FireworkEffect.builder();
                
                // 形状をランダムに決める
                effect.with(types[rand.nextInt(types.length)]);
                
                // 基本の色を単色～5色以内でランダムに決める
                effect.withColor(getRandomCrolors(1 + rand.nextInt(5)));
                
                // 余韻の色を単色～3色以内でランダムに決める
                effect.withFade(getRandomCrolors(1 + rand.nextInt(3)));
                
                // 爆発後に点滅するかをランダムに決める
                effect.flicker(true);
                
                // 爆発後に尾を引くかをランダムに決める
                effect.trail(true);
                
                // 打ち上げ高さを1以上4以内でランダムに決める
                meta.setPower(2);
                
                // 花火の設定情報を花火に設定
                meta.addEffect(effect.build());
                firework.setFireworkMeta(meta);
                
                blue.playSound(blue.getLocation(), Sound.FIREWORK_BLAST, 1, 1);
                
                Winer.remove(blue);
                
            }
            WonPlayer = blue;
            LostPlayer = red;
            if (quick == false)
            {
                LostPlayer.addPotionEffect(new PotionEffect(
                        PotionEffectType.INVISIBILITY, 20 * 5, 250));
            }
        }
        
        Bukkit.getServer().broadcastMessage(
                "§C§L[OneVsOne] §R§E§L" + LostPlayer.getName() + " vs "
                        + WonPlayer.getName() + ". Win Player : "
                        + WonPlayer.getName());
        
       
        
        
        Bukkit.getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
            @Override
            public void run()
            {
                
                // プレイヤーの位置とかインベントリとかもとにもどす
                // Teleport!
                try
                {
                    red.teleport(temploc.get(red));
                    blue.teleport(temploc.get(blue));
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    Bukkit.getServer().broadcastMessage(
                            "§4Location Recovery Error!");
                }
                try
                {
                    red.setHealth(temphealth.get(red));
                    blue.setHealth(temphealth.get(blue));
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    Bukkit.getServer().broadcastMessage(
                            "§4health Recovery Error!");
                }
                // SetHealth!
                
                // SetXP!
                try
                {
                    red.setTotalExperience(tempXP.get(red));
                    blue.setTotalExperience(tempXP.get(blue));
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    Bukkit.getServer().broadcastMessage("§4XP Recovery Error!");
                }
                
                // SetFoodLevel!
                red.setFoodLevel(tempfood.get(red));
                blue.setFoodLevel(tempfood.get(blue));
                // SetArmor!
                try
                {
                    red.getInventory().setArmorContents(temparmor.get(red));
                    blue.getInventory().setArmorContents(temparmor.get(blue));
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    Bukkit.getServer().broadcastMessage(
                            "§4Armor Recovery Error!");
                }
                
                // SetContent!
                try
                {
                    red.getInventory().setContents(tempContent.get(red));
                    blue.getInventory().setContents(tempContent.get(blue));
                }
                catch (Exception e)
                {
                    // TODO: handle exception
                    Bukkit.getServer().broadcastMessage(
                            "§4Inventory Recovery Error!");
                }
                
                red.updateInventory();
                blue.updateInventory();
                
            }
        }, 100);
        //無敵解除
        muteki.remove(red);
        muteki.remove(blue);
        
    }
    public void viewhelp(Player pl)
    {
        pl.sendMessage("§E-----OneVsOne help-----");
        pl.sendMessage("§E/1vs1 createarena <Arena Name>: Start arena setup wizard");
        pl.sendMessage("§E/1vs1 enable <Arena Name>: Enable arena");
        pl.sendMessage("§E/1vs1 join <Arena Name>: Join arena");
        pl.sendMessage("§E/1vs1 leave <Arena Name>: Leave  arena");
        pl.sendMessage("§E----In setup wizard command----");
        pl.sendMessage("§E/1vs1 setspawn1: Set Spawn Location Player1");
        pl.sendMessage("§E/1vs1 setspawn2: Set Spawn Location Player2");
        pl.sendMessage("§E/1vs1 setinv: Set arena Inventory");
        pl.sendMessage("§E/1vs1 savearena: Save arena information");
    }
    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent event)
    {
        if (nomove != null)
        {
            if (nomove.contains(event.getPlayer()))
            {
                Location loc = event.getFrom();
                loc.setX(loc.getBlockX() + 0.5);
                loc.setY(loc.getBlockY());
                loc.setZ(loc.getBlockZ() + 0.5);
                event.getPlayer().teleport(loc);
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void OnPlayerBreak(BlockPlaceEvent event)
    {
        if (PlayerArena != null)
        {
            if (PlayerArena.containsKey(event.getPlayer()))
            {
                Block bl = event.getBlock();
                if(event.getBlock().equals(bl))
                {
                    
                }
            }
        }
    }
    
    @EventHandler
    public void OnPlayerDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            // PlayerDeath
            Player Damaged = (Player) event.getEntity();
            if(muteki.contains(Damaged))
            {
                event.setDamage(0);
                return;
            }
            if (Damaged.getHealth() <= event.getDamage())
            { // Dead
                if (battleplayer != null)
                {
                    if (PlayerArena.containsKey(Damaged))
                    {
                        Player red = null;
                        Player blue = null;
                        if (battleplayer.containsKey(PlayerArena.get(Damaged)
                                + ":red"))
                        {
                            red = battleplayer.get(PlayerArena.get(Damaged)
                                    + ":red");
                            blue = battleplayer.get(PlayerArena.get(Damaged)
                                    + ":blue");
                        }
                        else if (battleplayer.containsKey(PlayerArena
                                .get(Damaged) + ":blue"))
                        {
                            blue = battleplayer.get(PlayerArena.get(Damaged)
                                    + ":blue");
                            red = battleplayer.get(PlayerArena.get(Damaged)
                                    + ":red");
                            
                        }
                        
                        final String Arenaname = PlayerArena.get(red);
                        
                        if (Damaged.equals(red))
                        {
                            
                            if (!(WIn.containsKey(blue)))
                            {
                                WIn.put(blue, 1);
                            }
                            else
                            {
                                WIn.put(blue, WIn.get(blue) + 1);
                                
                            }
                            if (!WIn.containsKey(red))
                            {
                                WIn.put(red, 0);
                            }
                            Integer kill = WIn.get(blue) + WIn.get(red);
                            
                            if (kill >= 2)
                            {
                                if (PlayerArena.get(blue).equals(
                                        PlayerArena.get(red)))
                                {
                                    if (WIn.get(red) >= 2)
                                    {
                                        red.sendMessage("§C§L[OneVsOne] §R§2§LYou Won This Game!");
                                        blue.sendMessage("§C§L[OneVsOne] §R§8§LYou Lost This Game!");
                                        red.setHealth(20);
                                        blue.setHealth(20);
                                        muteki.add(red);
                                        muteki.add(blue);
                                        event.setDamage(0);
                                        Winer.put(red, true);
                       
                                        stoparena(PlayerArena.get(red), false);
                                        return;
                                    }
                                    else if (WIn.get(blue) >= 2)
                                    {
                                        
                                        blue.sendMessage("§C§L[OneVsOne] §R§2§LYou Won This Game!");
                                        red.sendMessage("§C§L[OneVsOne] §R§8§LYou Lost This Game!");
                                        red.setHealth(20);
                                        blue.setHealth(20);
                                        muteki.add(red);
                                        muteki.add(blue);
                                        event.setDamage(0);
                                        Winer.put(blue, true);
                                        stoparena(PlayerArena.get(red), false);
                                        return;
                                        
                                    }
                                }
                            }
                            blue.sendMessage("§C§L[OneVsOne] §R§2§LYou Kill! HAH! kill:"
                                    + WIn.get(blue));
                            red.sendMessage("§C§L[OneVsOne] §R§8§LYou Killed! ah!");
                            blue.playSound(blue.getLocation(), Sound.LEVEL_UP,
                                    2, 1);
                            
                            try
                            {
                                event.setDamage(0);
                                startarena(Arenaname, blue, red, true);
                            }
                            catch (InterruptedException e)
                            {
                                // TODO 自動生成された catch ブロック
                                e.printStackTrace();
                            }
                            
                        }
                        else if (Damaged.equals(blue))
                        {
                            if (!(WIn.containsKey(red)))
                            {
                                WIn.put(red, 1);
                            }
                            else
                            {
                                WIn.put(red, WIn.get(red) + 1);
                            }
                            if (!WIn.containsKey(blue))
                            {
                                WIn.put(blue, 0);
                            }
                            Integer kill = WIn.get(red) + WIn.get(blue);
                            if (kill >= 2)
                            {
                                if (PlayerArena.get(blue).equals(
                                        PlayerArena.get(red)))
                                {
                                    if (WIn.get(red) >= 2)
                                    {
                                        red.sendMessage("§C§L[OneVsOne] §R§2§LYou Won This Game!");
                                        blue.sendMessage("§C§L[OneVsOne] §R§8§LYou Lost This Game!");
                                        red.setHealth(20);
                                        blue.setHealth(20);
                                        muteki.add(red);
                                        muteki.add(blue);
                                        event.setDamage(0);
                                        Winer.put(red, true);
                                        stoparena(PlayerArena.get(red), false);
                                        return;
                                    }
                                    else if (WIn.get(blue) >= 2)
                                    {
                                        blue.sendMessage("§C§L[OneVsOne] §R§2§LYou Won This Game!");
                                        red.sendMessage("§C§L[OneVsOne] §R§8§LYou Lost This Game!");
                                        red.setHealth(20);
                                        blue.setHealth(20);
                                        muteki.add(red);
                                        muteki.add(blue);
                                        event.setDamage(0);
                                        Winer.put(blue, true);
                                        stoparena(PlayerArena.get(red), false);
                                        return;
                                    }
                                }
                            }
                            red.sendMessage("§C§L[OneVsOne] §R§2§LYou Kill! HAH! kill:"
                                    + WIn.get(red));
                            blue.sendMessage("§C§L[OneVsOne] §R§8§LYou Killed! ah!");
                            red.playSound(blue.getLocation(), Sound.LEVEL_UP,
                                    2, 1);
                            event.setDamage(0);
                            try
                            {
                                startarena(Arenaname, blue, red, true);
                            }
                            catch (InterruptedException e)
                            {
                                // TODO 自動生成された catch ブロック
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
    private Color[] getRandomCrolors(int length)
    {
        // 配列を作る
        Color[] colors = new Color[length];
        Random rand = new Random();
        // 配列の要素を順に処理していく
        for (int n = 0; n != length; n++)
        {
            // 24ビットカラーの範囲でランダムな色を決める
            colors[n] = Color.fromBGR(rand.nextInt(1 << 24));
        }
        
        // 配列を返す
        return colors;
    }
    
    
    
    
    
    
    
    
    
    
    
    
}
