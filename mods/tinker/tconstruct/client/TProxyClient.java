package mods.tinker.tconstruct.client;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mods.tinker.common.fancyitem.FancyEntityItem;
import mods.tinker.common.fancyitem.FancyItemRender;
import mods.tinker.tconstruct.TConstruct;
import mods.tinker.tconstruct.TConstructRegistry;
import mods.tinker.tconstruct.TContent;
import mods.tinker.tconstruct.TProxyCommon;
import mods.tinker.tconstruct.client.entityrender.CartRender;
import mods.tinker.tconstruct.client.entityrender.CrystalRender;
import mods.tinker.tconstruct.client.entityrender.SkylaRender;
import mods.tinker.tconstruct.client.gui.ToolGuiElement;
import mods.tinker.tconstruct.entity.CartEntity;
import mods.tinker.tconstruct.entity.Crystal;
import mods.tinker.tconstruct.entity.Skyla;
import mods.tinker.tconstruct.logic.CastingTableLogic;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import org.w3c.dom.Document;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class TProxyClient extends TProxyCommon
{
	public static SmallFontRenderer smallFontRenderer;
	public static Icon blankSprite;

	/* Registers any rendering code. */
	public void registerRenderer ()
	{
		Minecraft mc = Minecraft.getMinecraft();
		smallFontRenderer = new SmallFontRenderer(mc.gameSettings, "/font/default.png", mc.renderEngine, false);
		RenderingRegistry.registerBlockHandler(new TableRender());
		RenderingRegistry.registerBlockHandler(new FrypanRender());
		RenderingRegistry.registerBlockHandler(new SmelteryRender());
		RenderingRegistry.registerBlockHandler(new TankRender());
		RenderingRegistry.registerBlockHandler(new SearedRender());
		RenderingRegistry.registerBlockHandler(new FluidRender());

		//Tools
		//MinecraftForgeClient.preloadTexture(TContent.blockTexture);
		/*IItemRenderer render = new SuperCustomToolRenderer();
		for (ToolCore tool : TConstructRegistry.tools)
		{
			MinecraftForgeClient.registerItemRenderer(tool.itemID, render);
		}*/

		//Special Renderers
		ClientRegistry.bindTileEntitySpecialRenderer(CastingTableLogic.class, new CastingTableSpecialRenderer());

		//Entities
		RenderingRegistry.registerEntityRenderingHandler(CartEntity.class, new CartRender());
		RenderingRegistry.registerEntityRenderingHandler(Skyla.class, new SkylaRender());
		RenderingRegistry.registerEntityRenderingHandler(FancyEntityItem.class, new FancyItemRender());
		RenderingRegistry.registerEntityRenderingHandler(Crystal.class, new CrystalRender());
		//RenderingRegistry.registerEntityRenderingHandler(net.minecraft.entity.player.EntityPlayer.class, new PlayerArmorRender()); // <-- Works, woo!

		addRenderMappings();
	}

	/* Ties an internal name to a visible one. */
	public void addNames ()
	{
		//LanguageRegistry.addName(TContent.lavaTank, "Lava Tank");
		/*LanguageRegistry.instance().addStringLocalization("itemGroup.TConstructTools", "TConstruct Tools");
		LanguageRegistry.instance().addStringLocalization("itemGroup.TConstructMaterials", "TConstruct Materials");
		LanguageRegistry.instance().addStringLocalization("itemGroup.TConstructBlocks", "TConstruct Blocks");*/

		String langDir = "/mods/tinker/resources/lang/";
		String[] langFiles = { "en_US.xml" };

		for (String langFile : langFiles)
		{
			try
			{
				LanguageRegistry.instance().loadLocalization(langDir + langFile, langFile.substring(langFile.lastIndexOf('/') + 1, langFile.lastIndexOf('.')), true);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		/*LanguageRegistry.instance().addStringLocalization("crafters.ToolStation", "Tool Station");
		LanguageRegistry.instance().addStringLocalization("crafters.PartBuilder", "Part Builder");
		LanguageRegistry.instance().addStringLocalization("crafters.PatternShaper", "Pattern Shaper");
		LanguageRegistry.instance().addStringLocalization("inventory.PatternChest", "Pattern Chest");
		LanguageRegistry.instance().addStringLocalization("crafters.Smeltery", "Smeltery");
		LanguageRegistry.instance().addStringLocalization("crafters.Frypan", "Frying Pan");

		LanguageRegistry.instance().addStringLocalization("ToolStation.Crafter.name", "Tool Station");
		LanguageRegistry.instance().addStringLocalization("ToolStation.Parts.name", "Part Builder");
		LanguageRegistry.instance().addStringLocalization("ToolStation.PatternChest.name", "Pattern Chest");
		LanguageRegistry.instance().addStringLocalization("ToolStation.PatternShaper.name", "Stencil Table");
		LanguageRegistry.instance().addStringLocalization("ToolStation.CastingTable.name", "Casting Table");

		LanguageRegistry.instance().addStringLocalization("CraftedSoil.Slime.name", "Slimy Mud");
		LanguageRegistry.instance().addStringLocalization("CraftedSoil.Grout.name", "Grout");

		LanguageRegistry.instance().addStringLocalization("MetalOre.NetherSlag.name", "Netherack Slag");
		LanguageRegistry.instance().addStringLocalization("MetalOre.Cobalt.name", "Cobalt Ore");
		LanguageRegistry.instance().addStringLocalization("MetalOre.Ardite.name", "Ardite Ore");
		LanguageRegistry.instance().addStringLocalization("MetalOre.Copper.name", "Copper Ore");
		LanguageRegistry.instance().addStringLocalization("MetalOre.Tin.name", "Tin Ore");
		LanguageRegistry.instance().addStringLocalization("MetalOre.Aluminum.name", "Aluminum Ore");
		LanguageRegistry.instance().addStringLocalization("MetalOre.Slag.name", "Stone Slag");

		LanguageRegistry.instance().addStringLocalization("Smeltery.Controller.name", "Smeltery");
		LanguageRegistry.instance().addStringLocalization("Smeltery.Drain.name", "Smeltery Drain");
		LanguageRegistry.instance().addStringLocalization("Smeltery.Brick.name", "Seared Bricks");
		LanguageRegistry.instance().addStringLocalization("Smeltery.Gague.name", "Seared Glass");
		LanguageRegistry.instance().addStringLocalization("Smeltery.Window.name", "Seared Window");*/

		for (int mat = 0; mat < materialTypes.length; mat++)
		{
			for (int type = 0; type < toolMaterialNames.length; type++)
			{
				String internalName = new StringBuilder().append("item.tconstruct.").append(materialTypes[mat]).append(".").append(toolMaterialNames[type]).append(".name").toString();
				String visibleName = new StringBuilder().append(toolMaterialNames[type]).append(materialNames[mat]).toString();
				LanguageRegistry.instance().addStringLocalization(internalName, "en_US", visibleName);
			}
		}

		for (int i = 0; i < shardNames.length; i++)
		{
			String internalName = "item.tconstruct.ToolShard." + toolMaterialNames[i] + ".name";
			String visibleName = shardNames[i];
			LanguageRegistry.instance().addStringLocalization(internalName, "en_US", visibleName);
		}

		for (int i = 0; i < materialItemNames.length; i++)
		{
			String internalName = "item.tconstruct.Materials." + materialItemInternalNames[i] + ".name";
			String visibleName = materialItemNames[i];
			LanguageRegistry.instance().addStringLocalization(internalName, "en_US", visibleName);
		}

		for (int i = 0; i < patterns.length; i++)
		{
			String internalName = "item.tconstruct.Pattern." + patterns[i] + ".name";
			String visibleName = patternNames[i] + " Pattern";
			LanguageRegistry.instance().addStringLocalization(internalName, "en_US", visibleName);
			internalName = "item.tconstruct.MetalPattern." + patterns[i] + ".name";
			visibleName = patternNames[i] + " Cast";
			LanguageRegistry.instance().addStringLocalization(internalName, "en_US", visibleName);
		}

		//LanguageRegistry.addName(TContent.manualBook, "Tinker's Log");

		LanguageRegistry.instance().addStringLocalization("item.tconstruct.diary.diary.name", "en_US", "Tinker's Log");
		LanguageRegistry.instance().addStringLocalization("item.tconstruct.Pattern.blank_pattern.name", "en_US", "Blank Pattern");
		LanguageRegistry.instance().addStringLocalization("item.tconstruct.Pattern.blank_cast.name", "en_US", "Cast");
		//LanguageRegistry.addName(TContent.blankPattern, "Blank Pattern");
		LanguageRegistry.addName(TContent.pickaxe, "Pickaxe");
		LanguageRegistry.addName(TContent.shovel, "Shovel");
		LanguageRegistry.addName(TContent.axe, "Axe");
		LanguageRegistry.addName(TContent.broadsword, "Broadsword");
		LanguageRegistry.addName(TContent.longsword, "Longsword");
		LanguageRegistry.addName(TContent.rapier, "Rapier");
		LanguageRegistry.addName(TContent.frypan, "Frying Pan");
		LanguageRegistry.addName(TContent.battlesign, "Battlesign");
		LanguageRegistry.addName(TContent.mattock, "Mattock");
		//LanguageRegistry.addName(TContent.lumberaxe, "Lumber Axe");

		/*LanguageRegistry.addName(TContent.ironFlowing, "Liquid Iron");
		LanguageRegistry.addName(TContent.ironStill, "Liquid Iron");
		LanguageRegistry.addName(TContent.goldFlowing, "Liquid Gold");
		LanguageRegistry.addName(TContent.goldStill, "Liquid Gold");
		LanguageRegistry.addName(TContent.copperFlowing, "Liquid Copper");
		LanguageRegistry.addName(TContent.copperStill, "Liquid Copper");
		LanguageRegistry.addName(TContent.tinFlowing, "Liquid Tin");
		LanguageRegistry.addName(TContent.tinStill, "Liquid Tin");
		LanguageRegistry.addName(TContent.aluminumFlowing, "Liquid Aluminum");
		LanguageRegistry.addName(TContent.aluminumStill, "Liquid Aluminum");
		LanguageRegistry.addName(TContent.cobaltFlowing, "Liquid Cobalt");
		LanguageRegistry.addName(TContent.cobaltStill, "Liquid Cobalt");
		LanguageRegistry.addName(TContent.arditeFlowing, "Liquid Ardite");
		LanguageRegistry.addName(TContent.arditeStill, "Liquid Ardite");

		LanguageRegistry.addName(TContent.bronzeFlowing, "Liquid Bronze");
		LanguageRegistry.addName(TContent.bronzeStill, "Liquid Bronze");
		LanguageRegistry.addName(TContent.alBrassFlowing, "Liquid Brass");
		LanguageRegistry.addName(TContent.alBrassStill, "Liquid Brass");
		LanguageRegistry.addName(TContent.alumiteFlowing, "Liquid Alumite");
		LanguageRegistry.addName(TContent.alumiteStill, "Liquid Alumite");
		LanguageRegistry.addName(TContent.manyullynFlowing, "Liquid Manyullyn");
		LanguageRegistry.addName(TContent.manyullynStill, "Liquid Manyullyn");
		LanguageRegistry.addName(TContent.obsidianFlowing, "Liquid Obsidian");
		LanguageRegistry.addName(TContent.obsidianStill, "Liquid Obsidian");
		LanguageRegistry.addName(TContent.steelFlowing, "Liquid Steel");
		LanguageRegistry.addName(TContent.steelStill, "Liquid Steel");*/

		addToolButtons();
	}

	public static final String[] shardNames = new String[] { "Wood", "Stone Shard", "Iron Chunk", "Flint Shard", "Cactus Shard", "Bone", "Obsidian Shard", "Netherrack Shard", "Slime Crystal Fragment", "Paper", "Cobalt Chunk", "Ardite Chunk", "Manyullyn Chunk", "Copper Chunk", "Bronze Chunk", "Alumite Chunk", "Steel Chunk" };

	public static final String[] materialItemInternalNames = new String[] { "PaperStack", "SlimeCrystal", "SearedBrick", "CobaltIngot", "ArditeIngot", "ManyullynIngot", "Mossball", "LavaCrystal", "NecroticBone", "CopperIngot", "TinIngot", "AluminumIngot", "RawAluminum", "BronzeIngot", "AlBrassIngot", "AlumiteIngot", "SteelIngot" };

	public static final String[] materialItemNames = new String[] { "Paper Stack", "Slime Crystal", "Seared Brick", "Cobalt Ingot", "Ardite Ingot", "Manyullyn Ingot", "Ball of Moss", "Lava Crystal", "Necrotic Bone", "Copper Ingot", "Tin Ingot", "Aluminum Ingot", "Raw Aluminum", "Bronze Ingot", "Aluminum Brass Ingot", "Alumite Ingot", "Steel Ingot" };

	public static final String[] toolMaterialNames = new String[] { "Wood", "Stone", "Iron", "Flint", "Cactus", "Bone", "Obsidian", "Netherrack", "Slime", "Paper", "Cobalt", "Ardite", "Manyullyn", "Copper", "Bronze", "Alumite", "Steel" };

	public static final String[] materialTypes = new String[] { "ToolRod", "PickaxeHead", "ShovelHead", "AxeHead", "SwordBlade", "LargeGuard", "MediumGuard", "Crossbar", "Binding", "FrypanHead", "SignHead", "LumberHead" };

	public static final String[] materialNames = new String[] { " Rod", " Pickaxe Head", " Shovel Head", " Axe Head", " Sword Blade", " Wide Guard", " Hand Guard", " Crossbar", " Binding", " Pan", " Board", " Broad Axe Head" };

	public static final String[] patterns = new String[] { "ingot", "rod", "pickaxe", "shovel", "axe", "swordblade", "largeguard", "mediumguard", "crossbar", "binding", "frypan", "sign" };

	public static final String[] patternNames = new String[] { "Ingot", "Tool Rod", "Pickaxe Head", "Shovel Head", "Axe Head", "Sword Blade", "Wide Guard", "Hand Guard", "Crossbar", "Tool Binding", "Pan", "Board", "Broad Axe Head" };

	public static Document diary;
	public static Document volume1;

	public void readManuals ()
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		diary = readManual("/manuals/diary.xml", dbFactory);
		volume1 = readManual("/manuals/materials.xml", dbFactory);
	}

	Document readManual (String location, DocumentBuilderFactory dbFactory)
	{
		try
		{
			InputStream stream = TConstruct.class.getResourceAsStream(location);
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(stream);
			doc.getDocumentElement().normalize();
			return doc;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public static Document getManualFromStack (ItemStack stack)
	{
		switch (stack.getItemDamage())
		{
		case 0:
			return diary;
		case 1:
			return volume1;
		}

		return null;
	}

	@Override
	public File getLocation ()
	{
		return Minecraft.getMinecraftDir();
	}

	static int[][] slotTypes = { new int[] { 0, 3, 0 }, //Repair
			new int[] { 1, 4, 0 }, //Pickaxe
			new int[] { 2, 5, 0 }, //Shovel
			new int[] { 2, 6, 0 }, //Axe
			//new int[] {2, 9, 0}, //Lumber Axe
			//new int[] {1, 7, 0}, //Ice Axe
			new int[] { 3, 8, 0 }, //Mattock
			new int[] { 1, 0, 1 }, //Broadsword
			new int[] { 1, 1, 1 }, //Longsword
			new int[] { 1, 2, 1 }, //Rapier
			new int[] { 2, 3, 1 }, //Frying pan
			new int[] { 2, 4, 1 } //Battlesign
	};

	static int[][] iconCoords = { new int[] { 0, 1, 2 }, new int[] { 13, 13, 13 }, //Repair
			new int[] { 0, 0, 1 }, new int[] { 2, 3, 3 }, //Pickaxe
			new int[] { 3, 0, 13 }, new int[] { 2, 3, 13 }, //Shovel
			new int[] { 2, 0, 13 }, new int[] { 2, 3, 13 }, //Axe
			//new int[] { 6, 0, 13 }, new int[] { 2, 3, 13 }, //Lumber Axe
			//new int[] { 0, 0, 5 }, new int[] { 2, 3, 3 }, //Ice Axe
			new int[] { 2, 0, 3 }, new int[] { 2, 3, 2 }, //Mattock
			new int[] { 1, 0, 2 }, new int[] { 2, 3, 3 }, //Broadsword
			new int[] { 1, 0, 3 }, new int[] { 2, 3, 3 }, //Longsword
			new int[] { 1, 0, 4 }, new int[] { 2, 3, 3 }, //Rapier
			new int[] { 4, 0, 13 }, new int[] { 2, 3, 13 }, //Frying Pan
			new int[] { 5, 0, 13 }, new int[] { 2, 3, 13 } //Battlesign
	};

	static String[] toolNames = { "Repair and Modification", "Pickaxe", "Shovel", "Axe",
			//"Lumber Axe",
			//"Ice Axe",
			"Mattock", "Broadsword", "Longsword", "Rapier", "Frying Pan", "Battlesign" };

	static String[] toolDescriptions = { "The main way to repair or change your tools. Place a tool and a material on the left to get started.",
			"The Pickaxe is a basic mining tool. It is effective on stone and ores.\n\nRequired parts:\n- Pickaxe Head\n- Tool Binding\n- Handle",
			"The Shovel is a basic digging tool. It is effective on dirt, sand, and snow.\n\nRequired parts:\n- Shovel Head\n- Handle",
			"The Axe is a basic chopping tool. It is effective on wood and leaves.\n\nRequired parts:\n- Axe Head\n- Handle",
			//"The Lumber Axe is a broad chopping tool. It harvests wood in a wide range and can fell entire trees.\n\nRequired parts:\n- Broad Axe Head\n- Handle",
			//"The Ice Axe is a tool for harvesting ice, mining, and attacking foes.\n\nSpecial Ability:\n- Wall Climb\nNatural Ability:\n- Ice Harvest\nDamage: Moderate\n\nRequired parts:\n- Pickaxe Head\n- Spike\n- Handle",
			"The Cutter Mattock is a versatile farming tool. It is effective on wood, dirt, and plants.\n\nSpecial Ability: Hoe\n\nRequired parts:\n- Axe Head\n- Shovel Head\n- Handle", "The Broadsword is a defensive weapon. Blocking cuts damage in half.\n\nSpecial Ability: Block\nDamage: Moderate\nDurability: High\n\nRequired parts:\n- Sword Blade\n- Wide Guard\n- Handle",
			"The Longsword is a balanced weapon. It is useful for knocking enemies away or getting in and out of battle quickly.\n\nNatural Ability:\n- Charge Boost\nDamage: Moderate\nDurability: Moderate\n\nRequired parts:\n- Sword Blade\n- Hand Guard\n- Handle", "The Rapier is an offensive weapon that relies on quick strikes to defeat foes.\n\nNatural Abilities:\n- Armor Pierce\n- Quick Strike\n- Charge Boost\nDamage: High\nDurability: Low\n\nRequired parts:\n- Sword Blade\n- Crossbar\n- Handle",
			"The Frying is a heavy weapon that uses sheer weight to stun foes.\n\nSpecial Ability: Block\nNatural Ability: Bash\nShift+rClick: Place Frying Pan\nDamage: Low\nDurability: High\n\nRequired parts:\n- Pan\n- Handle",
			//"The Battlesign is an advance in weapon technology worthy of Zombie Pigmen everywhere.\n\nSpecial Ability: Block\nShift-rClick: Place sign\nDamage: Low\nDurability: Average\n\nRequired parts:\n- Board\n- Handle"
			"The Battlesign is an advance in weapon technology worthy of Zombie Pigmen everywhere.\n\nSpecial Ability: Block\nDamage: Low\nDurability: Average\n\nRequired parts:\n- Sign Board\n- Handle" };

	void addToolButtons ()
	{
		for (int i = 0; i < toolNames.length; i++)
		{
			addToolButton(slotTypes[i][0], slotTypes[i][1], slotTypes[i][2], iconCoords[i * 2], iconCoords[i * 2 + 1], toolNames[i], toolDescriptions[i]);
		}
	}

	void addToolButton (int slotType, int xButton, int yButton, int[] xIcons, int[] yIcons, String title, String body)
	{
		TConstructRegistry.toolButtons.add(new ToolGuiElement(slotType, xButton, yButton, xIcons, yIcons, title, body));
	}

	void addRenderMappings ()
	{
		String[] partTypes = { "wood", "stone", "iron", "flint", "cactus", "bone", "obsidian", "netherrack", "slime", "paper", "cobalt", "ardite", "manyullyn", "copper", "bronze", "alumite", "steel" };
		String[] effectTypes = { "diamond", "emerald", "redstone", "glowstone", "moss", "ice", "lava", "blaze", "necrotic", "electric", "lapis" };
		for (int partIter = 0; partIter < partTypes.length; partIter++)
		{
			TConstructClientRegistry.addMaterialRenderMapping(partIter, "tinker", partTypes[partIter], true);
			//materialRenderMap(partIter, partTypes[partIter]);
		}
		for (int effectIter = 0; effectIter < effectTypes.length; effectIter++)
		{
			TConstructClientRegistry.addEffectRenderMapping(effectIter, "tinker", effectTypes[effectIter], true);
			//effectRenderMap(effectIter, effectTypes[effectIter]);
		}
	}

	/*void materialRenderMap (int materialID, String partialLocation)
	{
		for (ToolCore tool : TConstructRegistry.getToolMapping())
		{
			tool.partTextures.put(materialID, tool.getToolTextureFile() + partialLocation);
		}
	}

	void effectRenderMap (int materialID, String partialLocation)
	{
		for (ToolCore tool : TConstructRegistry.getToolMapping())
		{
			tool.effectTextures.put(materialID, tool.getToolTextureFile() + partialLocation);
		}
	}*/

	/* Keybindings */
	public static TControls controlInstance;

	public void registerKeys ()
	{
		controlInstance = new TControls();
		TickRegistry.registerTickHandler(controlInstance, Side.CLIENT);
		uploadKeyBindingsToGame(Minecraft.getMinecraft().gameSettings, controlInstance);
	}

	public void uploadKeyBindingsToGame (GameSettings settings, TKeyHandler keyhandler)
	{
		ArrayList<KeyBinding> harvestedBindings = Lists.newArrayList();
		for (KeyBinding kb : keyhandler.keyBindings)
		{
			harvestedBindings.add(kb);
		}

		KeyBinding[] modKeyBindings = harvestedBindings.toArray(new KeyBinding[harvestedBindings.size()]);
		KeyBinding[] allKeys = new KeyBinding[settings.keyBindings.length + modKeyBindings.length];
		System.arraycopy(settings.keyBindings, 0, allKeys, 0, settings.keyBindings.length);
		System.arraycopy(modKeyBindings, 0, allKeys, settings.keyBindings.length, modKeyBindings.length);
		settings.keyBindings = allKeys;
		settings.loadOptions();
	}
}