package minefantasy.mf2.integration.minetweaker;

import minefantasy.mf2.api.rpg.Skill;
import minefantasy.mf2.api.rpg.SkillList;

public enum Skills {
	
	/* TODO: Rewrite */
	
	Artisanry(SkillList.artisanry, "artisanry"), Construction(SkillList.construction, "construction"), Combat(SkillList.combat, "combat"), 
	Engineering(SkillList.engineering, "engineering"), Provisioning(SkillList.provisioning, "provisioning");
	
	private Skill s;
	private String name;
	
	private Skills(Skill s, String name) {
		this.s = s;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public Skill getSkill(){
		return s;
	}
	
	public static Skills getFromString(String name){
		for(Skills s : Skills.values()){
			if(s.getName().equalsIgnoreCase(name)){
				return s;
			}
		}
		return null;
	}
	
}
