package minefantasy.mf2.api.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import minefantasy.mf2.api.rpg.SkillList;

public class InformationList
{
	public static InformationPage artisanry = new InformationPage("infoPage.artisanry", SkillList.artisanry).registerInfoPage();
	public static InformationPage construction = new InformationPage("infoPage.construction", SkillList.construction).registerInfoPage();
	public static InformationPage provisioning = new InformationPage("infoPage.provisioning", SkillList.provisioning).registerInfoPage();
	public static InformationPage engineering = new InformationPage("infoPage.engineering", SkillList.engineering).registerInfoPage();
	public static InformationPage mastery = new InformationPage("infoPage.mastery", null).registerInfoPage();
	
	public static InformationPage dwarvern = new InformationPage("infoPage.dwarvern", SkillList.artisanry).registerInfoPage();
	public static InformationPage gnomish = new InformationPage("infoPage.gnomish", SkillList.engineering).registerInfoPage();
	
    /** Is the smallest column used to display a achievement on the GUI. */
    public static int minDisplayColumn;
    /** Is the smallest row used to display a achievement on the GUI. */
    public static int minDisplayRow;
    /** Is the biggest column used to display a achievement on the GUI. */
    public static int maxDisplayColumn;
    /** Is the biggest row used to display a achievement on the GUI. */
    public static int maxDisplayRow;
    /** Holds a list of all registered achievements. */
    public static List<InformationBase> knowledgeList = new ArrayList();
    public static HashMap<String, InformationBase>nameMap = new HashMap<String, InformationBase>();
    /**
     * A stub functions called to make the static initializer for this class run.
     */
    public static void init() {}
}