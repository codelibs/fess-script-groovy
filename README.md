Groovy Script Plugin for Fess
[![Java CI with Maven](https://github.com/codelibs/fess-script-groovy/actions/workflows/maven.yml/badge.svg)](https://github.com/codelibs/fess-script-groovy/actions/workflows/maven.yml)
==========================

## Overview

This plugin lets Fess evaluate [Groovy](https://groovy-lang.org/) scripts, for
example in data store scripts and search-result scripting.

## Installation

Install it from the Fess admin UI, under System > Plugin, or place the jar
manually:

1. Download `fess-script-groovy-X.X.X.jar` from the
   [CodeLibs repository](https://maven.codelibs.org/release/org/codelibs/fess/fess-script-groovy/).
2. Copy it to `$FESS_HOME/app/WEB-INF/plugin` (`/usr/share/fess/app/WEB-INF/plugin`
   for a package install), then restart Fess.

## Build

```bash
git clone https://github.com/codelibs/fess-script-groovy.git
cd fess-script-groovy
mvn package
```
