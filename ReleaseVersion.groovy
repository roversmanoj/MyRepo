/*** BEGIN META {
  "name" : "Retrieving the Release Version of a particaluar jenkins job",
  "comment" :"This script is used to get the Release Version with result of success from a particular Jenkins job ",
  "incoming-parameters" : ['JobName'],
  "outgoing-parameters" : ['ReleaseVersion'],
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
return lastVer.replace("-SNAPSHOT","")