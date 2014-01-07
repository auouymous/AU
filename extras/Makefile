MOD = extras
FILES = 
DEBUG = 1

include ../.submakefiles

jar:
	@echo ""

	sh build-textures.sh $(VER)

	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) com/qzx/au/$(MOD)/
	zip -r au_$(MOD)-$(VER).zip mcmod.info com mods assets
