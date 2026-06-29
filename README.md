# CrateReloaded

[![Build Status](https://github.com/Hazebyte/CrateReloaded/workflows/CI/badge.svg)](https://github.com/Hazebyte/CrateReloaded/actions)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Version](https://img.shields.io/badge/version-2.4.0-green.svg)](https://github.com/Hazebyte/CrateReloaded/releases)
[![Discord](https://img.shields.io/discord/YOUR_DISCORD_ID?label=Discord&logo=discord)](https://discord.gg/0srgnnU1nbB8wMML)
[![Minecraft](https://img.shields.io/badge/Minecraft-26.1-orange.svg)](https://www.spigotmc.org/)

A powerful and flexible crate plugin for Minecraft servers. Create customizable crates with animated openings, varied rewards, and a comprehensive claim system. Perfect for Spigot, Paper, and compatible server software.

---

## 📚 Documentation

- **[Wiki](https://github.com/Hazebyte/CrateReloaded/wiki)** - Comprehensive guides and tutorials
- **[API Documentation](https://github.com/Hazebyte/CrateReloadedAPI)** - For plugin developers
- **[Configuration Guide](https://github.com/Hazebyte/CrateReloaded/wiki/Configuration)** - All config options explained
- **[Examples](./examples/)** - Example crate configurations

---

## 🛠️ Development

### Prerequisites

JDK 25 is required — the build targets Java 25 (class-file major version 69). Install a JDK 25
(e.g. Temurin 25) and point your shell at it:

```bash
# macOS
export JAVA_HOME=$(/usr/libexec/java_home -v 25)

# Verify Java version
java -version  # Should show Java 25
```

`.jabbarc` pins `system@1.25` for [Jabba](https://github.com/shyiko/jabba) users.

### Building from Source

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Hazebyte/CrateReloaded.git
   cd CrateReloaded
   ```

2. **Build the plugin:**
   ```bash
   # Development build
   make build-dev

   # Production build
   make

   # Just compile
   make compile
   ```

3. **Find the built JAR:**
   - `bukkit/target/CrateReloaded.jar` (both `make` and `make build-dev` produce this shaded jar)

### Running Tests

```bash
# Run all tests
mvn test

# Generate coverage report
mvn test jacoco:report

# Generate full site with reports
mvn site
# View in browser: target/site/index.html
```

### Code Quality

CrateReloaded uses [Spotless](https://github.com/diffplug/spotless) with [Palantir Java Format](https://github.com/palantir/palantir-java-format) for code formatting:

```bash
# Check formatting
make lint

# Auto-fix formatting issues
make lint-fix
```