
/*** BEGIN META {
  "name" : "Retrieving the last successful build version of a particaluar jenkins job",
  "comment" :"This script is used to get the last build version with result of success from a particular Jenkins job ",
  "incoming-parameters" : ['JobName'],
  "outgoing-parameters" : ['LastSuccessBuildVersion'],
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

def version = jenkins.model.Jenkins.instance.getJob(finaljobname).getLastSuccessfulBuild().getParent().getRootModule().getVersion()
return version