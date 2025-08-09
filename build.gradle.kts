plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.me.ivan"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://packages.jetbrains.team/maven/p/markdown/maven")
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	implementation("org.xerial:sqlite-jdbc:3.42.0.0")

	implementation("org.commonmark:commonmark:0.21.0")
	implementation("org.jsoup:jsoup:1.16.1")

	implementation("org.languagetool:languagetool-core:5.9")
	implementation("org.languagetool:language-es:5.9")
	implementation("org.languagetool:language-en:5.9")
	implementation("org.languagetool:language-fr:5.9")
	implementation("org.languagetool:language-de:5.9")
	implementation("org.languagetool:language-it:5.9")
	implementation("org.languagetool:language-pt:5.9")
	implementation("org.languagetool:language-ru:5.9")
	implementation("org.languagetool:language-nl:5.9")

	implementation("org.apache.tika:tika-core:2.9.0")
	implementation("org.apache.tika:tika-langdetect:2.9.1")
	implementation("org.apache.tika:tika-langdetect-optimaize:2.9.1")

	implementation("com.google.guava:guava:31.1-jre")

	implementation("org.jetbrains.exposed:exposed-core:0.42.0")
	implementation("org.jetbrains.exposed:exposed-dao:0.42.0")
	implementation("org.jetbrains.exposed:exposed-jdbc:0.42.0")
	implementation("org.xerial:sqlite-jdbc:3.42.0.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
