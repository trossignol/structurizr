package fr.rossi.structurizr.parser;

import com.google.common.reflect.ClassPath;
import fr.rossi.structurizr.parser.exception.ClasspathLoaderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileSystems;
import java.util.List;
import java.util.stream.Collectors;

@Mojo(name = "structurizr-parser", defaultPhase = LifecyclePhase.COMPILE)
public class Parser extends AbstractMojo {

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    MavenProject project;

    @Parameter(property = "package")
    String rootPackage;

    private static URL toURL(String path) {
        try {
            return FileSystems.getDefault().getPath(path).toUri().toURL();
        } catch (MalformedURLException e) {
            throw new ClasspathLoaderException("Error building URL for path=" + path, e);
        }
    }

    public void execute() throws MojoExecutionException, MojoFailureException {
        resolveClasses().forEach(c -> getLog().info(c.getSimpleName()));
    }

    private List<Class<?>> resolveClasses() {
        var urlClassLoader = new URLClassLoader(
                new URL[]{toURL(project.getBuild().getOutputDirectory())},
                new URLClassLoader(this.getFiles()));

        // feed the classloader to ClassPath and resolve allClasses
        try {
            return ClassPath.from(urlClassLoader)
                    .getTopLevelClasses()
                    .stream()
                    .map(ClassPath.ClassInfo::load)
                    .filter(c -> StringUtils.isBlank(this.rootPackage) || c.getPackageName().startsWith(this.rootPackage))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ClasspathLoaderException("Error loading classpath", e);
        }
    }

    private URL[] getFiles() {
        try {
            return project.getCompileClasspathElements().stream()
                    .map(Parser::toURL)
                    .distinct()
                    .toArray(URL[]::new);
        } catch (DependencyResolutionRequiredException e) {
            throw new ClasspathLoaderException("Error resolving dependency", e);
        }
    }
}
