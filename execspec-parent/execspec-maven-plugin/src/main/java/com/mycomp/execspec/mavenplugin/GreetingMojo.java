package com.mycomp.execspec.mavenplugin;

/**
 * TODO - add at least one line of java doc comment.
 *
 * @author stasyukd
 * @since 2.0.0-SNAPSHOT
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Says "Hi" to the user.
 */
@Mojo(name = "sayhi")
public class GreetingMojo extends AbstractMojo {

    /**
     * The greeting to display.
     */
    @Parameter( property = "sayhi.greeting", defaultValue = "Hello World haha!" )
    private String greeting;

    public void execute() throws MojoExecutionException {
        getLog().info(greeting);
    }

}
