MOD = hud
FILES = 

include ../.submakefiles

jar:
	@echo ""

	mkdir -p com/qzx/au/util
	cp -R $(REOBF)/com/qzx/au/util/Config.class com/qzx/au/util/
	cp -R $(REOBF)/com/qzx/au/util/UI.class com/qzx/au/util/

	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) com/qzx/au/$(MOD)/
	zip -r au_$(MOD)-$(VER).zip mcmod.info com
