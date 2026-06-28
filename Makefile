# Requires JDK 25. If your default `java` differs: export JAVA_HOME=$(/usr/libexec/java_home -v 25)

START:
	$(MAKE) build

compile:
	mvn compile

clean:
	mvn clean

package:
	mvn package

# Lint only the first-party modules; api/utils/effectlib/cmi-stub are vendored/stub code.
lint:
	mvn -pl bukkit,common,nms spotless:check

lint-fix:
	mvn -pl bukkit,common,nms spotless:apply

version:
	mvn versions:set
	mvn versions:commit

proguard:
	sh ./scripts/obfuscate.sh

build-package:
	$(MAKE) package

build-dev:
	$(MAKE) build-package
	sh ./scripts/create-dev-jar.sh

build:
	$(MAKE) build-package
	$(MAKE) proguard
