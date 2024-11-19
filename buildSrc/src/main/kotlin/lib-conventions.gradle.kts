import com.huanshankeji.team.`Shreck Ye`
import com.huanshankeji.team.pomForTeamDefaultOpenSource
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    id("conventions")
    id("com.huanshankeji.kotlin-jvm-library-sonatype-ossrh-publish-conventions")
    id("com.huanshankeji.team.default-github-packages-maven-publish")
    id("com.huanshankeji.team.dokka.github-dokka-convention")
}

publishing.publications.getByName<MavenPublication>("maven") {
    pomForTeamDefaultOpenSource(project, "Exposed Vert.x SQL Client", "Exposed on top of Vert.x Reactive SQL Client") {
        `Shreck Ye`()
    }
}

tasks.named<KotlinCompilationTask<*>>("compileKotlin").configure {
    compilerOptions.freeCompilerArgs.add("-opt-in=com.huanshankeji.exposedvertxsqlclient.InternalApi")
}
