MOD = core
FILES = 
DEBUG = 1

include ../.submakefiles

jar:
	@echo ""

	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) com/qzx/au/$(MOD)/
	zip -r au_$(MOD)-$(VER).zip mcmod.info com
