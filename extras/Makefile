MOD = extras
FILES = 
DEBUG = 0

include ../.submakefiles

jar:
	@echo ""

	sh build-textures.sh $(MOD_ASSETS)

	mkdir -p tmp/com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) tmp/com/qzx/au/$(MOD)/
	(cd tmp ; zip -r ../au_$(MOD)-$(VER).zip *)
