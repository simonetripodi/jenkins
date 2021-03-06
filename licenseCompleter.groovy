/*
    This script auguments the missing license information in our dependencies.
*/
complete {
    // license constants
    def apacheLicense = license("The Apache Software License, Version 2.0","http://www.apache.org/licenses/LICENSE-2.0.txt")
    def cddl = license("CDDL","http://www.sun.com/cddl/")
    def lgpl = license("LGPL 2.1","http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html")
    def mitLicense = license("MIT License","http://www.opensource.org/licenses/mit-license.php")
    def jenkinsLicense = license("MIT License","http://jenkins-ci.org/mit-license")


    match("asm:*") {
        if (dependency.licenses.isEmpty())
            rewriteLicense([], license("BSD License","http://asm.ow2.org/license.html"))
    }

    // Apache components
    //   logkit is a part of Avalon
    match(["org.apache.ant:*","commons-jelly:*","log4j:*","avalon-framework:*","logkit:logkit","oro:oro","commons-codec:*","commons-beanutils:*","commons-net:*","commons-cli:*","*:commons-jelly","org.jvnet.hudson:commons-jelly-tags-define","slide:slide-webdavlib"]) {
        if (dependency.licenses.isEmpty())
            rewriteLicense([], apacheLicense)
    }

    // GlassFish components are dual-licensed between CDDL and GPL+Classpath Exception
    // we elect to take them under CDDL.
    // note that central has a different POM from m.g.o-public (http://repo2.maven.org/maven2/javax/mail/mail/1.4/mail-1.4.pom
    // vs http://maven.glassfish.org/content/groups/public/javax/mail/mail/1.4/mail-1.4.pom), so we aren't using  rewriteLicense here
    match(["javax.mail:*","org.jvnet.hudson:activation","org.jvnet:tiger-types","javax.servlet:jstl"]) {
        if (dependency.licenses.isEmpty())
            dependency.licenses=[cddl]
    }

    match("antlr:*") {
        rewriteLicense([], license("BSD License","http://www.antlr.org/license.html"))
    }

    match("jaxen:jaxen") {
        rewriteLicense([], license("BSD License","http://jaxen.codehaus.org/license.html"))
    }

    match("args4j:args4j") {
        rewriteLicense([], mitLicense)
    }

    match("*:dom4j") {
        rewriteLicense([],license("BSD License","http://dom4j.sourceforge.net/dom4j-1.6.1/license.html"))
    }

    match(["org.codehaus.groovy:*"]) {
        // see http://groovy.codehaus.org/License+Information
        // see http://jmdns.sourceforge.net/license.html
        rewriteLicense([],apacheLicense)
    }

    match("*:stapler-adjunct-timeline") {
        rewriteLicense([],license("BSD License","http://simile.mit.edu/license.html"))
    }

    match("*:txw2") {
        // see http://java.net/projects/jaxb/sources/version2/content/trunk/txw2/license.txt?rev=3611
        rewriteLicense([],cddl)
    }

    match(["org.kohsuke.jinterop:j-interop","org.kohsuke.jinterop:j-interopdeps"]) {
        rewriteLicense([],license("LGPL v3","http://www.j-interop.org/license.html"))
    }

    // these are our own modules that have license in the trunk but not in these released versions
    // as we upgrade them, we should just pick up the license info from POM
    match(["org.jenkins-ci.modules:instance-identity","org.jvnet.hudson:task-reactor","org.jvnet.hudson:annotation-indexer","*:jinterop-wmi","*:maven2.1-interceptor","*:lib-jenkins-maven-embedder"]) {
        rewriteLicense([],jenkinsLicense)
    }

    match("*:jna") {
        rewriteLicense([],lgpl)
    }

    match(["org.jvnet.localizer:localizer","*:trilead-putty-extension"]) {
        // see http://java.net/projects/localizer
        // see http://java.net/projects/trilead-putty-extension/
        rewriteLicense([],mitLicense);
    }

    match("org.codehaus.plexus:plexus-interactivity-api") {
        rewriteLicense([],mitLicense)
    }

    match("de.zeigermann.xml:xml-im-exporter:1.1") {
        rewriteLicense([],license("BSD License","http://xml-im-exporter.cvs.sourceforge.net/viewvc/xml-im-exporter/xml-im-exporter/Copying.txt?revision=1.3&view=markup"))
    }

    match("*:sezpoz") {
        // GPL-phobia people react to "GPL" strongly, so accept sezpoz under CDDL
        rewriteLicense([license("CDDL or GPL 2 with Classpath Exception",null)],cddl);
    }
           



    //
    // Choose from multi-licensed modules
    //==========================================================================

    match("*:jna-posix") {
        accept("GNU Lesser General Public License Version 2.1")
    }
}
