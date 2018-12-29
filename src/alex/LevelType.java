package alex;

public enum LevelType {
	
	OVERWORLD(),
	UNDERWORLD(),
	CASTLE();
	
	@Override
	public String toString() {
		if(this == LevelType.OVERWORLD)  return "OVERWORLD";
		else if(this == LevelType.UNDERWORLD) return "UNDERWORLD";
		else if(this == LevelType.CASTLE) return "CASTLE";
		else return "LEVEL_TYPE_ERROR";
	}
}
