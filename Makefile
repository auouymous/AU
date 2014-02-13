include .makefiles

all:
	make clean v=$(VER)

	@# check APIs
	@(sh .check-api.sh $(VER) $(APIJAR) $(APISRC))

	@# prepare MCP links
ifeq ($(GRADLE),no)
	@(cd idmap ; make prepare v=$(VER))
endif
	@(cd core ; make prepare v=$(VER))
	@(cd hud ; make prepare v=$(VER))
ifneq ($(VER),147)
	@(cd extras ; make prepare v=$(VER))
#	@(cd world ; make prepare v=$(VER))
endif

	@# compile all modules
ifeq ($(GRADLE),no)
	(cd $(BASE) ; sh recompile.sh)
	(cd $(BASE) ; sh reobfuscate.sh)
else
	(cd $(BASE) ; ./gradlew build)
endif

	@# package classes and files into jars
ifeq ($(GRADLE),no)
	@(cd idmap ; make jar v=$(VER))
endif
	@(cd core ; make jar v=$(VER))
	@(cd hud ; make jar v=$(VER))
ifneq ($(VER),147)
	@(cd extras ; make jar v=$(VER))
#	@(cd world ; make jar v=$(VER))
endif

clean:
	@# clean up modules
	@(cd idmap ; make clean v=$(VER))
	@(cd core ; make clean v=$(VER))
	@(cd hud ; make clean v=$(VER))
	@(cd extras ; make clean v=$(VER))
	@(cd world ; make clean v=$(VER))

	@# clean up MCP files
	rm -rf $(SRC)/com $(REOBF)/com $(REOBF)/net $(BASE)/logs/*.log $(BASE)/.gradle/gradle.log
