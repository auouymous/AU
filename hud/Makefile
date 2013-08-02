include ../.makefiles

MOD = hud
FILES = 

prepare:
	@mkdir $(SRC)/com/qzx/au/$(MOD)
	@(cd src; for j in *.java; do cpp -E -P -DMC$(VER) -o $(SRC)/com/qzx/au/$(MOD)/$$j $$j; done)

jar:
	@echo ""
	mkdir -p com/qzx/au/$(MOD)
	cp -R $(REOBF)/com/qzx/au/$(MOD)/*.class $(FILES) com/qzx/au/$(MOD)/
	mkdir -p com/qzx/au/util
	cp -R $(REOBF)/com/qzx/au/util/*.class com/qzx/au/util/
	zip -r au_$(MOD).zip mcmod.info com

clean:
	rm -f au_$(MOD).zip *.class
	rm -rf com
