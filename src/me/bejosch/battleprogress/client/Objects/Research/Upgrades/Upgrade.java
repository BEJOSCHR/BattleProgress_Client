package me.bejosch.battleprogress.client.Objects.Research.Upgrades;

import me.bejosch.battleprogress.client.Enum.ResearchCategory;
import me.bejosch.battleprogress.client.Enum.UpgradeType;
import me.bejosch.battleprogress.client.Game.Handler.Game_ResearchHandler;
import me.bejosch.battleprogress.client.Objects.Research.UpgradeDataContainer;

public class Upgrade {

	//DATA
	private UpgradeDataContainer dataContainer = null;
	private String title = "Title";
	private String generalDescription  = "GeneralDescription";
	private String effectDescription  = "EffectDescription"; 
	
	//STRUCTURE
	private UpgradeType type = null;
	private UpgradeType dependency = null;
	private ResearchCategory category = null;
	private int x = 0, y = 0; // POSITION IN THE CATEGORY - LIKE A MATRIX - x LEFT/RIGHT - y UP/DOWN
	
	public Upgrade(UpgradeType type, UpgradeType dependency, ResearchCategory category, int x, int y) {
		
		this.type = type;
		this.dependency = dependency;
		this.category = category;
		this.x = x;
		this.y = y;
		
		this.dataContainer = Game_ResearchHandler.getDataContainer(type);
		this.title = this.dataContainer.getDescription()[0];
		this.generalDescription = this.dataContainer.getDescription()[1];;
		this.effectDescription = this.dataContainer.getDescription()[3];
		
	}
	
	
	
	/**
	 * @return the type
	 */
	public UpgradeType getType() {
		return type;
	}

	/**
	 * @return the dependency
	 */
	public UpgradeType getDependency() {
		return dependency;
	}

	/**
	 * @return the category
	 */
	public ResearchCategory getCategory() {
		return category;
	}
	
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the dataContainer
	 */
	public UpgradeDataContainer getDataContainer() {
		return dataContainer;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the generalDescription
	 */
	public String getGeneralDescription() {
		return generalDescription;
	}

	/**
	 * @return the effectDescription
	 */
	public String getEffectDescription() {
		if(this.dataContainer.effectValue == 0) { //UNLOCK CONTAINER SO NORMALY NO %% IN THERE (Like Converter unlock)
			return effectDescription.replace("%%", "?");
		}else {
			return effectDescription.replace("%%", ""+this.dataContainer.effectValue);
		}
	}
	
	
}
