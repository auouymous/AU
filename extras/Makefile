MOD = extras
FILES = 

include ../.submakefiles

jar:
	@echo ""

	sh build-textures.sh

	mkdir -p com/qzx/au/util
	cp -R $(REOBF)/com/qzx/au/util/*.class com/qzx/au/util/

	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/*.class $(FILES) com/qzx/au/$(MOD)/
	zip -r au_$(MOD)-$(VER).zip mcmod.info com mods
