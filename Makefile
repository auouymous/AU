include .makefiles

all:
	make clean

	@# prepare MCP links
	@mkdir -p $(SRC)/com/qzx/au
	@(cd util ; make prepare)
	@(cd hud ; make prepare)
ifneq ($(VER),147)
	@#(cd core ; make prepare)
	@#(cd world ; make prepare)
endif

	@# compile all modules
	(cd $(BASE) ; sh recompile.sh)
	(cd $(BASE) ; sh reobfuscate.sh)

	@# package classes and files into jars
	@(cd hud ; make jar)
ifneq ($(VER),147)
	@#(cd core ; make jar)
	@#(cd world ; make jar)
endif

clean:
	@# clean up modules
	@(cd hud ; make clean)
ifneq ($(VER),147)
	@#(cd core ; make clean)
	@#(cd world ; make clean)
endif

	@# clean up MCP files
	rm -rf $(SRC)/com $(REOBF)/com $(REOBF)/net
