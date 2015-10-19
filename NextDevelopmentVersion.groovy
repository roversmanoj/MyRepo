/*** BEGIN META {
  "name" : "Retrieving the Next Development Version of a particaluar jenkins job",
  "comment" :"This script is used to get the Next Development Version with result of success from a particular Jenkins job ",
  "incoming-parameters" : ['JobName'],
  "outgoing-parameters" : ['NextDevelopmentVersion'],
  "authors" : [
     { name : "Developed by Cognizant Team" }
  ]
} END META **/

def build = Thread.currentThread().toString()
def regexp= ".+?/job/([^/]+)/.*"
def match = build  =~ regexp
def jobName = match[0][1]
int firstchar=jobName.indexOf("_");
String finaljobname=jobName.substring(firstchar+1);

def lastVer =  jenkins.model.Jenkins.instance.getJob(finaljobname).getLastSuccessfulBuild().getParent().getRootModule().getVersion()

def fromVersion = lastVer.replace("-SNAPSHOT","")

String nextVersion;
        int lastDotIndex = fromVersion.lastIndexOf('.');
        try {
            if (lastDotIndex != -1) {
                // probably a major minor version e.g., 2.1.1
                String minorVersionToken = fromVersion.substring(lastDotIndex + 1);
                String nextMinorVersion;
                int lastDashIndex = minorVersionToken.lastIndexOf('-');
                if (lastDashIndex != -1) {
                    // probably a minor-buildNum e.g., 2.1.1-4 (should change to 2.1.1-5)
                    String buildNumber = minorVersionToken.substring(lastDashIndex + 1);
                    int nextBuildNumber = Integer.parseInt(buildNumber) + 1;
                    nextMinorVersion = minorVersionToken.substring(0, lastDashIndex + 1) + nextBuildNumber;
                } else {
                    nextMinorVersion = Integer.parseInt(minorVersionToken) + 1 + "";
                }
                nextVersion = fromVersion.substring(0, lastDotIndex + 1) + nextMinorVersion;
            } else {
                // maybe it's just a major version; try to parse as an int
                int nextMajorVersion = Integer.parseInt(fromVersion) + 1;
                nextVersion = nextMajorVersion + "";
            }
        } catch (NumberFormatException e) {
            return fromVersion;
        }
        return nextVersion + "-SNAPSHOT";