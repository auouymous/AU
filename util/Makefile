include ../.makefiles

prepare:
	@mkdir $(SRC)/com/qzx/au/util
	@(cd src; for j in *.java; do cpp -E -P -DMC$(VER) -o $(SRC)/com/qzx/au/util/$$j $$j; done)

jar:
	@echo "Nothing to package"

clean:
	@echo "Nothing to clean"
