package minefantasy.mf2.api.knowledge;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import minefantasy.mf2.api.rpg.Skill;
import net.minecraft.util.StatCollector;

public class InformationPage
{
    private String name;
    private LinkedList<InformationBase> entries;
    public final Skill baseSkill;

    public InformationPage(String name, Skill skill, InformationBase... achievements)
    {
        this.name = name;
        this.baseSkill = skill;
        this.entries = new LinkedList<InformationBase>(Arrays.asList(achievements));
    }

    public String getName()
    {
        return name;
    }

    public List<InformationBase> getInfoList()
    {
        return entries;
    }
    
    private static LinkedList<InformationPage> infoPages = new LinkedList<InformationPage>();
    
    /**
     * Registers an achievement page.
     * @param page The page.
     */
    public static void registerInfoPage(InformationPage page)
    {
        if (getInfoPage(page.getName()) != null)
        {
            throw new RuntimeException("Duplicate achievement page name \"" + page.getName() + "\"!");
        }
        infoPages.add(page);
    }
    
    /**
     * Will return an achievement page by its index on the list.
     * @param index The page's index.
     * @return the achievement page corresponding to the index or null if invalid index
     */
    public static InformationPage getInfoPage(int index)
    {
        return infoPages.get(index);
    }
    
    /**
     * Will return an achievement page by its name.
     * @param name The page's name.
     * @return the achievement page with the given name or null if no such page
     */
    public static InformationPage getInfoPage(String name)
    {
        for (InformationPage page : infoPages)
        {
            if (page.getName().equals(name))
            {
                return page;
            }
        }
        return null;
    }
    
    /**
     * Will return the list of achievement pages.
     * @return the list's size
     */
    public static Set<InformationPage> getInfoPages()
    {
        return new HashSet<InformationPage>(infoPages);
    }
    
    /**
     * Will return whether an achievement is in any page or not.
     * @param achievement The achievement.
     */
    public static boolean isInfoInPages(InformationBase achievement)
    {
        for (InformationPage page : infoPages)
        {
            if (page.getInfoList().contains(achievement)) 
            {
                return true;
            }
        }
        return false;
    }
    
    public static String getTitle(int index)
    {
        return StatCollector.translateToLocal(index == -1 ? "Basic" : getInfoPage(index).getName());
    }
    public static Skill getSkill(int index)
    {
        return index == -1 ? null : getInfoPage(index).baseSkill;
    }

	public void addInfo(InformationBase entry)
	{
		entries.add(entry);
	}

	public InformationPage registerInfoPage()
	{
		registerInfoPage(this);
		return this;
	}

}