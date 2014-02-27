MOD = extras
FILES = 
DEBUG = 0

include ../.submakefiles

jar:
	@echo ""

	sh build-textures.sh $(MOD_ASSETS)

	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) com/qzx/au/$(MOD)/
	zip -r au_$(MOD)-$(VER).zip mcmod.info com $(ASSETS)
