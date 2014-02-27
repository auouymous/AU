MOD = tts
FILES = 
DEBUG = 0

include ../.submakefiles

prepareMod:
	rm -rf freetts/extracted com de
	mkdir freetts/extracted com de

	sh .install-freetts.sh $(APIJAR)

	cp -R freetts/extracted/com/* com
	cp -R freetts/extracted/de/* de

jar:
	@echo ""

	sh build-textures.sh $(MOD_ASSETS)

	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) com/qzx/au/$(MOD)/
	zip -r au_$(MOD)-$(VER).zip mcmod.info com $(ASSETS) de

cleanMod:
	rm -rf freetts/extracted de

