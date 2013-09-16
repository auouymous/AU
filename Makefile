include .makefiles

all:
	make clean v=$(VER)

	@# prepare MCP links
	@(cd util ; make prepare v=$(VER))
	@(cd hud ; make prepare v=$(VER))
ifneq ($(VER),147)
	@(cd idmap ; make prepare v=$(VER))
	@(cd extras ; make prepare v=$(VER))
	@#(cd core ; make prepare v=$(VER))
	@#(cd world ; make prepare v=$(VER))
endif

	@# compile all modules
	(cd $(BASE) ; sh recompile.sh)
	(cd $(BASE) ; sh reobfuscate.sh)

	@# package classes and files into jars
	@(cd hud ; make jar v=$(VER))
ifneq ($(VER),147)
	@(cd idmap ; make jar v=$(VER))
	@(cd extras ; make jar v=$(VER))
	@#(cd core ; make jar v=$(VER))
	@#(cd world ; make jar v=$(VER))
endif

clean:
	@# clean up modules
	@(cd hud ; make clean v=$(VER))
	@(cd idmap ; make clean v=$(VER))
	@(cd extras ; make clean v=$(VER))
	@#(cd core ; make clean v=$(VER))
	@#(cd world ; make clean v=$(VER))

	@# clean up MCP files
	rm -rf $(SRC)/com $(REOBF)/com $(REOBF)/net
