MOD = tts
FILES = 
DEBUG = 0

include ../.submakefiles

prepareMod:
	rm -rf freetts/extracted
	mkdir freetts/extracted tmp/com tmp/de

	sh .install-freetts.sh $(APIJAR)

	cp -R freetts/extracted/com/* tmp/com
	cp -R freetts/extracted/de/* tmp/de

jar:
	@echo ""

	sh build-textures.sh $(MOD_ASSETS)

	mkdir -p tmp/com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) tmp/com/qzx/au/$(MOD)/
	(cd tmp ; zip -r ../au_$(MOD)-$(VER).zip *)

cleanMod:
	rm -rf freetts/extracted
