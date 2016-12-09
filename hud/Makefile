MOD = hud
FILES = 

include ../.submakefiles

jar:
	@echo ""

	mkdir -p tmp/com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/* $(FILES) tmp/com/qzx/au/$(MOD)/
	(cd tmp ; zip -r ../au_$(MOD)-$(VER).zip *)
