import com.huanshankeji.CommonDependencies
import com.huanshankeji.CommonVersions
import com.huanshankeji.team.`Shreck Ye`
import com.huanshankeji.team.pomForTeamDefaultOpenSource
import com.huanshankeji.team.repositoriesAddTeamGithubPackagesMavenRegistry

plugins {
    id("com.huanshankeji.team.with-group")
    id("com.huanshankeji.kotlin-jvm-library-sonatype-ossrh-publish-conventions")
    id("com.huanshankeji.team.default-github-packages-maven-publish")
}

repositories {
    mavenLocal()
    mavenCentral()
}
repositoriesAddTeamGithubPackagesMavenRegistry("kotlin-common")

val commonVersions = CommonVersions(kotlinCommon = "0.2.4")
val commonDependencies = CommonDependencies(commonVersions)

dependencies {
    api(commonDependencies.exposed.core()) // TODO: use `implementation` when possible
    // TODO: remove the Exposed JDBC dependency and the PostgresSQL dependency when there is no need to have an exposed transaction to generate SQLs
    runtimeOnly(commonDependencies.exposed.module("jdbc"))
    api(commonDependencies.kotlinCommon.exposed())

    with(commonDependencies.vertx) {
        implementation(platformStackDepchain())
        api(moduleWithoutVersion("sql-client")) // TODO: use `implementation` when possible
        implementation(moduleWithoutVersion("lang-kotlin"))
        implementation(moduleWithoutVersion("lang-kotlin-coroutines"))
    }
    implementation(commonDependencies.kotlinCommon.vertx())

    implementation(commonDependencies.kotlinCommon.core())
    implementation(commonDependencies.arrow.core())

    implementation(commonDependencies.kotlinCommon.net())
}

// for PostgreSQL
dependencies {
    runtimeOnly(commonDependencies.postgreSql())
    implementation(commonDependencies.vertx.moduleWithoutVersion("pg-client"))
}

version = "0.2.0-SNAPSHOT"

publishing.publications.getByName<MavenPublication>("maven") {
    artifactId = rootProject.name + "-postgresql"

    pomForTeamDefaultOpenSource(project, "Exposed Vert.x SQL Client", "Exposed on top of Vert.x Reactive SQL Client") {
        `Shreck Ye`()
    }
}
